package com.example.bookchecker.feature.book_add.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookchecker.domain.model.Book
import com.example.bookchecker.domain.model.Entry
import com.example.bookchecker.domain.model.Progress
import com.example.bookchecker.domain.model.Rating
import com.example.bookchecker.domain.usecase.entry.EntryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class BookAddViewModel @Inject constructor(
    private val entryUseCases: EntryUseCases,
) : ViewModel() {

    val title = MutableLiveData<String>()
    val author = MutableLiveData<String>()
    val pageCount = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    private val _selectedStatus = MutableLiveData<String>()
    val selectedStatus: LiveData<String> = _selectedStatus

    private val _selectedGenres = MutableLiveData<List<String>>(emptyList())
    val selectedGenres: LiveData<List<String>> = _selectedGenres

    private val _startDate = MutableLiveData<LocalDate>()
    val startDate: LiveData<LocalDate> = _startDate

    private val _endDate = MutableLiveData<LocalDate>()
    val endDate: LiveData<LocalDate> = _endDate

    private val _coverUri = MutableLiveData<String>()
    val coverUri: LiveData<String> = _coverUri

    val entryOpResult = MutableLiveData<String>()

    fun toggleStatus(status: String) {
        _selectedStatus.value = status
    }

    // Add or remove genre
    fun toggleGenre(genre: String) {
        val current = _selectedGenres.value.orEmpty().toMutableList()
        if (current.contains(genre)) current.remove(genre) else current.add(genre)
        _selectedGenres.value = current
    }

    fun setStartDate(date: LocalDate) {
        _startDate.value = date
    }

    fun setEndDate(date: LocalDate) {
        _endDate.value = date
    }

    fun setCover(uri: Uri) {
        _coverUri.value = uri.toString()
    }

    fun createEntry(
        title: String,
        author: String,
        totalPages: Int,
        status: String,
        description: String? = null,
        startedAt: String? = null,
        finishedAt: String? = null,
        progressCurrentPage: Int? = null,
        ratingScore: Int? = null,
        genres: List<String>? = null
    ) = viewModelScope.launch {
        runCatching {
            // 1. BookDto → Book (доменная модель)
            val book = Book(
                id = null,
                title = title,
                author = author,
                totalPages = totalPages,
                description = description,
                coverUrl = null,
                genres = genres
            )

            // 2. Собираем Entry (доменная модель)
            val entry = Entry(
                book = book,
                status = status,
                startedAt = startedAt,
                finishedAt = finishedAt,
                progress = progressCurrentPage?.let {
                    Progress(currentPage = it, percent = 0.0, updatedAt = null)
                },
                rating = ratingScore?.let {
                    Rating(score = it, scale = 10)
                },
                notes = emptyList()
            )

            // 3. Вызываем useCase с Entry (а не EntryDto!)
            entryUseCases.addEntry(entry)
        }.onSuccess {
            entryOpResult.postValue("Created entry with nested book: id=${it.id}")
        }.onFailure {
            entryOpResult.postValue("Error: ${it.message}")
        }
    }

}