package com.example.bookchecker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bookchecker.data.local.dao.BookDao
import com.example.bookchecker.data.local.dao.EntryDao
import com.example.bookchecker.data.local.dao.NoteDao
import com.example.bookchecker.data.local.entity.BookEntity
import com.example.bookchecker.data.local.entity.EntryEntity
import com.example.bookchecker.data.local.entity.NoteEntity
import com.example.bookchecker.data.local.entity.GenreEntity

@Database(
    entities = [BookEntity::class, EntryEntity::class, NoteEntity::class, GenreEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun entryDao(): EntryDao
    abstract fun noteDao(): NoteDao
}