package com.example.bookchecker.core.di

import com.example.bookchecker.data.remote.source.BookRemoteDataSource
import com.example.bookchecker.data.remote.source.BookRemoteDataSourceImpl
import com.example.bookchecker.data.remote.source.EntryRemoteDataSource
import com.example.bookchecker.data.remote.source.EntryRemoteDataSourceImpl
import com.example.bookchecker.data.remote.source.NoteRemoteDataSource
import com.example.bookchecker.data.remote.source.NoteRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindBookRemoteDataSource(
        impl: BookRemoteDataSourceImpl
    ): BookRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindEntryRemoteRepository(
        impl: EntryRemoteDataSourceImpl
    ): EntryRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindNoteRemoteRepository(
        impl: NoteRemoteDataSourceImpl
    ): NoteRemoteDataSource
}