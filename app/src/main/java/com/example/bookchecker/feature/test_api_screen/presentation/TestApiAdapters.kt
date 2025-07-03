package com.example.bookchecker.feature.test_api_screen.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookchecker.databinding.ItemSimpleTextBinding
import com.example.bookchecker.domain.model.Book
import com.example.bookchecker.domain.model.Entry
import com.example.bookchecker.domain.model.Note

/**
 * Адаптер для отображения списка книг.
 */
class BookAdapter : ListAdapter<Book, BookAdapter.VH>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(old: Book, new: Book): Boolean =
                old.id == new.id

            override fun areContentsTheSame(old: Book, new: Book): Boolean =
                old == new
        }
    }

    inner class VH(val binding: ItemSimpleTextBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(
            ItemSimpleTextBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun onBindViewHolder(holder: VH, position: Int) {
        val book = getItem(position)
        holder.binding.tvText.text = "${book.id}: ${book.title}"
    }
}

/**
 * Адаптер для отображения списка записей (entries).
 */
class EntryAdapter : ListAdapter<Entry, EntryAdapter.VH>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Entry>() {
            override fun areItemsTheSame(old: Entry, new: Entry): Boolean =
                old.id == new.id

            override fun areContentsTheSame(old: Entry, new: Entry): Boolean =
                old == new
        }
    }

    inner class VH(val binding: ItemSimpleTextBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(
            ItemSimpleTextBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: VH, position: Int) {
        val entry = getItem(position)
        // Название книги (обязательно, т.к. в модели book: Book)
        val bookTitle = entry.book.title
        // Статус (nullable String)
        val status = entry.status ?: "—"
        // Формируем текст
        holder.binding.tvText.text =
            "${entry.id ?: "?"}: $bookTitle — статус: $status"
    }
}

/**
 * Адаптер для отображения списка заметок (notes).
 */
class NoteAdapter : ListAdapter<Note, NoteAdapter.VH>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(old: Note, new: Note): Boolean =
                old.id == new.id

            override fun areContentsTheSame(old: Note, new: Note): Boolean =
                old == new
        }
    }

    inner class VH(val binding: ItemSimpleTextBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(
            ItemSimpleTextBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun onBindViewHolder(holder: VH, position: Int) {
        val note = getItem(position)
        holder.binding.tvText.text = "${note.id}: ${note.text}"
    }
}
