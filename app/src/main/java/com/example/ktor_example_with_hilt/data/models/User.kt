package com.example.ktor_example_with_hilt.data.models
import kotlinx.serialization.Serializable

@Serializable
data class DataList(val items: List<User>)
@Serializable
data class User(val name: String, val description: String?, val owner: Owner?)
@Serializable
data class Owner(val avatar_url: String?, val type: String)
