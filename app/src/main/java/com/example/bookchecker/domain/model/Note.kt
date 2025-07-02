package com.example.bookchecker.domain.model


data class Note(
    val id: Long,
    val entryId: Long,
    val text: String,
    val createdAt: String
)