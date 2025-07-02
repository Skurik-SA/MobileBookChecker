package com.example.bookchecker.data.local.source

import com.example.bookchecker.data.local.dao.NoteDao
import com.example.bookchecker.data.mapper.NoteMapper
import com.example.bookchecker.domain.model.Note
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteLocalDataSourceImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteLocalDataSource {
    override suspend fun getNotes(entryId: Long): List<Note> =
        noteDao.getByEntryId(entryId).map(NoteMapper::entityToDomain)

    override suspend fun insertNote(note: Note): Long {
        val entity = NoteMapper.domainToDto(note).let { NoteMapper.dtoToEntity(it, note.entryId) }
        return noteDao.insert(entity)
    }

    override suspend fun updateNote(note: Note) {
        val entity = NoteMapper.domainToDto(note).let { NoteMapper.dtoToEntity(it, note.entryId) }
        noteDao.update(entity)
    }

    override suspend fun deleteNote(id: Long) = noteDao.delete(id)
}