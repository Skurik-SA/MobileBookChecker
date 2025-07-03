package com.example.bookchecker.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BookDto(
    val id: Long? = null,

    val title: String,

    val author: String,

    @param:Json(name = "total_pages")
    val totalPages: Int,

    val description: String? = null,

    val cover: String? = null,

    @param:Json(name = "genre_names")
    val genres: List<String>? = emptyList()
)

//"book": {
//    "id": 14,
//    "title": "Робинзон Крузо",
//    "author": "ХЗ-Кто",
//    "total_pages": 125,
//    "description": "В далёкой-далёкой галактике...",
//    "cover": null,
//    "genre_names": [
//    "Ужасы",
//    "На реальных событиях"
//    ]
//},