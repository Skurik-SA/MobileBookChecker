package com.example.bookchecker.data.mapper

import com.example.bookchecker.data.local.entity.BookEntity
import com.example.bookchecker.data.remote.dto.BookDto
import com.example.bookchecker.domain.model.Book

object BookMapper {
    fun dtoToEntity(dto: BookDto): BookEntity = BookEntity(
        id = dto.id ?: 0L,
        title = dto.title,
        author = dto.author,
        totalPages = dto.totalPages,
        description = dto.description,
        coverUrl = dto.cover,
        genres = dto.genres!!.joinToString(",") // store as CSV
    )

    fun entityToDomain(entity: BookEntity): Book = Book(
        id = entity.id,
        title = entity.title,
        author = entity.author,
        totalPages = entity.totalPages,
        description = entity.description,
        coverUrl = entity.coverUrl,
        genres = entity.genres.split(",")
    )

    fun domainToEntity(model: Book): BookEntity = BookEntity(
        id = model.id,
        title = model.title,
        author = model.author,
        totalPages = model.totalPages,
        description = model.description,
        coverUrl = model.coverUrl,
        genres = model.genres!!.joinToString(",")
    )

    fun domainToDto(model: Book): BookDto = BookDto(
        id = model.id,
        title = model.title,
        author = model.author,
        totalPages = model.totalPages,
        description = model.description,
        cover = model.coverUrl,
        genres = model.genres
    )
}