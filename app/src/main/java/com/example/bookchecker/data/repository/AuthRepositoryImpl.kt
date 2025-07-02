package com.example.bookchecker.data.repository

import com.example.bookchecker.core.session.SessionManager
import com.example.bookchecker.data.remote.api.AuthApi
import com.example.bookchecker.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val sessionManager: SessionManager
) : AuthRepository {

    override suspend fun login(username: String, password: String): Result<Unit> = runCatching {
        val response = api.login(
            mapOf("username" to username, "password" to password)
        )
        sessionManager.saveTokens(response.access, response.refresh)
    }

    override suspend fun refresh(): Result<Unit> = runCatching {
        val refreshToken = sessionManager.getRefreshToken()
            ?: error("Refresh token not found")
        val response = api.refresh(mapOf("refresh" to refreshToken))
        sessionManager.saveTokens(response.access, response.refresh)
    }

    override suspend fun register(username: String, email: String, password: String): Result<Unit> = runCatching {
        api.register(
            mapOf("username" to username, "email" to email, "password" to password)
        )
    }

    override suspend fun logout() {
        sessionManager.clearTokens()
    }

    override fun isAuthorized(): Flow<Boolean> =
        sessionManager.accessToken.map { !it.isNullOrBlank() }
}