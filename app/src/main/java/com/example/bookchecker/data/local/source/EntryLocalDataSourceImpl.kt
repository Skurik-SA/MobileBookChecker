package com.example.bookchecker.data.local.source

import com.example.bookchecker.data.local.dao.BookDao
import com.example.bookchecker.data.local.dao.EntryDao
import com.example.bookchecker.data.local.dao.NoteDao
import com.example.bookchecker.data.mapper.BookMapper
import com.example.bookchecker.data.mapper.EntryMapper
import com.example.bookchecker.data.mapper.NoteMapper
import com.example.bookchecker.data.remote.dto.EntryDto
import com.example.bookchecker.domain.model.Entry
import com.example.bookchecker.domain.model.Note
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EntryLocalDataSourceImpl @Inject constructor(
    private val entryDao: EntryDao,
    private val noteDao: NoteDao,
    private val bookDao: BookDao
) : EntryLocalDataSource {

    override suspend fun insertEntry(entry: Entry): Long {
        // 1. Вставляем книгу и получаем её ID
        val bookDto = BookMapper.domainToDto(entry.book)
        val bookEntity = BookMapper.dtoToEntity(bookDto)

        val bookId = if (bookEntity.id == null) {
            bookDao.insert(bookEntity) // возвращает новый id
        } else {
            // проверим, существует ли книга в БД — если нет, вставим
            val existing = bookDao.getById(bookEntity.id)
            if (existing == null) bookDao.insert(bookEntity) else bookEntity.id
        }

        // 2. Обновляем entry с bookId
        val entryWithBookId = entry.copy(book = entry.book.copy(id = bookId))

        // 3. Маппим и сохраняем Entry
        val dto = EntryMapper.domainToDto(entryWithBookId)
        val entity = EntryMapper.dtoToEntity(dto)

        return entryDao.insert(entity)
    }

    override suspend fun getEntries(): List<Entry> =
        entryDao.getAllWithBook().map { pair ->
            val (entryEntity, bookEntity) = pair
            val notes: List<Note> = noteDao
                .getByEntryId(entryEntity.id)
                .map(NoteMapper::entityToDomain)
            EntryMapper.entityToDomain(
                entryEntity,
                BookMapper.entityToDomain(bookEntity),
                notes
            )
        }

    override suspend fun getEntryById(id: Long): Entry? =
        entryDao.getByIdWithBook(id)?.let { (entryEntity, bookEntity) ->
            val notes = noteDao.getByEntryId(id).map(NoteMapper::entityToDomain)
            EntryMapper.entityToDomain(
                entryEntity,
                BookMapper.entityToDomain(bookEntity),
                notes
            )
        }

    override suspend fun updateEntry(entry: Entry) {
        val entity = EntryMapper.domainToEntity(entry)
        entryDao.update(entity)
    }

    override suspend fun deleteEntry(id: Long) =
        entryDao.delete(id)
}