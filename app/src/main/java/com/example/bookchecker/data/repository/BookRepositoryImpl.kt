package com.example.bookchecker.data.repository

import com.example.bookchecker.data.local.source.BookLocalDataSource
import com.example.bookchecker.data.remote.source.BookRemoteDataSource
import com.example.bookchecker.domain.model.Book
import com.example.bookchecker.domain.repository.BookRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepositoryImpl @Inject constructor(
    private val remote: BookRemoteDataSource,
    private val local: BookLocalDataSource
) : BookRepository {
    override suspend fun addBook(book: Book): Book {
        // Сначала создаём удалённо, затем сохраняем локально
        val created = remote.createBook(book)
        local.insertBook(created)
        return created
    }

    override suspend fun getBooks(search: String?, orderBy: String?): List<Book> {
        // Пытаемся получить из сети, при ошибке возвращаем из кэша
        return try {
            val books = remote.getBooks(search, orderBy)
            // Обновляем локальное хранилище
            books.forEach { local.insertBook(it) }
            books
        } catch (e: Exception) {
            local.getBooks()
        }
    }

    override suspend fun getBookById(id: Long): Book? {
        return try {
            val book = remote.getBookById(id)
            local.insertBook(book)
            book
        } catch (e: Exception) {
            local.getBookById(id)
        }
    }

    override suspend fun deleteBook(id: Long) {
        remote.deleteBook(id)
        local.deleteBook(id)
    }
}