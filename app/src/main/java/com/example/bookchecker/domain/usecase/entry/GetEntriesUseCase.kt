package com.example.bookchecker.domain.usecase.entry

import com.example.bookchecker.domain.model.Entry
import com.example.bookchecker.domain.repository.EntryRepository
import javax.inject.Inject

class GetEntriesUseCase @Inject constructor(
    private val repository: EntryRepository
) {
    suspend operator fun invoke(): List<Entry> = repository.getEntries()
}