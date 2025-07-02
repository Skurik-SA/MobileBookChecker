package com.example.bookchecker.domain.model


data class Progress(
    val currentPage: Int,
    val percent: Double,
    val updatedAt: String
)