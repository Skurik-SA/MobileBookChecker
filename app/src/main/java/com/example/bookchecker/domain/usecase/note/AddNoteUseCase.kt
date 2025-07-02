package com.example.bookchecker.domain.usecase.note

import com.example.bookchecker.domain.model.Note
import com.example.bookchecker.domain.repository.NoteRepository
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(entryId: Long, note: Note): Note =
        repository.addNote(entryId, note)
}