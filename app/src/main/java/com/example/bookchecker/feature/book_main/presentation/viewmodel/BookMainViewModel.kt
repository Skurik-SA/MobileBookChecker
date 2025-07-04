package com.example.bookchecker.feature.book_main.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookchecker.domain.model.BookStatus
import com.example.bookchecker.domain.model.Entry
import com.example.bookchecker.domain.usecase.entry.EntryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BookMainUiState(
    val isLoading: Boolean = false,
    val entries: List<Entry> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class BookMainViewModel @Inject constructor(
    private val entryUseCases: EntryUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(BookMainUiState(isLoading = true))
    val uiState: StateFlow<BookMainUiState> = _uiState.asStateFlow()

    var currentStatus: BookStatus = BookStatus.READING

    init {
        loadEntries()
    }

    private fun loadEntries() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val all = entryUseCases.getEntries()
                val filtered = all.filter { it.status == currentStatus.name }
                _uiState.value = BookMainUiState(
                    isLoading = false,
                    entries = filtered,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = BookMainUiState(
                    isLoading = false,
                    entries = emptyList(),
                    error = e.message
                )
            }
        }
    }

    fun setFilter(status: BookStatus) {
        currentStatus = status
        loadEntries()
    }
}
