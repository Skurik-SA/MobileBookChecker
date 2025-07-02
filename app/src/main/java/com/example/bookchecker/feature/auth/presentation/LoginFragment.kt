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
import com.example.bookchecker.databinding.FragmentLoginBinding
import com.example.bookchecker.feature.auth.presentation.viewmodel.LoginUiState
import com.example.bookchecker.feature.auth.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val vm: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLoginSubmit.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            vm.login(username, password)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vm.loginState.collect { state ->
                binding.progressBar.visibility =
                    if (state is LoginUiState.Loading) View.VISIBLE else View.GONE
                binding.btnLoginSubmit.isEnabled = state != LoginUiState.Loading

                when (state) {
                    is LoginUiState.Success -> findNavController().navigate(R.id.action_login_to_main_graph)
                    is LoginUiState.Error -> Toast.makeText(
                        requireContext(),
                        state.message ?: "Ошибка входа",
                        Toast.LENGTH_SHORT
                    ).show()
                    else -> { /* Idle or Loading handled above */ }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
