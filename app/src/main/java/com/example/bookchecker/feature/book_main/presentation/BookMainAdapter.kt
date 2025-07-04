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
) : ListAdapter<Entry, BookMainAdapter.VH>(object: DiffUtil.ItemCallback<Entry>() {
    override fun areItemsTheSame(o: Entry, n: Entry) = o.id == n.id
    override fun areContentsTheSame(o: Entry, n: Entry) = o == n
}) {
    inner class VH(val b: ItemBookBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(e: Entry) {
            b.tvTitle.text = e.book.title
            b.tvAuthor.text = e.book.author
            // Обработка обложки: если URL есть — грузим, иначе ставим плейсхолдер
            val url = e.book.coverUrl
            if (!url.isNullOrBlank()) {
                b.ivCover.load(url) {
                    placeholder(R.drawable.ic_placeholder)
                    error(R.drawable.ic_placeholder)
                }
            } else {
                // Нет обложки — сразу плейсхолдер
                b.ivCover.setImageResource(R.drawable.ic_placeholder)
            }
            // выставляем прогресс
            val percent = e.progress?.let { it.currentPage * 100 / e.book.totalPages } ?: 0
            b.progressBar.progress = percent

            b.root.setOnClickListener { onClick(e) }
        }
    }
    override fun onCreateViewHolder(p: ViewGroup, t: Int) = VH(
        ItemBookBinding.inflate(LayoutInflater.from(p.context), p, false)
    )
    override fun onBindViewHolder(h: VH, pos: Int) = h.bind(getItem(pos))
}
