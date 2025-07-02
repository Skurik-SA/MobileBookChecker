package com.example.bookchecker.domain.model


data class Rating(
    val score: Int,
    val scale: Int = 10
)