package com.example.bookchecker.domain.usecase.book

import com.example.bookchecker.domain.model.Book
import com.example.bookchecker.domain.repository.BookRepository
import javax.inject.Inject

class AddBookUseCase @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(book: Book): Book = repository.addBook(book)
}