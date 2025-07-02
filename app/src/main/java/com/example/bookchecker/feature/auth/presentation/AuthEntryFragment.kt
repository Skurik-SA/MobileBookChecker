package com.example.bookchecker.feature.auth.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bookchecker.R
import com.example.bookchecker.databinding.FragmentAuthEntryBinding
import com.example.bookchecker.feature.auth.presentation.viewmodel.AuthEntryViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthEntryFragment : Fragment(R.layout.fragment_auth_entry) {

    private var _binding: FragmentAuthEntryBinding? = null
    private val binding get() = _binding!!
    private val vm: AuthEntryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthEntryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_to_login)
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_to_register)
        }

        binding.btnGuest.setOnClickListener {
            findNavController().navigate(R.id.action_to_main_graph)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}