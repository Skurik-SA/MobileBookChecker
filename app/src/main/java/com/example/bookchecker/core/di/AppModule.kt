package com.example.bookchecker.core.di

import android.content.Context
import com.example.bookchecker.core.session.SessionManager
import com.example.bookchecker.data.remote.api.AuthApi
import com.example.bookchecker.data.repository.AuthRepositoryImpl
import com.example.bookchecker.domain.repository.AuthRepository
import com.example.bookchecker.domain.repository.BookRepository
import com.example.bookchecker.domain.repository.EntryRepository
import com.example.bookchecker.domain.repository.NoteRepository
import com.example.bookchecker.domain.usecase.auth.AuthUseCases
import com.example.bookchecker.domain.usecase.auth.IsAuthorizedUseCase
import com.example.bookchecker.domain.usecase.auth.LoginUseCase
import com.example.bookchecker.domain.usecase.auth.LogoutUseCase
import com.example.bookchecker.domain.usecase.auth.RefreshTokenUseCase
import com.example.bookchecker.domain.usecase.auth.RegisterUseCase
import com.example.bookchecker.domain.usecase.book.AddBookUseCase
import com.example.bookchecker.domain.usecase.book.BookUseCases
import com.example.bookchecker.domain.usecase.book.DeleteBookUseCase
import com.example.bookchecker.domain.usecase.book.GetBookByIdUseCase
import com.example.bookchecker.domain.usecase.book.GetBooksUseCase
import com.example.bookchecker.domain.usecase.entry.AddEntryUseCase
import com.example.bookchecker.domain.usecase.entry.DeleteEntryUseCase
import com.example.bookchecker.domain.usecase.entry.EntryUseCases
import com.example.bookchecker.domain.usecase.entry.GetEntriesUseCase
import com.example.bookchecker.domain.usecase.entry.GetEntryByIdUseCase
import com.example.bookchecker.domain.usecase.entry.UpdateEntryUseCase
import com.example.bookchecker.domain.usecase.note.AddNoteUseCase
import com.example.bookchecker.domain.usecase.note.DeleteNoteUseCase
import com.example.bookchecker.domain.usecase.note.GetNotesUseCase
import com.example.bookchecker.domain.usecase.note.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager =
        SessionManager(context)

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApi,
        sessionManager: SessionManager
    ): AuthRepository = AuthRepositoryImpl(api, sessionManager)

    @Provides
    @Singleton
    fun provideAuthUseCases(
        repository: AuthRepository
    ): AuthUseCases = AuthUseCases(
        login = LoginUseCase(repository),
        register = RegisterUseCase(repository),
        refresh = RefreshTokenUseCase(repository),
        logout = LogoutUseCase(repository),
        isAuthorized = IsAuthorizedUseCase(repository)
    )

    @Provides
    @Singleton
    fun provideBookUseCases(
        repository: BookRepository
    ): BookUseCases = BookUseCases(
        getBooks = GetBooksUseCase(repository),
        getBookById = GetBookByIdUseCase(repository),
        addBook = AddBookUseCase(repository),
        deleteBook = DeleteBookUseCase(repository)
    )

    @Provides
    @Singleton
    fun provideEntryUseCases(
        repository: EntryRepository
    ): EntryUseCases = EntryUseCases(
        getEntries = GetEntriesUseCase(repository),
        getEntryById = GetEntryByIdUseCase(repository),
        addEntry = AddEntryUseCase(repository),
        deleteEntry = DeleteEntryUseCase(repository),
        updateEntry = UpdateEntryUseCase(repository)
    )

    @Provides
    @Singleton
    fun provideNoteUseCases(
        repository: NoteRepository
    ): NoteUseCases = NoteUseCases(
        getNotes = GetNotesUseCase(repository),
        addNote = AddNoteUseCase(repository),
        deleteNote = DeleteNoteUseCase(repository)
    )
}
