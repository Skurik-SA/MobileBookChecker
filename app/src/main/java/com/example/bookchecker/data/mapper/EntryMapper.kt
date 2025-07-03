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
    /** Domain → Entity (Room) */
    fun domainToEntity(model: Entry): EntryEntity =
        EntryEntity(
            id               = model.id ?: 0L,
            bookId           = model.book.id!!,
            status           = model.status,
            startedAt        = model.startedAt,
            finishedAt       = model.finishedAt,
            currentPage      = model.progress?.currentPage ?: 0,
            percent          = model.progress?.percent ?: 0.0,
            progressUpdatedAt= model.progress?.updatedAt,
            ratingScore      = model.rating?.score,
            ratingScale      = model.rating?.scale
        )

    /** Entity + Book + Notes → Domain */
    fun entityToDomain(
        entity: EntryEntity,
        book: com.example.bookchecker.domain.model.Book,
        notes: List<Note>
    ): Entry =
        Entry(
            id         = entity.id,
            book       = book,
            status     = entity.status,
            startedAt  = entity.startedAt,
            finishedAt = entity.finishedAt,
            progress   = Progress(
                currentPage = entity.currentPage,
                percent     = entity.percent,
                updatedAt   = entity.progressUpdatedAt
            ),
            rating     = entity.ratingScore?.let { Rating(it, entity.ratingScale ?: 10) },
            notes      = notes
        )

    /** DTO → Entity */
    fun dtoToEntity(dto: EntryDto): EntryEntity =
        EntryEntity(
            id               = dto.id ?: 0L,
            bookId           = dto.book.id ?: 0L,
            status           = dto.status,
            startedAt        = dto.startedAt,
            finishedAt       = dto.finishedAt,
            currentPage      = dto.progress?.currentPage ?: 0,
            percent          = dto.progress?.percent ?: 0.0,
            progressUpdatedAt= dto.progress?.updatedAt,
            ratingScore      = dto.rating?.score,
            ratingScale      = dto.rating?.scale
        )

    /** DTO → Domain */
    fun dtoToDomain(dto: EntryDto): Entry {
        val book = BookMapper.dtoToEntity(dto.book).let(BookMapper::entityToDomain)
        // Для NoteMapper передаём явно entryId:
        val notes = dto.notes.map { noteDto ->
            NoteMapper.dtoToEntity(noteDto, dto.id)
        }.map(NoteMapper::entityToDomain)
        // Собираем EntryEntity из DTO:
        val entity = dtoToEntity(dto)
        return entityToDomain(entity, book, notes)
    }

    /** Domain → DTO */
    fun domainToDto(model: Entry): EntryDto =
        EntryDto(
            id         = model.id,
            book       = BookMapper.domainToDto(model.book),
            status     = model.status,
            startedAt  = model.startedAt,
            finishedAt = model.finishedAt,
            progress   = model.progress?.let {
                ProgressDto(it.currentPage, it.percent, it.updatedAt)
            },
            rating     = model.rating?.let { RatingDto(it.score, it.scale) },
            notes      = model.notes.orEmpty().map(NoteMapper::domainToDto)
        )
}