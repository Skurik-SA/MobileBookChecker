package com.example.bookchecker.domain.model

data class Book(
    val id: Long? = null,
    val title: String,
    val author: String,
    val totalPages: Int,
    val description: String? = null,
    val coverUrl: String? = null,
    val genres: List<String>? = null
)