package com.example.bookchecker.domain.model


data class Progress(
    val currentPage: Int,
    val percent: Double? = null,
    val updatedAt: String? = null
)