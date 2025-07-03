package com.example.bookchecker.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = 0L,

    val title: String,

    val author: String,

    @ColumnInfo(name = "total_pages") val totalPages: Int,

    val description: String?,

    @ColumnInfo(name = "cover_url") val coverUrl: String?,

    /** Жанры через запятую */
    val genres: String
)
