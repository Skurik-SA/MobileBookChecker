package com.example.bookchecker.data.remote.dto
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RatingDto(
    val score: Int,
    val scale: Int = 10
)
