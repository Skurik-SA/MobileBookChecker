package com.example.bookchecker.data.remote.dto

import com.squareup.moshi.Json

data class EntryDto(
    val id: Long,

    @param:Json(name = "book")
    val book: BookDto,

    val status: String,

    @param:Json(name = "started_at")
    val startedAt: String?,

    @param:Json(name = "finished_at")
    val finishedAt: String?,

    val progress: ProgressDto,
    val rating: RatingDto?,
    val notes: List<NoteDto> = emptyList()
)