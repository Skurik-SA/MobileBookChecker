package com.example.bookchecker.domain.usecase.book

import com.example.bookchecker.domain.repository.BookRepository
import javax.inject.Inject


class DeleteBookUseCase @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(id: Long) = repository.deleteBook(id)
}