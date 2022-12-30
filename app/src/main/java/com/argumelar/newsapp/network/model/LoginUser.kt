package com.argumelar.newsapp.network.model

data class LoginUser(
    val username: String,
    val password: String
)

data class LoginResponse(
    val token: String?,
    val message: String
)
