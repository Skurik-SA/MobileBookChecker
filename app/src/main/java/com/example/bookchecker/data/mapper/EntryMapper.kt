package com.example.bookchecker.data.mapper

import com.example.bookchecker.data.local.entity.EntryEntity
import com.example.bookchecker.data.remote.dto.EntryDto
import com.example.bookchecker.data.remote.dto.ProgressDto
import com.example.bookchecker.data.remote.dto.RatingDto
import com.example.bookchecker.domain.model.Book
import com.example.bookchecker.domain.model.Entry
import com.example.bookchecker.domain.model.Note
import com.example.bookchecker.domain.model.Progress
import com.example.bookchecker.domain.model.Rating


object EntryMapper {
    fun dtoToEntity(dto: EntryDto): EntryEntity = EntryEntity(
        id = dto.id,
        bookId = dto.book.id ?: 0L,
        status = dto.status,
        startedAt = dto.startedAt,
        finishedAt = dto.finishedAt,
        currentPage = dto.progress.currentPage,
        percent = dto.progress.percent,
        progressUpdatedAt = dto.progress.updatedAt,
        ratingScore = dto.rating?.score,
        ratingScale = dto.rating?.scale
    )

    fun entityToDomain(entity: EntryEntity, book: Book, notes: List<Note>): Entry =
        Entry(
            id = entity.id,
            book = book,
            status = entity.status,
            startedAt = entity.startedAt,
            finishedAt = entity.finishedAt,
            progress = Progress(
                currentPage = entity.currentPage,
                percent = entity.percent,
                updatedAt = entity.progressUpdatedAt
            ),
            rating = entity.ratingScore?.let { score ->
                Rating(score = score, scale = entity.ratingScale ?: 10)
            },
            notes = notes
        )

    fun domainToDto(model: Entry): EntryDto = EntryDto(
        id = model.id,
        book = BookMapper.domainToDto(model.book),
        status = model.status,
        startedAt = model.startedAt,
        finishedAt = model.finishedAt,
        progress = ProgressDto(
            currentPage = model.progress.currentPage,
            percent = model.progress.percent,
            updatedAt = model.progress.updatedAt
        ),
        rating = model.rating?.let { RatingDto(it.score, it.scale) },
        notes = model.notes.map { NoteMapper.domainToDto(it) }
    )
}
