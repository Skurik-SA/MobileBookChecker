package com.example.bookchecker.data.remote.dto

import com.squareup.moshi.Json

data class ProgressDto(
    @param:Json(name = "current_page")
    val currentPage: Int,

    val percent: Double,

    @param:Json(name = "updated_at")
    val updatedAt: String
)