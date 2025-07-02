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
class LoginViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val loginState: StateFlow<LoginUiState> = _loginState

    fun login(username: String, password: String) = viewModelScope.launch {
        _loginState.value = LoginUiState.Loading
        authUseCases.login(username, password).fold(
            onSuccess = { _loginState.value = LoginUiState.Success },
            onFailure = { _loginState.value = LoginUiState.Error(it.message) }
        )
    }
}