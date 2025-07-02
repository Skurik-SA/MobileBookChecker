package com.example.bookchecker.data.remote.dto.auth

data class RefreshResponseDto (
    val access: String,
    val refresh: String
)