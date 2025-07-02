package com.example.bookchecker.data.local.source

import com.example.bookchecker.domain.model.Entry

interface EntryLocalDataSource {
    suspend fun insertEntry(entry: Entry): Long
    suspend fun getEntries(): List<Entry>
    suspend fun getEntryById(id: Long): Entry?
    suspend fun updateEntry(entry: Entry)
    suspend fun deleteEntry(id: Long)
}