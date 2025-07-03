package com.example.bookchecker.feature.test_api_screen.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookchecker.R
import com.example.bookchecker.databinding.FragmentTestApiBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.bookchecker.feature.test_api_screen.presentation.viewmodel.TestApiViewModel


@AndroidEntryPoint
class TestApiFragment : Fragment(R.layout.fragment_test_api) {

    private val vm: TestApiViewModel by viewModels()
    private var _binding: FragmentTestApiBinding? = null
    private val binding get() = _binding!!

    private lateinit var bookAdapter: BookAdapter
    private lateinit var entryAdapter: EntryAdapter
    private lateinit var noteAdapter: NoteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentTestApiBinding.bind(view)

        // --- Adapters ---
        bookAdapter = BookAdapter()
        binding.rvBooks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = bookAdapter
        }

        entryAdapter = EntryAdapter()
        binding.rvEntries.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = entryAdapter
        }

        noteAdapter = NoteAdapter()
        binding.rvNotes.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = noteAdapter
        }

        // --- Observers ---
        vm.booksLive.observe(viewLifecycleOwner) {
            bookAdapter.submitList(it)
        }
        vm.bookOpResult.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        vm.entriesLive.observe(viewLifecycleOwner) {
            entryAdapter.submitList(it)
        }
        vm.entryOpResult.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        vm.notesLive.observe(viewLifecycleOwner) {
            noteAdapter.submitList(it)
        }
        vm.noteOpResult.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        // --- Buttons ---
        binding.btnLoadBooks.setOnClickListener {
            vm.loadBooks(binding.etBookSearch.text.toString().takeIf { it.isNotBlank() })
        }
        binding.btnCreateBook.setOnClickListener {
            val title = binding.etNewBookTitle.text.toString()
            val author = binding.etBookAuthor.text.toString()
            val pages = binding.etBookPages.text.toString().toIntOrNull() ?: return@setOnClickListener
            vm.createBook(title, author, pages)
        }

        binding.btnDeleteBook.setOnClickListener {
            binding.etBookId.text.toString().toLongOrNull()?.let { vm.deleteBook(it) }
        }

        binding.btnLoadEntries.setOnClickListener { vm.loadEntries() }
        binding.btnCreateEntry.setOnClickListener {
            val title  = binding.etNewBookTitle.text.toString()
            val author = binding.etBookAuthor.text.toString()

            val pages  = binding.etEntryPages.text.toString().toIntOrNull() ?: return@setOnClickListener
            val status = binding.etEntryStatus.text.toString()
            // дополнительные поля (даты, прогресс, рейтинг) тоже считывайте из EditText-ов
            vm.createEntry(
                title = title,
                author = author,
                totalPages = pages,
                status = status,
                startedAt = "2025-05-05",
                finishedAt = null,
                progressCurrentPage = 100,
                ratingScore = 7
            )
        }
        binding.btnDeleteEntry.setOnClickListener {
            binding.etEntryId.text.toString().toLongOrNull()?.let { vm.deleteEntry(it) }
        }

        binding.btnLoadNotes.setOnClickListener {
            binding.etNotesEntryId.text.toString().toLongOrNull()?.let { vm.loadNotes(it) }
        }
        binding.btnCreateNote.setOnClickListener {
            val eid = binding.etNotesEntryId.text.toString().toLongOrNull() ?: return@setOnClickListener
            vm.createNote(eid, binding.etNewNoteText.text.toString())
        }
        binding.btnDeleteNote.setOnClickListener {
            val eid = binding.etNotesEntryId.text.toString().toLongOrNull() ?: return@setOnClickListener
            val nid = binding.etNoteId.text.toString().toLongOrNull() ?: return@setOnClickListener
            vm.deleteNote(eid, nid)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
