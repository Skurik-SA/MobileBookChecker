package com.example.bookchecker.data.repository

import com.example.bookchecker.core.session.SessionManager
import com.example.bookchecker.data.local.source.BookLocalDataSource
import com.example.bookchecker.data.remote.source.BookRemoteDataSource
import com.example.bookchecker.domain.model.Book
import com.example.bookchecker.domain.repository.BookRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


class BookRepositoryImpl @Inject constructor(
    private val remote: BookRemoteDataSource,
    private val local: BookLocalDataSource,
    private val sessionManager: SessionManager
) : BookRepository {

    private fun isAuthorized(): Boolean = runBlocking {
        sessionManager.accessToken.firstOrNull() != null
    }

    override suspend fun addBook(book: Book): Book {
        return if (isAuthorized()) {
            val created = remote.createBook(book)
            local.insertBook(created)
            created
        } else {
            local.insertBook(book)
            book
        }
    }

    override suspend fun getBooks(search: String?, orderBy: String?): List<Book> {
        return if (isAuthorized()) {
            try {
                val books = remote.getBooks(search, orderBy)
                books.forEach { local.insertBook(it) }
                books
            } catch (e: Exception) {
                local.getBooks()
            }
        } else {
            local.getBooks()
        }
    }

    override suspend fun getBookById(id: Long): Book? {
        return if (isAuthorized()) {
            try {
                val book = remote.getBookById(id)
                local.insertBook(book)
                book
            } catch (e: Exception) {
                local.getBookById(id)
            }
        } else {
            local.getBookById(id)
        }
    }

    override suspend fun deleteBook(id: Long) {
        if (isAuthorized()) {
            remote.deleteBook(id)
        }
        local.deleteBook(id)
    }
}