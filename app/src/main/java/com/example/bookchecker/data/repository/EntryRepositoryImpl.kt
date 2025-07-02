package com.example.bookchecker.data.repository

import com.example.bookchecker.core.session.SessionManager
import com.example.bookchecker.data.local.source.EntryLocalDataSource
import com.example.bookchecker.data.remote.source.EntryRemoteDataSource
import com.example.bookchecker.domain.model.Entry
import com.example.bookchecker.domain.repository.EntryRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EntryRepositoryImpl @Inject constructor(
    private val remote: EntryRemoteDataSource,
    private val local: EntryLocalDataSource,
    private val sessionManager: SessionManager
) : EntryRepository {

    private fun isAuthorized(): Boolean = runBlocking {
        sessionManager.accessToken.firstOrNull() != null
    }


    override suspend fun addEntry(entry: Entry): Entry {
        return if (isAuthorized()) {
            val created = remote.createEntry(entry)
            local.insertEntry(created)
            created
        } else {
            local.insertEntry(entry)
            entry
        }
    }

    override suspend fun getEntries(): List<Entry> {
        return if (isAuthorized()) {
            try {
                val entries = remote.getEntries()
                entries.forEach { local.insertEntry(it) }
                entries
            } catch (e: Exception) {
                local.getEntries()
            }
        } else {
            local.getEntries()
        }
    }

    override suspend fun getEntryById(id: Long): Entry? {
        return if (isAuthorized()) {
            try {
                val entry = remote.getEntryById(id)
                local.insertEntry(entry)
                entry
            } catch (e: Exception) {
                local.getEntryById(id)
            }
        } else {
            local.getEntryById(id)
        }
    }

    override suspend fun updateEntry(entry: Entry): Entry {
        return if (isAuthorized()) {
            val updated = remote.updateEntry(entry)
            local.updateEntry(updated)
            updated
        } else {
            local.updateEntry(entry)
            entry
        }
    }

    override suspend fun deleteEntry(id: Long) {
        if (isAuthorized()) {
            remote.deleteEntry(id)
        }
        local.deleteEntry(id)
    }
}
