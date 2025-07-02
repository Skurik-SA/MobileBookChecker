package com.example.bookchecker.data.remote.source

import com.example.bookchecker.data.mapper.BookMapper
import com.example.bookchecker.data.remote.api.BookApi
import com.example.bookchecker.domain.model.Book
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRemoteDataSourceImpl @Inject constructor(
    private val api: BookApi
) : BookRemoteDataSource {
    override suspend fun createBook(book: Book): Book {
        val dto = BookMapper.domainToDto(book)
        val response = api.create(dto)
        val entity = BookMapper.dtoToEntity(response)
        return BookMapper.entityToDomain(entity)
    }

    override suspend fun getBooks(search: String?, orderBy: String?): List<Book> =
        api.list(search, orderBy)
            .map { dto -> BookMapper.entityToDomain(BookMapper.dtoToEntity(dto)) }

    override suspend fun getBookById(id: Long): Book =
        api.get(id).let { dto -> BookMapper.entityToDomain(BookMapper.dtoToEntity(dto)) }

    override suspend fun deleteBook(id: Long) = api.delete(id)
}