package com.example.bookchecker.data.remote.api

import com.example.bookchecker.data.remote.dto.BookDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApi {
    @POST("books/")
    suspend fun create(@Body dto: BookDto): BookDto

    @GET("books/")
    suspend fun list(
        @Query("search") search: String? = null,
        @Query("ordering") orderBy: String? = null
    ): List<BookDto>

    @GET("books/{id}/")
    suspend fun get(@Path("id") id: Long): BookDto

    @DELETE("books/{id}/delete/")
    suspend fun delete(@Path("id") id: Long)
}