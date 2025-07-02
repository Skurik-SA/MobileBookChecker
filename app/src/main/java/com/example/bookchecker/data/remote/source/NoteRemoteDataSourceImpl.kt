package com.example.bookchecker.data.remote.source

import com.example.bookchecker.data.mapper.NoteMapper
import com.example.bookchecker.data.remote.api.NoteApi
import com.example.bookchecker.domain.model.Note
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRemoteDataSourceImpl @Inject constructor(
    private val api: NoteApi
) : NoteRemoteDataSource {
    override suspend fun getNotes(entryId: Long): List<Note> =
        api.list(entryId).map { dto -> NoteMapper.dtoToEntity(dto, entryId).let(NoteMapper::entityToDomain) }

    override suspend fun createNote(entryId: Long, note: Note): Note {
        val dto = NoteMapper.domainToDto(note)
        val response = api.create(entryId, dto)
        return NoteMapper.dtoToEntity(response, entryId).let(NoteMapper::entityToDomain)
    }

    override suspend fun getNote(entryId: Long, noteId: Long): Note =
        api.get(entryId, noteId).let { dto -> NoteMapper.dtoToEntity(dto, entryId).let(NoteMapper::entityToDomain) }

    override suspend fun updateNote(entryId: Long, note: Note): Note {
        val dto = NoteMapper.domainToDto(note)
        val response = api.update(entryId, note.id, dto)
        return NoteMapper.dtoToEntity(response, entryId).let(NoteMapper::entityToDomain)
    }

    override suspend fun deleteNote(entryId: Long, noteId: Long) = api.delete(entryId, noteId)
}
