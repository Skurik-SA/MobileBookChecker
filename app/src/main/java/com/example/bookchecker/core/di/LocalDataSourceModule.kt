package com.example.bookchecker.core.di

import com.example.bookchecker.data.local.source.BookLocalDataSource
import com.example.bookchecker.data.local.source.BookLocalDataSourceImpl
import com.example.bookchecker.data.local.source.EntryLocalDataSource
import com.example.bookchecker.data.local.source.EntryLocalDataSourceImpl
import com.example.bookchecker.data.local.source.NoteLocalDataSource
import com.example.bookchecker.data.local.source.NoteLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule { // Или DatabaseModule

    @Binds
    @Singleton
    abstract fun bindBookLocalDataSource(
        impl: BookLocalDataSourceImpl
    ): BookLocalDataSource

    @Binds
    @Singleton
    abstract fun bindEntryLocalDataSource(
        impl: EntryLocalDataSourceImpl
    ): EntryLocalDataSource

    @Binds
    @Singleton
    abstract fun bindNoteLocalDataSource(
        impl: NoteLocalDataSourceImpl
    ): NoteLocalDataSource
}