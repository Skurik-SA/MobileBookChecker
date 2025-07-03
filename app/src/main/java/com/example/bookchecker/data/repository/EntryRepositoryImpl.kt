package com.example.bookchecker.data.repository

import com.example.bookchecker.core.session.SessionManager
import com.example.bookchecker.data.local.source.EntryLocalDataSource
import com.example.bookchecker.data.mapper.EntryMapper
import com.example.bookchecker.data.remote.dto.EntryDto
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
            val createdDto = remote.createEntry(EntryMapper.domainToDto(entry))
            val created = EntryMapper.dtoToDomain(createdDto)
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
                val dtos = remote.getEntries()
                val domains = dtos.map(EntryMapper::dtoToDomain)
                // Вместо domains.forEach(local::insertEntry)
                for (entry in domains) {
                    local.insertEntry(entry)  // этот suspend-функция теперь корректно вызывается
                }
                domains
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
                val dto = remote.getEntryById(id)
                val domain = EntryMapper.dtoToDomain(dto)
                local.updateEntry(domain)
                domain
            } catch (e: Exception) {
                local.getEntryById(id)
            }
        } else {
            local.getEntryById(id)
        }
    }

    override suspend fun updateEntry(entry: Entry): Entry {
        return if (isAuthorized()) {
            val updatedDto = remote.updateEntry(entry.id!!, EntryMapper.domainToDto(entry))
            val updated = EntryMapper.dtoToDomain(updatedDto)
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