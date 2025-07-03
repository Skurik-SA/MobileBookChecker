package com.example.bookchecker.domain.usecase.book

data class BookUseCases (
    val getBooks: GetBooksUseCase,
    val getBookById: GetBookByIdUseCase,
    val addBook: AddBookUseCase,
    val deleteBook: DeleteBookUseCase
)
