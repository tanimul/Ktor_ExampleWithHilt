package com.example.ktor_example_with_hilt.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ktor_example_with_hilt.data.network.ApiSate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: MainActivityRepository
) : ViewModel() {

    private val _apiStateFlow: MutableStateFlow<ApiSate> = MutableStateFlow(ApiSate.Empty)
    val apiStateFlow: StateFlow<ApiSate> = _apiStateFlow

    fun getRepoData(q: String) = viewModelScope.launch {
        repository.getRepoData(q)
            .onStart {
                _apiStateFlow.value = ApiSate.Loading
            }.catch { e ->
                Log.e(TAG, "getData: $e")
                _apiStateFlow.value = ApiSate.Failed(e)
            }.collect { response ->
                _apiStateFlow.value = ApiSate.Success(response.items)
            }
    }
}