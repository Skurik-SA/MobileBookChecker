package com.example.bookchecker.feature.book_list.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookchecker.databinding.FragmentBookListBinding

class BookListFragment : Fragment() {

    private var _binding: FragmentBookListBinding? = null
    private val binding get() = _binding!!

    private val mockBooks = listOf(
        BookListItem(1, "Война и мир", "Лев Толстой"),
        BookListItem(2, "Преступление и наказание", "Фёдор Достоевский"),
        BookListItem(3, "Мастер и Маргарита", "Михаил Булгаков"),
        BookListItem(4, "Над пропастью во ржи", "Дж. Д. Сэлинджер")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = BookListAdapter(mockBooks)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}