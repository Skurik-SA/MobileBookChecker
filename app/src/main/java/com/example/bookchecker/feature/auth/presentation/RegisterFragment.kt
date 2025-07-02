package com.example.bookchecker.feature.auth.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bookchecker.R
import com.example.bookchecker.databinding.FragmentRegisterBinding
import com.example.bookchecker.feature.auth.presentation.viewmodel.AuthViewModel

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val vm: AuthViewModel by activityViewModels()

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

        vm.registerResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                findNavController().navigate(R.id.action_register_to_main_graph)
            } else {
                Toast.makeText(requireContext(), "Ошибка регистрации", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}