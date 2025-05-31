package com.matchmate.di

import com.matchmate.data.repository.UserListRepo
import com.matchmate.data.repository.UserListRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun getUserRepository(repo: UserListRepoImpl): UserListRepo
}