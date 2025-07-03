package com.example.bookchecker.domain.usecase.note

import dagger.Provides
import javax.inject.Singleton

data class NoteUseCases (
    val getNotes: GetNotesUseCase,
    val addNote: AddNoteUseCase,
    val deleteNote: DeleteNoteUseCase,
)
