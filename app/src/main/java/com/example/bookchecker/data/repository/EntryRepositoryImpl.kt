package com.example.bookchecker.data.repository

import com.example.bookchecker.data.local.source.EntryLocalDataSource
import com.example.bookchecker.data.remote.source.EntryRemoteDataSource
import com.example.bookchecker.domain.model.Entry
import com.example.bookchecker.domain.repository.EntryRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EntryRepositoryImpl @Inject constructor(
    private val remote: EntryRemoteDataSource,
    private val local: EntryLocalDataSource
) : EntryRepository {
    override suspend fun addEntry(entry: Entry): Entry {
        val created = remote.createEntry(entry)
        local.insertEntry(created)
        return created
    }

    override suspend fun getEntries(): List<Entry> {
        return try {
            val entries = remote.getEntries()
            entries.forEach { local.insertEntry(it) }
            entries
        } catch (e: Exception) {
            local.getEntries()
        }
    }

    override suspend fun getEntryById(id: Long): Entry? {
        return try {
            val entry = remote.getEntryById(id)
            local.insertEntry(entry)
            entry
        } catch (e: Exception) {
            local.getEntryById(id)
        }
    }

    override suspend fun updateEntry(entry: Entry): Entry {
        val updated = remote.updateEntry(entry)
        local.updateEntry(updated)
        return updated
    }

    override suspend fun deleteEntry(id: Long) {
        remote.deleteEntry(id)
        local.deleteEntry(id)
    }
}
