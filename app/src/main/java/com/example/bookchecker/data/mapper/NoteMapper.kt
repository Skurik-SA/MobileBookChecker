package com.example.bookchecker.data.mapper

import com.example.bookchecker.data.local.entity.NoteEntity
import com.example.bookchecker.data.remote.dto.NoteDto
import com.example.bookchecker.domain.model.Note


object NoteMapper {
    fun dtoToEntity(dto: NoteDto, entryId: Long): NoteEntity = NoteEntity(
        id = dto.id,
        entryId = entryId,
        text = dto.text,
        createdAt = dto.createdAt
    )

    fun entityToDomain(entity: NoteEntity): Note = Note(
        id = entity.id,
        entryId = entity.entryId,
        text = entity.text,
        createdAt = entity.createdAt
    )

    fun domainToDto(model: Note): NoteDto = NoteDto(
        id = model.id,
        text = model.text,
        createdAt = model.createdAt
    )
}