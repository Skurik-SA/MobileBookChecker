package com.example.bookchecker.data.repository

import com.example.bookchecker.data.local.source.NoteLocalDataSource
import com.example.bookchecker.data.remote.source.NoteRemoteDataSource
import com.example.bookchecker.domain.model.Note
import com.example.bookchecker.domain.repository.NoteRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepositoryImpl @Inject constructor(
    private val remote: NoteRemoteDataSource,
    private val local: NoteLocalDataSource
) : NoteRepository {
    override suspend fun addNote(entryId: Long, note: Note): Note {
        val created = remote.createNote(entryId, note)
        local.insertNote(created)
        return created
    }

    override suspend fun getNotes(entryId: Long): List<Note> {
        return try {
            val notes = remote.getNotes(entryId)
            notes.forEach { local.insertNote(it) }
            notes
        } catch (e: Exception) {
            local.getNotes(entryId)
        }
    }

    override suspend fun getNote(entryId: Long, noteId: Long): Note? {
        return try {
            val note = remote.getNote(entryId, noteId)
            local.insertNote(note)
            note
        } catch (e: Exception) {
            local.getNotes(entryId).find { it.id == noteId }
        }
    }

    override suspend fun updateNote(entryId: Long, note: Note): Note {
        val updated = remote.updateNote(entryId, note)
        local.updateNote(updated)
        return updated
    }

    override suspend fun deleteNote(entryId: Long, noteId: Long) {
        remote.deleteNote(entryId, noteId)
        local.deleteNote(noteId)
    }
}