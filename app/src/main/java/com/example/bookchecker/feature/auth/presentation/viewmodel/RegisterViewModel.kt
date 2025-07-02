package com.example.bookchecker.feature.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookchecker.domain.usecase.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {
    private val _registerState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val registerState: StateFlow<RegisterUiState> = _registerState

    fun register(email: String, username: String, password: String, repeat: String) = viewModelScope.launch {
        _registerState.value = RegisterUiState.Loading
        authUseCases.register(username, email, password).fold(
            onSuccess = { _registerState.value = RegisterUiState.Success },
            onFailure = { _registerState.value = RegisterUiState.Error(it.message) }
        )
    }
}