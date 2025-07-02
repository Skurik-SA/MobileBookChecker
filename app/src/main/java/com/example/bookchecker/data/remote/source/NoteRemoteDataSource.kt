package com.example.bookchecker.data.remote.source

import com.example.bookchecker.domain.model.Note

interface NoteRemoteDataSource {
    suspend fun getNotes(entryId: Long): List<Note>
    suspend fun createNote(entryId: Long, note: Note): Note
    suspend fun getNote(entryId: Long, noteId: Long): Note
    suspend fun updateNote(entryId: Long, note: Note): Note
    suspend fun deleteNote(entryId: Long, noteId: Long)
}
