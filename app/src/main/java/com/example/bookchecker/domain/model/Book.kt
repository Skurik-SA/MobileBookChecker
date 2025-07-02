package com.example.bookchecker.domain.model

data class Book(
    val id: Long,
    val title: String,
    val author: String,
    val totalPages: Int,
    val description: String?,
    val coverUrl: String?,
    val genres: List<String>
)