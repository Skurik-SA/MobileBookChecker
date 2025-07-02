package com.example.bookchecker.domain.repository

import com.example.bookchecker.domain.model.Book

interface BookRepository {
    suspend fun addBook(book: Book): Book
    suspend fun getBooks(search: String? = null, orderBy: String? = null): List<Book>
    suspend fun getBookById(id: Long): Book?
    suspend fun deleteBook(id: Long)
}