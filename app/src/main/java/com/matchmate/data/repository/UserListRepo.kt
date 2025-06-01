package com.matchmate.data.repository

import com.matchmate.data.model.User
import com.matchmate.data.network.Resource
import kotlinx.coroutines.flow.Flow

interface UserListRepo {

    suspend fun getUsers(results: Int): Flow<Resource<List<User>>>

    suspend fun acceptUser(emailId: String) : User?

    suspend fun declineUser(emailId: String) : User?
}