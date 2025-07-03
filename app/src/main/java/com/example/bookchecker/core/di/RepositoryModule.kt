package com.example.bookchecker.core.di

import com.example.bookchecker.data.repository.BookRepositoryImpl
import com.example.bookchecker.data.repository.EntryRepositoryImpl
import com.example.bookchecker.data.repository.NoteRepositoryImpl
import com.example.bookchecker.domain.repository.BookRepository
import com.example.bookchecker.domain.repository.EntryRepository
import com.example.bookchecker.domain.repository.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBookRepository(
        bookRepositoryImpl: BookRepositoryImpl
    ): BookRepository

    @Binds
    @Singleton
    abstract fun bindEntryRepository(
        entryRepositoryImpl: EntryRepositoryImpl
    ): EntryRepository

    @Binds
    @Singleton
    abstract fun bindNoteRepository(
        noteRepositoryImpl: NoteRepositoryImpl
    ): NoteRepository
}