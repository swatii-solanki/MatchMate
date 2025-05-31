package com.matchmate.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.matchmate.data.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)

    @Query("SELECT * FROM users")
    fun getAllUsersFlow(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE email = :emailId")
    suspend fun getUserById(emailId: String): User?

    @Update
    suspend fun updateUser(user: User)

    @Query("DELETE FROM users")
    suspend fun deleteAll()
}