package com.example.ktor_example_with_hilt.data.network

sealed class ApiSate {
    object Empty : ApiSate()
    object Loading : ApiSate()
    class Success<T>(val data: T) : ApiSate()
    class Failed(val message: Throwable) : ApiSate()
}