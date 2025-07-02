package com.example.bookchecker.data.remote.source

import com.example.bookchecker.domain.model.Entry


interface EntryRemoteDataSource {
    suspend fun createEntry(entry: Entry): Entry
    suspend fun getEntries(): List<Entry>
    suspend fun getEntryById(id: Long): Entry
    suspend fun updateEntry(entry: Entry): Entry
    suspend fun deleteEntry(id: Long)
}