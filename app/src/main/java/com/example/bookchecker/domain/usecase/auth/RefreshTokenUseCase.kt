package com.example.bookchecker.domain.usecase.auth

import com.example.bookchecker.domain.repository.AuthRepository
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = repository.refresh()
}