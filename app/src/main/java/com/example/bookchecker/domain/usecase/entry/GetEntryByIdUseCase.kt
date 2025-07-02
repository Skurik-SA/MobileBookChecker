package com.example.bookchecker.domain.usecase.entry

import com.example.bookchecker.domain.model.Entry
import com.example.bookchecker.domain.repository.EntryRepository
import javax.inject.Inject

class GetEntryByIdUseCase @Inject constructor(
    private val repository: EntryRepository
) {
    suspend operator fun invoke(id: Long): Entry? = repository.getEntryById(id)
}