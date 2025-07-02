package com.example.bookchecker.data.remote.api

import com.example.bookchecker.data.remote.dto.NoteDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface NoteApi {
    @GET("entries/{entryId}/notes/")
    suspend fun list(@Path("entryId") entryId: Long): List<NoteDto>

    @POST("entries/{entryId}/notes/")
    suspend fun create(
        @Path("entryId") entryId: Long,
        @Body dto: NoteDto
    ): NoteDto

    @GET("entries/{entryId}/notes/{noteId}/")
    suspend fun get(
        @Path("entryId") entryId: Long,
        @Path("noteId") noteId: Long
    ): NoteDto

    @PATCH("entries/{entryId}/notes/{noteId}/")
    suspend fun update(
        @Path("entryId") entryId: Long,
        @Path("noteId") noteId: Long,
        @Body dto: NoteDto
    ): NoteDto

    @DELETE("entries/{entryId}/notes/{noteId}/")
    suspend fun delete(
        @Path("entryId") entryId: Long,
        @Path("noteId") noteId: Long
    )
}