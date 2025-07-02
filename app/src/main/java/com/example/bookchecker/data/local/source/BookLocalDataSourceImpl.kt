package com.example.bookchecker.data.local.source

import com.example.bookchecker.data.local.dao.BookDao
import com.example.bookchecker.data.mapper.BookMapper
import com.example.bookchecker.domain.model.Book
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookLocalDataSourceImpl @Inject constructor(
    private val bookDao: BookDao
) : BookLocalDataSource {
    override suspend fun insertBook(book: Book): Long {
        val entity = BookMapper.domainToEntity(book)
        return bookDao.insert(entity)
    }

    override suspend fun getBooks(): List<Book> =
        bookDao.getAll().map(BookMapper::entityToDomain)

    override suspend fun getBookById(id: Long): Book? =
        bookDao.getById(id)?.let(BookMapper::entityToDomain)

    override suspend fun deleteBook(id: Long) = bookDao.delete(id)
}