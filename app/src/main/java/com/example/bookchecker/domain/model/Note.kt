package com.example.bookchecker.domain.model


data class Note(
    val id: Long? = null,
    val entryId: Long? = null,
    val text: String,
    val createdAt: String? = null,
)