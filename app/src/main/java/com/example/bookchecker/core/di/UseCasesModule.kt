package com.example.bookchecker.core.di
//
//import com.example.bookchecker.domain.repository.BookRepository
//import com.example.bookchecker.domain.repository.EntryRepository
//import com.example.bookchecker.domain.repository.NoteRepository
//import com.example.bookchecker.domain.usecase.book.AddBookUseCase
//import com.example.bookchecker.domain.usecase.book.BookUseCases
//import com.example.bookchecker.domain.usecase.book.DeleteBookUseCase
//import com.example.bookchecker.domain.usecase.book.GetBookByIdUseCase
//import com.example.bookchecker.domain.usecase.book.GetBooksUseCase
//import com.example.bookchecker.domain.usecase.entry.AddEntryUseCase
//import com.example.bookchecker.domain.usecase.entry.DeleteEntryUseCase
//import com.example.bookchecker.domain.usecase.entry.EntryUseCases
//import com.example.bookchecker.domain.usecase.entry.GetEntriesUseCase
//import com.example.bookchecker.domain.usecase.entry.GetEntryByIdUseCase
//import com.example.bookchecker.domain.usecase.entry.UpdateEntryUseCase
//import com.example.bookchecker.domain.usecase.note.AddNoteUseCase
//import com.example.bookchecker.domain.usecase.note.DeleteNoteUseCase
//import com.example.bookchecker.domain.usecase.note.GetNotesUseCase
//import com.example.bookchecker.domain.usecase.note.NoteUseCases
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object UseCasesModule {
//
//    @Provides
//    @Singleton
//    fun provideBookUseCases(repo: BookRepository) = BookUseCases(
//        getBooks = GetBooksUseCase(repo),
//        getBookById = GetBookByIdUseCase(repo),
//        addBook = AddBookUseCase(repo),
//        deleteBook = DeleteBookUseCase(repo)
//    )
//
//    @Provides
//    @Singleton
//    fun provideEntryUseCases(repo: EntryRepository) = EntryUseCases(
//        getEntries = GetEntriesUseCase(repo),
//        getEntryById = GetEntryByIdUseCase(repo),
//        addEntry = AddEntryUseCase(repo),
//        updateEntry = UpdateEntryUseCase(repo),
//        deleteEntry = DeleteEntryUseCase(repo)
//    )
//
//    @Provides @Singleton
//    fun provideNoteUseCases(repo: NoteRepository) = NoteUseCases(
//        getNotes = GetNotesUseCase(repo),
//        addNote = AddNoteUseCase(repo),
//        deleteNote = DeleteNoteUseCase(repo)
//    )
//}