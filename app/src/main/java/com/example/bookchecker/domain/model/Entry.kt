package com.example.bookchecker.domain.model

enum class BookStatus { TO_READ, READING, FINISHED }

data class Entry(
    val id: Long? = null,
    val book: Book,
    val status: String,
    val startedAt: String? = null,
    val finishedAt: String? = null,
    val progress: Progress? = null,
    val rating: Rating? = null,
    val notes: List<Note>? = null
)