package com.example.bookchecker.data.repository

import com.example.bookchecker.core.session.SessionManager
import com.example.bookchecker.data.remote.api.AuthApi
import com.example.bookchecker.data.remote.dto.auth.LoginRequestDto
import com.example.bookchecker.data.remote.dto.auth.RefreshRequestDto
import com.example.bookchecker.data.remote.dto.auth.RegisterRequestDto
import com.example.bookchecker.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val sessionManager: SessionManager
) : AuthRepository {

    override suspend fun login(username: String, password: String): Result<Unit> = runCatching {
        val request = LoginRequestDto(username = username, password = password)
        val response = api.login(request)
        sessionManager.saveTokens(response.access, response.refresh)
    }

    override suspend fun refresh(): Result<Unit> = runCatching {
        val refreshToken = sessionManager.getRefreshToken()
            ?: error("Refresh token not found")
        val request = RefreshRequestDto(refresh = refreshToken)
        val response = api.refresh(request)
        sessionManager.saveTokens(response.access, response.refresh)
    }

    override suspend fun register(username: String, email: String, password: String): Result<Unit> = runCatching {
        val request = RegisterRequestDto(email = email, username = username, password = password)
        api.register(request)
    }

    override suspend fun logout() {
        sessionManager.clearTokens()
    }

    override fun isAuthorized(): Flow<Boolean> =
        sessionManager.accessToken.map { !it.isNullOrBlank() }
}