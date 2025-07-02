package com.example.bookchecker.feature.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookchecker.domain.usecase.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthEntryViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {
    private val _authState = MutableStateFlow<AuthUiState>(AuthUiState.Unauthorized)
    val authState: StateFlow<AuthUiState> = _authState

    init {
        // Observe current authorization state
        authUseCases.isAuthorized()
            .onEach { authorized ->
                _authState.value = if (authorized) AuthUiState.Authorized else AuthUiState.Unauthorized
            }
            .launchIn(viewModelScope)
    }

    fun logout() = viewModelScope.launch {
        authUseCases.logout()
    }
}
