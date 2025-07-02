package com.example.bookchecker.data.local.source

import com.example.bookchecker.domain.model.Book


interface BookLocalDataSource {
    suspend fun insertBook(book: Book): Long
    suspend fun getBooks(): List<Book>
    suspend fun getBookById(id: Long): Book?
    suspend fun deleteBook(id: Long)
}
