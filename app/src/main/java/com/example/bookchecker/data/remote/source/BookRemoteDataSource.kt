package com.example.bookchecker.data.remote.source

import com.example.bookchecker.domain.model.Book

interface BookRemoteDataSource {
    suspend fun createBook(book: Book): Book
    suspend fun getBooks(search: String? = null, orderBy: String? = null): List<Book>
    suspend fun getBookById(id: Long): Book
    suspend fun deleteBook(id: Long)
}