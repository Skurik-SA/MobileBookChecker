package com.example.bookchecker.feature.auth.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bookchecker.R
import com.example.bookchecker.databinding.FragmentRegisterBinding
import com.example.bookchecker.feature.auth.presentation.viewmodel.RegisterUiState
import com.example.bookchecker.feature.auth.presentation.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val vm: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegisterSubmit.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            val repeat = binding.etPasswordRepeat.text.toString()
            vm.register(email, username, password, repeat)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vm.registerState.collect { state ->
                when (state) {
                    is RegisterUiState.Idle -> {
                        binding.progressBar.visibility = View.GONE
                        binding.btnRegisterSubmit.isEnabled = true
                    }
                    is RegisterUiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.btnRegisterSubmit.isEnabled = false
                    }
                    is RegisterUiState.Success -> {
                        findNavController().navigate(R.id.action_register_to_main_graph)
                    }
                    is RegisterUiState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.btnRegisterSubmit.isEnabled = true
                        Toast.makeText(requireContext(), state.message ?: "Ошибка регистрации", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}