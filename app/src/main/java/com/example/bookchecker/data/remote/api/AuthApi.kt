package com.example.bookchecker.data.remote.api

import com.example.bookchecker.data.remote.dto.auth.LoginRequestDto
import com.example.bookchecker.data.remote.dto.auth.LoginResponseDto
import com.example.bookchecker.data.remote.dto.auth.RefreshRequestDto
import com.example.bookchecker.data.remote.dto.auth.RefreshResponseDto
import com.example.bookchecker.data.remote.dto.auth.RegisterRequestDto
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/token/")
    suspend fun login(
        @Body body: LoginRequestDto
    ): LoginResponseDto

    @POST("auth/token/refresh/")
    suspend fun refresh(
        @Body body: RefreshRequestDto
    ): RefreshResponseDto

    @POST("auth/register/")
    suspend fun register(
        @Body body: RegisterRequestDto
    ): ResponseBody
}
