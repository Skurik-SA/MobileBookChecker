package com.example.bookchecker.feature.book_main.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookchecker.R
import com.example.bookchecker.databinding.FragmentBookMainBinding
import com.example.bookchecker.domain.model.BookStatus
import com.example.bookchecker.feature.book_list.presentation.BookListItem
import com.example.bookchecker.feature.book_main.presentation.viewmodel.BookMainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class BookMainFragment : Fragment(R.layout.fragment_book_main) {

    private var _b: FragmentBookMainBinding? = null
    private val b get() = _b!!
    private val vm: BookMainViewModel by viewModels()
    private lateinit var adapter: BookMainAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _b = FragmentBookMainBinding.bind(view)

        val currentFilter = vm.currentStatus

        val statusArg = arguments?.getString("status") ?: BookStatus.READING.name
        val status = BookStatus.entries.find { it.name == statusArg } ?: BookStatus.READING

        if (currentFilter != status) {
            vm.setFilter(status)
        }

        when (status) {
            BookStatus.TO_READ -> b.bottomNav.selectedItemId = R.id.bookToReadFragment
            BookStatus.READING -> b.bottomNav.selectedItemId = R.id.bookReadingFragment
            BookStatus.FINISHED -> b.bottomNav.selectedItemId = R.id.bookFinishedFragment
        }

        vm.setFilter(BookStatus.valueOf(statusArg))

        adapter = BookMainAdapter { entry ->
            findNavController().navigate(
                R.id.action_global_to_bookDetail,
                bundleOf("entryId" to entry.id)
            )
        }

        b.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        b.recyclerView.adapter = adapter

        var firstLoad = true

        b.bottomNav.setOnItemSelectedListener { item ->
            if (firstLoad) {
                firstLoad = false
                return@setOnItemSelectedListener true
            }

            when (item.itemId) {
                R.id.bookToReadFragment -> {
                    vm.setFilter(BookStatus.TO_READ)
                    true
                }
                R.id.bookReadingFragment -> {
                    vm.setFilter(BookStatus.READING)
                    true
                }
                R.id.bookFinishedFragment -> {
                    vm.setFilter(BookStatus.FINISHED)
                    true
                }
                else -> false
            }
        }

        // Подписка на изменения UI-состояния
        lifecycleScope.launchWhenStarted {
            vm.uiState.collectLatest { state ->
                b.progressBar.isVisible = state.isLoading
                adapter.submitList(state.entries)

                state.error?.let {
                    Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}


