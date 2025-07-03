package com.example.bookchecker.domain.usecase.entry

data class EntryUseCases (
    val getEntries: GetEntriesUseCase,
    val getEntryById: GetEntryByIdUseCase,
    val addEntry: AddEntryUseCase,
    val deleteEntry: DeleteEntryUseCase,
    val updateEntry: UpdateEntryUseCase
)
