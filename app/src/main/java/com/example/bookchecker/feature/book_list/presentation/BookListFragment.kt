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
        BookListItem(4, "1984", "Джордж Оруэлл"),
        BookListItem(5, "Улисс", "Джеймс Джойс"),
        BookListItem(6, "Лолита", "Владимир Набоков"),
        BookListItem(7, "Великий Гэтсби", "Фрэнсис Скотт Фицджеральд"),
        BookListItem(8, "Дон Кихот", "Мигель де Сервантес"),
        BookListItem(9, "Моби Дик", "Герман Мелвилл"),
        BookListItem(10, "Анна Каренина", "Лев Толстой"),
        BookListItem(11, "Сто лет одиночества", "Габриэль Гарсиа Маркес"),
        BookListItem(12, "Гордость и предубеждение", "Джейн Остин"),
        BookListItem(13, "Посторонний", "Альбер Камю"),
        BookListItem(14, "Процесс", "Франц Кафка"),
        BookListItem(15, "Сердце тьмы", "Джозеф Конрад"),
        BookListItem(16, "Гроздья гнева", "Джон Стейнбек"),
        BookListItem(17, "В поисках утраченного времени", "Марсель Пруст"),
        BookListItem(18, "Шум и ярость", "Уильям Фолкнер"),
        BookListItem(19, "Убить пересмешника", "Харпер Ли"),
        BookListItem(20, "О дивный новый мир", "Олдос Хаксли"),
        BookListItem(21, "Заводной апельсин", "Энтони Бёрджесс"),
        BookListItem(22, "Над пропастью во ржи", "Дж. Д. Сэлинджер"),
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