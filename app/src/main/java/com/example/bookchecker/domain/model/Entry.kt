package com.example.bookchecker.domain.model

enum class BookStatus { TO_READ, READING, FINISHED }

data class Entry(
    val id: Long,
    val book: Book,
    val status: String,
    val startedAt: String?,
    val finishedAt: String?,
    val progress: Progress,
    val rating: Rating?,
    val notes: List<Note>
)