package com.example.bookchecker.feature.test_api_screen.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookchecker.data.remote.dto.BookDto
import com.example.bookchecker.data.remote.dto.EntryDto
import com.example.bookchecker.data.remote.dto.ProgressDto
import com.example.bookchecker.data.remote.dto.RatingDto
import com.example.bookchecker.domain.model.Book
import com.example.bookchecker.domain.model.Entry
import com.example.bookchecker.domain.model.Note
import com.example.bookchecker.domain.model.Progress
import com.example.bookchecker.domain.model.Rating
import com.example.bookchecker.domain.usecase.book.BookUseCases
import com.example.bookchecker.domain.usecase.entry.EntryUseCases
import com.example.bookchecker.domain.usecase.note.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestApiViewModel @Inject constructor(
    private val bookUseCases: BookUseCases,
    private val entryUseCases: EntryUseCases,
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    // BOOKS
    val booksLive = MutableLiveData<List<Book>>(emptyList())
    val bookOpResult = MutableLiveData<String>()
    // ENTRIES
    val entriesLive = MutableLiveData<List<Entry>>(emptyList())
    val entryOpResult = MutableLiveData<String>()
    // NOTES
    val notesLive = MutableLiveData<List<Note>>(emptyList())
    val noteOpResult = MutableLiveData<String>()

    // --- Books ---
    fun loadBooks(search: String?, orderBy: String? = null) = viewModelScope.launch {
        runCatching {
            bookUseCases.getBooks(search, orderBy)
        }.onSuccess {
            booksLive.postValue(it)
            bookOpResult.postValue("Loaded ${it.size} books")
        }.onFailure {
            bookOpResult.postValue("Error: ${it.message}")
        }
    }

    fun createBook(title: String, author: String, totalPages: Int) = viewModelScope.launch {
        runCatching {
            bookUseCases.addBook(
                Book(
                    title = title,
                    author = author,
                    totalPages = totalPages
                )
            )
        }.onSuccess {
            bookOpResult.postValue("Created book id=${it.id}")
            loadBooks(null)
        }.onFailure {
            bookOpResult.postValue("Error: ${it.message}")
        }
    }

    fun deleteBook(id: Long) = viewModelScope.launch {
        runCatching {
            bookUseCases.deleteBook(id)
        }.onSuccess {
            bookOpResult.postValue("Deleted book $id")
            loadBooks(null)
        }.onFailure {
            bookOpResult.postValue("Error: ${it.message}")
        }
    }

    // --- Entries ---
    fun loadEntries() = viewModelScope.launch {
        runCatching {
            entryUseCases.getEntries()
        }.onSuccess {
            entriesLive.postValue(it)
            entryOpResult.postValue("Loaded ${it.size} entries")
        }.onFailure {
            entryOpResult.postValue("Error: ${it.message}")
        }
    }

    fun createEntry(
        title: String,
        author: String,
        totalPages: Int,
        status: String,
        startedAt: String? = null,
        finishedAt: String? = null,
        progressCurrentPage: Int? = null,
        ratingScore: Int? = null
    ) = viewModelScope.launch {
        runCatching {
            // 1. BookDto → Book (доменная модель)
            val book = Book(
                id = null,
                title = title,
                author = author,
                totalPages = totalPages,
                description = null,
                coverUrl = null,
                genres = emptyList()
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
            loadEntries()
        }.onFailure {
            entryOpResult.postValue("Error: ${it.message}")
        }
    }



    fun deleteEntry(id: Long) = viewModelScope.launch {
        runCatching {
            entryUseCases.deleteEntry(id)
        }.onSuccess {
            entryOpResult.postValue("Deleted entry $id")
            loadEntries()
        }.onFailure {
            entryOpResult.postValue("Error: ${it.message}")
        }
    }

    // --- Notes ---
    fun loadNotes(entryId: Long) = viewModelScope.launch {
        runCatching {
            noteUseCases.getNotes(entryId)
        }.onSuccess {
            notesLive.postValue(it)
            noteOpResult.postValue("Loaded ${it.size} notes for entry $entryId")
        }.onFailure {
            noteOpResult.postValue("Error: ${it.message}")
        }
    }

    fun createNote(entryId: Long, text: String) = viewModelScope.launch {
        runCatching {
            noteUseCases.addNote(entryId, Note(text = text))
        }.onSuccess {
            noteOpResult.postValue("Created note id=${it.id}")
            loadNotes(entryId)
        }.onFailure {
            noteOpResult.postValue("Error: ${it.message}")
        }
    }

    fun deleteNote(entryId: Long, noteId: Long) = viewModelScope.launch {
        runCatching {
            noteUseCases.deleteNote(entryId, noteId)
        }.onSuccess {
            noteOpResult.postValue("Deleted note $noteId")
            loadNotes(entryId)
        }.onFailure {
            noteOpResult.postValue("Error: ${it.message}")
        }
    }
}

