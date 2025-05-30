package com.matchmate.data.network

sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(val errorMsg: String?) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}