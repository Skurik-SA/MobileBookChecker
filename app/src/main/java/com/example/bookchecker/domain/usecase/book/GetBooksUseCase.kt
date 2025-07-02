package com.example.bookchecker.domain.usecase.book

import com.example.bookchecker.domain.model.Book
import com.example.bookchecker.domain.repository.BookRepository
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(search: String? = null, orderBy: String? = null): List<Book> =
        repository.getBooks(search, orderBy)
}
