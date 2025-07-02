package com.example.bookchecker.data.remote.dto.auth

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterRequestDto (
    val email: String,
    val username: String,
    val password: String
)