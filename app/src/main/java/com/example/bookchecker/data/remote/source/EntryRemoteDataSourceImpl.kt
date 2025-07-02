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

    private fun mapDtoToDomain(dto: EntryDto): Entry {
        val bookDomain = BookMapper.dtoToEntity(dto.book).let(BookMapper::entityToDomain)
        val notesDomain = dto.notes.map { noteDto ->
            NoteMapper.dtoToEntity(noteDto, dto.id).let(NoteMapper::entityToDomain)
        }
        val entryEntity = EntryMapper.dtoToEntity(dto)
        return EntryMapper.entityToDomain(entryEntity, bookDomain, notesDomain)
    }

    override suspend fun createEntry(entry: Entry): Entry =
        api.create(EntryMapper.domainToDto(entry)).let(::mapDtoToDomain)

    override suspend fun getEntries(): List<Entry> =
        api.list().map(::mapDtoToDomain)

    override suspend fun getEntryById(id: Long): Entry =
        api.get(id).let(::mapDtoToDomain)

    override suspend fun updateEntry(entry: Entry): Entry =
        api.update(entry.id, EntryMapper.domainToDto(entry)).let(::mapDtoToDomain)

    override suspend fun deleteEntry(id: Long) {
        api.delete(id)
    }
}
