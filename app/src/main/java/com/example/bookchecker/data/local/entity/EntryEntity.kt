package com.example.bookchecker.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "entries",
    foreignKeys = [
        ForeignKey(
            entity = BookEntity::class,
            parentColumns = ["id"],
            childColumns = ["book_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class EntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = 0L,

    @ColumnInfo(name = "book_id") val bookId: Long,

    val status: String,

    @ColumnInfo(name = "started_at") val startedAt: String?,

    @ColumnInfo(name = "finished_at") val finishedAt: String?,

    @ColumnInfo(name = "current_page") val currentPage: Int,

    val percent: Double? = 0.0,

    @ColumnInfo(name = "progress_updated_at") val progressUpdatedAt: String?,

    @ColumnInfo(name = "rating_score") val ratingScore: Int?,

    @ColumnInfo(name = "rating_scale") val ratingScale: Int?
)