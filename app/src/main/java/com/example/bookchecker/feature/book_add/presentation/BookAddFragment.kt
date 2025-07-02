package com.example.bookchecker.feature.book_add.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bookchecker.databinding.FragmentBookAddBinding

class BookAddFragment : Fragment() {
    private var _binding: FragmentBookAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBookAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title.text = "Добавление книги"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
