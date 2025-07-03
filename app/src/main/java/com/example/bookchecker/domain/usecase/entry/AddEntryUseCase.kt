package com.example.bookchecker.domain.usecase.entry

import com.example.bookchecker.data.remote.dto.EntryDto
import com.example.bookchecker.domain.model.Entry
import com.example.bookchecker.domain.repository.EntryRepository
import javax.inject.Inject

class AddEntryUseCase @Inject constructor(
    private val repository: EntryRepository
) {
    // Принимаем доменную Entry, возвращаем доменную Entry
    suspend operator fun invoke(entry: Entry): Entry = repository.addEntry(entry)
}