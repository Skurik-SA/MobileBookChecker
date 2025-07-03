package com.example.bookchecker.data.remote.source

import com.example.bookchecker.data.remote.dto.EntryDto
import com.example.bookchecker.domain.model.Entry


interface EntryRemoteDataSource {
    suspend fun createEntry(dto: EntryDto): EntryDto
    suspend fun getEntries(): List<EntryDto>
    suspend fun getEntryById(id: Long): EntryDto
    suspend fun updateEntry(id: Long, dto: EntryDto): EntryDto
    suspend fun deleteEntry(id: Long)
}