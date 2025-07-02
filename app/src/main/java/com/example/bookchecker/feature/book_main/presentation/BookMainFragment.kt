package com.example.bookchecker.feature.book_main.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookchecker.databinding.FragmentBookMainBinding
import com.example.bookchecker.feature.book_list.presentation.BookListItem

class BookMainFragment : Fragment() {
    private var _binding: FragmentBookMainBinding? = null
    private val binding get() = _binding!!

    private val mockBooks = listOf(
        BookMainItem(1, "Война и мир", "Лев Толстой"),
        BookMainItem(2, "Преступление и наказание", "Фёдор Достоевский"),
        BookMainItem(3, "Мастер и Маргарита", "Михаил Булгаков"),
        BookMainItem(4, "Над пропастью во ржи", "Дж. Д. Сэлинджер")
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBookMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = BookMainAdapter(mockBooks)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
