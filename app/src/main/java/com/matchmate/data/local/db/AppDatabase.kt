package com.matchmate.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.matchmate.data.local.dao.UserDao
import com.matchmate.data.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}