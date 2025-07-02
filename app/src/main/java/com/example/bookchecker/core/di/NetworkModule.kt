package com.example.bookchecker.core.di

import android.content.Context
import com.example.bookchecker.core.session.SessionManager
import com.example.bookchecker.data.remote.api.AuthApi
import com.example.bookchecker.data.remote.api.BookApi
import com.example.bookchecker.data.remote.api.EntryApi
import com.example.bookchecker.data.remote.api.NoteApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

//    private const val BASE_URL = "http://127.0.0.1:8000/api/"
    private const val BASE_URL = "http://10.0.2.2:8000/api/"
    private const val CACHE_SIZE_BYTES: Long = 10L * 1024 * 1024 // 10MB
    private const val MAX_STALE_SECONDS: Int = 60 * 60 * 24 * 7 // 7 days
    private const val MAX_AGE_SECONDS: Int = 60 // 1 minute

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun provideAuthInterceptor(
        sessionManager: SessionManager
    ): Interceptor = Interceptor { chain ->
        // Retrieve latest access token synchronously
        val token = runBlocking { sessionManager.accessToken.firstOrNull() }
        val request = chain.request().newBuilder().apply {
            token?.let { addHeader("Authorization", "Bearer $it") }
        }.build()
        chain.proceed(request)
    }

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache =
        Cache(context.cacheDir, CACHE_SIZE_BYTES)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        logging: HttpLoggingInterceptor,
        authInterceptor: Interceptor,
        cache: Cache,
        @ApplicationContext context: Context
    ): OkHttpClient =
        OkHttpClient.Builder()
            .cache(cache)
            // Offline cache interceptor
            .addInterceptor { chain ->
                var request = chain.request()
                if (!hasNetwork(context)) {
                    request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=$MAX_STALE_SECONDS")
                        .build()
                }
                chain.proceed(request)
            }
            // Network interceptor to set cache headers
            .addNetworkInterceptor { chain ->
                val response = chain.proceed(chain.request())
                response.newBuilder()
                    .header("Cache-Control", "public, max-age=$MAX_AGE_SECONDS")
                    .build()
            }
            .addInterceptor(authInterceptor)
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()


    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)    // поменяй на свой URL
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideBookApi(retrofit: Retrofit): BookApi =
        retrofit.create(BookApi::class.java)

    @Provides
    @Singleton
    fun provideEntryApi(retrofit: Retrofit): EntryApi =
        retrofit.create(EntryApi::class.java)

    @Provides
    @Singleton
    fun provideNoteApi(retrofit: Retrofit): NoteApi =
        retrofit.create(NoteApi::class.java)


    private fun hasNetwork(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val caps = cm.getNetworkCapabilities(network) ?: return false
        return caps.hasCapability(android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
