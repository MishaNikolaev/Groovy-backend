package com.nmichail.groovy.data.dtos.auth

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val email: String,
    val password: String,
    val username: String
)

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class UserDto(
    val id: String,
    val email: String,
    val username: String
)

@Serializable
data class AuthResponse(
    val user: UserDto,
    val token: String
) 