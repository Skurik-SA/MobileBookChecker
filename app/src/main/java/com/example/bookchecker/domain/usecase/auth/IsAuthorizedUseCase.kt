package com.example.bookchecker.domain.usecase.auth

import com.example.bookchecker.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsAuthorizedUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Flow<Boolean> = repository.isAuthorized()
}
