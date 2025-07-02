package com.example.bookchecker.domain.model

data class Statistics(
    val totalBooksRead: Int,
    val totalPagesRead: Int,
    val topGenre: String?,
    val monthCount: Map<String, Int>, // e.g. "2025-06": 5 books
    val yearCount: Map<Int, Int>
)
