package com.example.bookchecker.feature.book_detail.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bookchecker.R

class BookDetailFragment : Fragment(R.layout.fragment_book_detail) {

    // Если нужна ViewModel:
    // private val vm: BookDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val entryId = arguments?.getLong("entryId") ?: return

        // TODO: Поднять данные по entryId и заполнить UI
    }
}
