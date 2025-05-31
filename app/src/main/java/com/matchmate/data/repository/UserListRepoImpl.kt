package com.matchmate.data.repository

import com.matchmate.data.local.dao.UserDao
import com.matchmate.data.model.User
import com.matchmate.data.network.ApiService
import com.matchmate.data.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserListRepoImpl @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) : UserListRepo {

    override suspend fun getNetworkUsers(results: Int): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading)
        try {
            val response = apiService.getUsers(results)

            userDao.deleteAll()

            userDao.insertAll(response.results)

            emit(Resource.Success(response.results))
        } catch (e: Exception) {

            emit(Resource.Failure(e.message))

            getLocalUsers()
        }
    }.flowOn(Dispatchers.IO)

    override fun getLocalUsers(): Flow<List<User>> {
        return userDao.getAllUsersFlow().flowOn(Dispatchers.IO)
    }

    override suspend fun acceptUser(emailId: String): User? {
        val user = userDao.getUserById(emailId)

        user?.let {
            user.isAccepted = true
            user.isDeclined = false
            userDao.updateUser(user)
        }

        return user
    }

    override suspend fun declineUser(emailId: String): User? {
        val user = userDao.getUserById(emailId)

        user?.let {
            user.isAccepted = false
            user.isDeclined = true
            userDao.updateUser(user)
        }

        return user
    }
}