package com.example.bookchecker.data.remote.source

import com.example.bookchecker.data.mapper.BookMapper
import com.example.bookchecker.data.mapper.EntryMapper
import com.example.bookchecker.data.mapper.NoteMapper
import com.example.bookchecker.data.remote.api.EntryApi
import com.example.bookchecker.data.remote.dto.EntryDto
import com.example.bookchecker.domain.model.Entry
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EntryRemoteDataSourceImpl @Inject constructor(
    private val api: EntryApi
) : EntryRemoteDataSource {
    override suspend fun createEntry(dto: EntryDto): EntryDto =
        api.create(dto)

    override suspend fun getEntries(): List<EntryDto> =
        api.list()

    override suspend fun getEntryById(id: Long): EntryDto =
        api.get(id)

    override suspend fun updateEntry(id: Long, dto: EntryDto): EntryDto =
        api.update(id, dto)

    override suspend fun deleteEntry(id: Long) =
        api.delete(id)
}