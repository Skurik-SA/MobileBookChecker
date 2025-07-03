package com.example.bookchecker.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class EntryDto(
    val id: Long? = null,

    @param:Json(name = "book")
    val book: BookDto,

    val status: String,

    @param:Json(name = "started_at")
    val startedAt: String? = null,

    @param:Json(name = "finished_at")
    val finishedAt: String? = null,

    val progress: ProgressDto? = null,
    val rating: RatingDto? = null,
    val notes: List<NoteDto> = emptyList()
)
