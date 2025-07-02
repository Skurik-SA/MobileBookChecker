package com.example.bookchecker.feature.auth.presentation.viewmodel


sealed class AuthUiState {
    object Unauthorized : AuthUiState()
    object Authorized : AuthUiState()
}
