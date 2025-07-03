package com.example.bookchecker.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProgressDto(
    @param:Json(name = "current_page")
    val currentPage: Int,

    val percent: Double? = null,

    @param:Json(name = "updated_at")
    val updatedAt: String? = null
)