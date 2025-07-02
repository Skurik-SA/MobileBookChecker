package com.example.bookchecker.core.di

import android.content.Context
import androidx.room.Room
import com.example.bookchecker.data.local.AppDatabase
import com.example.bookchecker.data.local.dao.BookDao
import com.example.bookchecker.data.local.dao.EntryDao
import com.example.bookchecker.data.local.dao.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides @Singleton
    fun provideDatabase(@ApplicationContext ctx: Context): AppDatabase =
        Room.databaseBuilder(
            ctx,
            AppDatabase::class.java,
            "app_database"
        ).build()

    @Provides
    fun provideBookDao(db: AppDatabase): BookDao = db.bookDao()

    @Provides
    fun provideEntryDao(db: AppDatabase): EntryDao = db.entryDao()

    @Provides
    fun provideNoteDao(db: AppDatabase): NoteDao = db.noteDao()

//    @Provides @Singleton
//    fun provideBookRepository(impl: BookRepositoryImpl): BookRepository = impl
//
//    @Provides @Singleton
//    fun provideEntryRepository(impl: EntryRepositoryImpl): EntryRepository = impl
//
//    @Provides @Singleton
//    fun provideNoteRepository(impl: NoteRepositoryImpl): NoteRepository = impl
}