package com.matchmate.data.network

import com.matchmate.data.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/")
    suspend fun getUsers(@Query("results") results: Int): UserResponse
}