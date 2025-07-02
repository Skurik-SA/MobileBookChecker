package com.example.bookchecker.feature.book_main.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookchecker.databinding.ItemBookBinding
import com.example.bookchecker.feature.book_list.presentation.BookListAdapter

class BookMainAdapter(
    private val items: List<BookMainItem>
) : RecyclerView.Adapter<BookMainAdapter.BookViewHolder>(){

    inner class BookViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BookMainItem) {
            binding.titleTextView.text = item.title
            binding.authorTextView.text = item.author
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookMainAdapter.BookViewHolder {
        val binding = ItemBookBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookMainAdapter.BookViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}