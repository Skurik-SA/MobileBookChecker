package com.example.bookchecker.feature.auth.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bookchecker.R
import com.example.bookchecker.databinding.FragmentLoginBinding
import com.example.bookchecker.feature.auth.presentation.viewmodel.LoginViewModel


class LoginFragment: Fragment(R.layout.fragment_login) {

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

        binding.btnSocial.setOnClickListener {
            Toast.makeText(requireContext(), "Social login not implemented", Toast.LENGTH_SHORT).show()
        }

        vm.loginResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                findNavController().navigate(R.id.action_login_to_main_graph)
            } else {
                Toast.makeText(requireContext(), "Ошибка входа", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}