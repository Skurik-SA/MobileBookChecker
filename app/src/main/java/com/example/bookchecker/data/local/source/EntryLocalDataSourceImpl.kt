package com.example.bookchecker.data.local.source

import com.example.bookchecker.data.local.dao.EntryDao
import com.example.bookchecker.data.local.dao.NoteDao
import com.example.bookchecker.data.mapper.BookMapper
import com.example.bookchecker.data.mapper.EntryMapper
import com.example.bookchecker.data.mapper.NoteMapper
import com.example.bookchecker.domain.model.Entry
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EntryLocalDataSourceImpl @Inject constructor(
    private val entryDao: EntryDao,
    private val noteDao: NoteDao
) : EntryLocalDataSource {
    override suspend fun insertEntry(entry: Entry): Long {
        val entity = EntryMapper.domainToDto(entry).let(EntryMapper::dtoToEntity)
        return entryDao.insert(entity)
    }

    override suspend fun getEntries(): List<Entry> =
        entryDao.getAllWithBook().map { (entryEntity, bookEntity) ->
            val entry = EntryMapper.entityToDomain(
                entryEntity,
                BookMapper.entityToDomain(bookEntity),
                noteDao.getByEntryId(entryEntity.id).map { noteEntity ->
                    NoteMapper.entityToDomain(noteEntity)
                }
            )
            entry
        }

    override suspend fun getEntryById(id: Long): Entry? =
        entryDao.getByIdWithBook(id)?.let { (entryEntity, bookEntity) ->
            EntryMapper.entityToDomain(
                entryEntity,
                BookMapper.entityToDomain(bookEntity),
                noteDao.getByEntryId(id).map(NoteMapper::entityToDomain)
            )
        }

    override suspend fun updateEntry(entry: Entry) {
        val entity = EntryMapper.domainToDto(entry).let(EntryMapper::dtoToEntity)
        entryDao.update(entity)
    }

    override suspend fun deleteEntry(id: Long) = entryDao.delete(id)
}