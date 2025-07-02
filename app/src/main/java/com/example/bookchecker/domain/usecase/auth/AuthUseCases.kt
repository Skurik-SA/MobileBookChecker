package com.example.bookchecker.domain.usecase.auth

data class AuthUseCases(
    val login: LoginUseCase,
    val register: RegisterUseCase,
    val refresh: RefreshTokenUseCase,
    val logout: LogoutUseCase,
    val isAuthorized: IsAuthorizedUseCase
)