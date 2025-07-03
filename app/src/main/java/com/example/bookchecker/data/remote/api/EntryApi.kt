package com.example.bookchecker.data.remote.api

import com.example.bookchecker.data.remote.dto.EntryDto
import retrofit2.http.*

interface EntryApi {
    @POST("entries/")
    suspend fun create(@Body dto: EntryDto): EntryDto

    @GET("entries/")
    suspend fun list(): List<EntryDto>

    @GET("entries/{id}/")
    suspend fun get(@Path("id") id: Long): EntryDto

    @PATCH("entries/{id}/")
    suspend fun update(
        @Path("id") id: Long?,
        @Body dto: EntryDto
    ): EntryDto

    @DELETE("entries/{id}/")
    suspend fun delete(@Path("id") id: Long)
}