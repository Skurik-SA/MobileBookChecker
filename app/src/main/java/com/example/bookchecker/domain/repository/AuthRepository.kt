package com.example.bookchecker.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(username: String, password: String): Result<Unit>
    suspend fun refresh(): Result<Unit>
    suspend fun register(username: String, email: String, password: String): Result<Unit>
    suspend fun logout()
    fun isAuthorized(): Flow<Boolean>
}