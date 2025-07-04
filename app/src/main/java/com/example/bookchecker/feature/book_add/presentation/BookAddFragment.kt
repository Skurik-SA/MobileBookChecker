package com.example.bookchecker.feature.book_add.presentation

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bookchecker.databinding.FragmentBookAddBinding
import com.example.bookchecker.feature.book_add.presentation.viewmodel.BookAddViewModel
import com.google.android.material.chip.Chip
import androidx.core.net.toUri
import com.example.bookchecker.R
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.util.Calendar


@AndroidEntryPoint
class BookAddFragment : Fragment(R.layout.fragment_book_add) {
    private var _binding: FragmentBookAddBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BookAddViewModel by viewModels()

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            binding.imageCover.setImageURI(it)
            viewModel.setCover(it)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.entryOpResult.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        // Title
        binding.edtTitle.doOnTextChanged { text, _, _, _ -> viewModel.title.value = text.toString() }
        // Author
        binding.edtAuthor.doOnTextChanged { text, _, _, _ -> viewModel.author.value = text.toString() }
        // Pages
        binding.edtPages.doOnTextChanged { text, _, _, _ -> viewModel.pageCount.value = text.toString() }
        // Description
        binding.edtDescription.doOnTextChanged { text, _, _, _ -> viewModel.description.value = text.toString() }


//        val statuses = listOf("READ", "READING", "TO_READ")
//        val statusesAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, statuses)
//        binding.autoBookReadingStatus.setAdapter(statusesAdapter)
//        binding.autoBookReadingStatus.setOnItemClickListener { _, _, pos, _ ->
//            viewModel.toggleStatus(statuses[pos])
//        }
//        viewModel.selectedStatus.observe(viewLifecycleOwner) { status ->
//            binding.etEntryStatus.setText(status)
//        }

        // Genre dropdown adapter (example list)
        val genres = listOf("Fiction", "Non-fiction", "Fantasy", "Sci-Fi")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, genres)
        binding.autoGenre.setAdapter(adapter)
        binding.autoGenre.setOnItemClickListener { _, _, pos, _ ->
            viewModel.toggleGenre(genres[pos])
        }

        viewModel.selectedGenres.observe(viewLifecycleOwner) { list ->
            binding.chipGroupGenres.removeAllViews()
            list.forEach { genre ->
                val chip = Chip(requireContext()).apply {
                    text = genre
                    isCloseIconVisible = true
                    setOnCloseIconClickListener { viewModel.toggleGenre(genre) }
                }
                binding.chipGroupGenres.addView(chip)
            }
        }

        // Date pickers
        binding.layoutStartDate.setEndIconOnClickListener { showDatePicker(true) }
        binding.layoutEndDate.setEndIconOnClickListener { showDatePicker(false) }

        viewModel.startDate.observe(viewLifecycleOwner) { date ->
            binding.edtStartDate.setText(date.toString())
        }
        viewModel.endDate.observe(viewLifecycleOwner) { date ->
            binding.edtEndDate.setText(date.toString())
        }

        // Cover selection
        binding.btnSelectCover.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }
        viewModel.coverUri.observe(viewLifecycleOwner) { uri ->
            binding.imageCover.setImageURI(uri.toUri())
        }

        // Save
//        binding.btnSave.setOnClickListener {
//            viewModel.saveBook()
//            // TODO: navigate back or show confirmation
//        }

        binding.btnSave.setOnClickListener {
            val title  = binding.edtTitle.text.toString()
            val author = binding.edtAuthor.text.toString()

            val pages  = binding.edtPages.text.toString().toIntOrNull() ?: return@setOnClickListener
            val description = binding.edtDescription.text.toString().takeIf { it.isNotBlank() }
            val startedAt = viewModel.startDate.value?.toString()
            val finishedAt = viewModel.endDate.value?.toString()
            val genres = viewModel.selectedGenres.value
            Log.d("CreateEntry", "Book: $title")
            Log.d("CreateEntry", "Entry: $author")
            Log.d("CreateEntry", "Entry: $pages")
            Log.d("CreateEntry", "Entry: $description")
            Log.d("CreateEntry", "Entry: $startedAt")
            Log.d("CreateEntry", "Entry: $finishedAt")
            Log.d("CreateEntry", "Entry: $genres")

//            val status = binding.etEntryStatus.text.toString()
            // дополнительные поля (даты, прогресс, рейтинг) тоже считывайте из EditText-ов
            viewModel.createEntry(
                title = title,
                author = author,
                totalPages = pages,
                description = description,
                status = "TO_READ",
                startedAt = startedAt,
                finishedAt = finishedAt,
                progressCurrentPage = 0,
                ratingScore = 0,
                genres = genres
            )
        }
    }

    private fun showDatePicker(isStart: Boolean) {
        val cal = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                val date = LocalDate.of(year, month + 1, day)
                if (isStart) viewModel.setStartDate(date) else viewModel.setEndDate(date)
            },
            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
