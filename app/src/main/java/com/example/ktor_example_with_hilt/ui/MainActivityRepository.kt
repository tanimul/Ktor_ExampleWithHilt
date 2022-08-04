package com.example.ktor_example_with_hilt.ui

import com.example.ktor_example_with_hilt.data.models.DataList
import com.example.ktor_example_with_hilt.data.network.ApiService
import io.ktor.client.call.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainActivityRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getRepoData(q: String): Flow<DataList> = flow {
        emit(apiService.getRepoData(q).body())
    }

}