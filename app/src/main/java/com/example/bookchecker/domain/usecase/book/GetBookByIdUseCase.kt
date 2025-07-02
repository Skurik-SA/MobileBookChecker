package com.example.bookchecker.domain.usecase.book

import com.example.bookchecker.domain.model.Book
import com.example.bookchecker.domain.repository.BookRepository
import javax.inject.Inject

class GetBookByIdUseCase @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(id: Long): Book? = repository.getBookById(id)
}