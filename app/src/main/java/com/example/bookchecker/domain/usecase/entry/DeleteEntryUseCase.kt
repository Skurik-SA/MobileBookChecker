package com.example.bookchecker.domain.usecase.entry

import com.example.bookchecker.domain.repository.EntryRepository
import javax.inject.Inject

class DeleteEntryUseCase @Inject constructor(
    private val repository: EntryRepository
) {
    suspend operator fun invoke(id: Long) = repository.deleteEntry(id)
}