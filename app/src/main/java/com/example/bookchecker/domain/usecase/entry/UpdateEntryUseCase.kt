package com.example.bookchecker.domain.usecase.entry

import com.example.bookchecker.domain.model.Entry
import com.example.bookchecker.domain.repository.EntryRepository
import javax.inject.Inject


class UpdateEntryUseCase @Inject constructor(
    private val repository: EntryRepository
) {
    suspend operator fun invoke(entry: Entry): Entry = repository.updateEntry(entry)
}