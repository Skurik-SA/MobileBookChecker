package com.example.bookchecker.data.remote.dto.auth

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RefreshResponseDto (
    val access: String,
    val refresh: String
)