package com.example.bookchecker.data.remote.dto

import com.squareup.moshi.Json

data class NoteDto(
    val id: Long,
    val text: String,
    @param:Json(name = "created_at")
    val createdAt: String
)