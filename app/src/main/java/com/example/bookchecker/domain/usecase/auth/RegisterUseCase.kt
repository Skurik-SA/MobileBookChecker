package com.example.bookchecker.domain.usecase.auth

import com.example.bookchecker.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(username: String, email: String, password: String) =
        repository.register(username, email, password)
}