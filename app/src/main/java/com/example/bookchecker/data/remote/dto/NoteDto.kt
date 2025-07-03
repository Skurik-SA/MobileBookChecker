package com.example.bookchecker.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NoteDto(
    val id: Long? = null,
    val text: String,
    @param:Json(name = "created_at")
    val createdAt: String? = null
)