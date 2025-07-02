package com.example.bookchecker.data.local.dao

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import androidx.room.Update
import com.example.bookchecker.data.local.entity.BookEntity
import com.example.bookchecker.data.local.entity.EntryEntity

/**
 * Для выборки Entry вместе с соответствующей BookEntity
 */
data class EntryWithBook(
    @Embedded val entry: EntryEntity,
    @Relation(parentColumn = "book_id", entityColumn = "id")
    val book: BookEntity
)

@Dao
interface EntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: EntryEntity): Long

    @Update
    suspend fun update(entry: EntryEntity)

    @Query("DELETE FROM entries WHERE id = :id")
    suspend fun delete(id: Long)

    @Transaction
    @Query("SELECT * FROM entries")
    suspend fun getAllWithBook(): List<EntryWithBook>

    @Transaction
    @Query("SELECT * FROM entries WHERE id = :id")
    suspend fun getByIdWithBook(id: Long): EntryWithBook?
}