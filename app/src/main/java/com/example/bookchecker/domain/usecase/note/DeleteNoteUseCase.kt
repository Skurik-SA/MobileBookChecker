package com.example.bookchecker.domain.usecase.note

import com.example.bookchecker.domain.repository.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(entryId: Long, noteId: Long) =
        repository.deleteNote(entryId, noteId)
}
