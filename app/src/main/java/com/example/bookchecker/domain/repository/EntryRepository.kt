package com.example.bookchecker.domain.repository

import com.example.bookchecker.data.remote.dto.EntryDto
import com.example.bookchecker.domain.model.Entry

interface EntryRepository {
    suspend fun addEntry(entry: Entry): Entry
    suspend fun getEntries(): List<Entry>
    suspend fun getEntryById(id: Long): Entry?
    suspend fun updateEntry(entry: Entry): Entry
    suspend fun deleteEntry(id: Long)
}