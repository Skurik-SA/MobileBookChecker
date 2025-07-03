package com.example.bookchecker.data.repository

import com.example.bookchecker.core.session.SessionManager
import com.example.bookchecker.data.local.source.NoteLocalDataSource
import com.example.bookchecker.data.remote.source.NoteRemoteDataSource
import com.example.bookchecker.domain.model.Note
import com.example.bookchecker.domain.repository.NoteRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton


class NoteRepositoryImpl @Inject constructor(
    private val remote: NoteRemoteDataSource,
    private val local: NoteLocalDataSource,
    private val sessionManager: SessionManager
) : NoteRepository {

    private fun isAuthorized(): Boolean = runBlocking {
        sessionManager.accessToken.firstOrNull() != null
    }

    override suspend fun addNote(entryId: Long, note: Note): Note {
        return if (isAuthorized()) {
            val created = remote.createNote(entryId, note)
            local.insertNote(created)
            created
        } else {
            local.insertNote(note)
            note
        }
    }

    override suspend fun getNotes(entryId: Long): List<Note> {
        return if (isAuthorized()) {
            try {
                val notes = remote.getNotes(entryId)
                notes.forEach { local.insertNote(it) }
                notes
            } catch (e: Exception) {
                local.getNotes(entryId)
            }
        } else {
            local.getNotes(entryId)
        }
    }

    override suspend fun getNote(entryId: Long, noteId: Long): Note? {
        return if (isAuthorized()) {
            try {
                val note = remote.getNote(entryId, noteId)
                local.insertNote(note)
                note
            } catch (e: Exception) {
                local.getNotes(entryId).find { it.id == noteId }
            }
        } else {
            local.getNotes(entryId).find { it.id == noteId }
        }
    }

    override suspend fun updateNote(entryId: Long, note: Note): Note {
        return if (isAuthorized()) {
            val updated = remote.updateNote(entryId, note)
            local.updateNote(updated)
            updated
        } else {
            local.updateNote(note)
            note
        }
    }

    override suspend fun deleteNote(entryId: Long, noteId: Long) {
        if (isAuthorized()) {
            remote.deleteNote(entryId, noteId)
        }
        local.deleteNote(noteId)
    }
}