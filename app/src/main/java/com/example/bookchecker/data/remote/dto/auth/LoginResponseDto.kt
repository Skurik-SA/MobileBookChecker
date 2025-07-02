package com.example.bookchecker.data.remote.dto.auth

import com.squareup.moshi.Json

data class LoginResponseDto (
    val access: String,

    val refresh: String,

    @param:Json(name = "user_id")
    val userId: Long,

    val username: String
)