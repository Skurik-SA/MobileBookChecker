package com.example.bookchecker.feature.book_stats.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bookchecker.databinding.FragmentBookStatsBinding

class BookStatsFragment : Fragment() {
    private var _binding: FragmentBookStatsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBookStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title.text = "Статистика чтения"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
