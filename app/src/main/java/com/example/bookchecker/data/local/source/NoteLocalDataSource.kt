package com.example.bookchecker.data.local.source

import com.example.bookchecker.domain.model.Note


interface NoteLocalDataSource {
    suspend fun getNotes(entryId: Long): List<Note>
    suspend fun insertNote(note: Note): Long
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(id: Long)
}