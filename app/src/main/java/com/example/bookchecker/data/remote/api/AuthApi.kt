package com.example.bookchecker.data.remote.api

import com.example.bookchecker.data.remote.dto.auth.LoginResponseDto
import com.example.bookchecker.data.remote.dto.auth.RefreshResponseDto
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/token/")
    suspend fun login(@Body body: Map<String, String>): LoginResponseDto

    @POST("auth/token/refresh/")
    suspend fun refresh(@Body body: Map<String, String>): RefreshResponseDto

    @POST("auth/register/")
    suspend fun register(@Body body: Map<String, String>): ResponseBody
}
