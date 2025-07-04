package com.example.bookchecker.feature.book_main.presentation

import coil.load
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookchecker.R
import com.example.bookchecker.databinding.ItemBookBinding
import com.example.bookchecker.domain.model.Entry
import com.example.bookchecker.feature.book_list.presentation.BookListAdapter

class BookMainAdapter(
    private val onClick: (Entry) -> Unit
) : ListAdapter<Entry, BookMainAdapter.BookViewHolder>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Entry>() {
            override fun areItemsTheSame(old: Entry, new: Entry) = old.id == new.id
            override fun areContentsTheSame(old: Entry, new: Entry) = old == new
        }
    }

    inner class BookViewHolder(private val b: ItemBookBinding)
        : RecyclerView.ViewHolder(b.root) {

        fun bind(e: Entry) {
            b.tvTitle.text = e.book.title
            b.tvAuthor.text = e.book.author
            b.ivCover.load(e.book.coverUrl) {
                placeholder(R.drawable.ic_placeholder)
                error(R.drawable.ic_placeholder)
            }
            val progress = e.progress?.let {
                if (e.book.totalPages != 0) (it.currentPage * 100) / e.book.totalPages else 0
            } ?: 0

            b.progressBar.progress = progress
            b.root.setOnClickListener { onClick(e) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BookViewHolder(
            ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) =
        holder.bind(getItem(position))
}
