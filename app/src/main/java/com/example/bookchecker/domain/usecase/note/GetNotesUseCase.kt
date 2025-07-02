package com.example.bookchecker.domain.usecase.note

import com.example.bookchecker.domain.model.Note
import com.example.bookchecker.domain.repository.NoteRepository
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(entryId: Long): List<Note> = repository.getNotes(entryId)
}
