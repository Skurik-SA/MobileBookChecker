package com.example.bookchecker.domain.repository

import com.example.bookchecker.domain.model.Note

interface NoteRepository {
    suspend fun getNotes(entryId: Long): List<Note>
    suspend fun addNote(entryId: Long, note: Note): Note
    suspend fun getNote(entryId: Long, noteId: Long): Note?
    suspend fun updateNote(entryId: Long, note: Note): Note
    suspend fun deleteNote(entryId: Long, noteId: Long)
}