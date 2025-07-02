package com.example.bookchecker.core.di

import android.content.Context
import com.example.bookchecker.core.session.SessionManager
import com.example.bookchecker.data.remote.api.AuthApi
import com.example.bookchecker.data.repository.AuthRepositoryImpl
import com.example.bookchecker.domain.repository.AuthRepository
import com.example.bookchecker.domain.usecase.auth.AuthUseCases
import com.example.bookchecker.domain.usecase.auth.IsAuthorizedUseCase
import com.example.bookchecker.domain.usecase.auth.LoginUseCase
import com.example.bookchecker.domain.usecase.auth.LogoutUseCase
import com.example.bookchecker.domain.usecase.auth.RefreshTokenUseCase
import com.example.bookchecker.domain.usecase.auth.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager =
        SessionManager(context)

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApi,
        sessionManager: SessionManager
    ): AuthRepository = AuthRepositoryImpl(api, sessionManager)

    @Provides
    @Singleton
    fun provideAuthUseCases(
        repository: AuthRepository
    ): AuthUseCases = AuthUseCases(
        login = LoginUseCase(repository),
        register = RegisterUseCase(repository),
        refresh = RefreshTokenUseCase(repository),
        logout = LogoutUseCase(repository),
        isAuthorized = IsAuthorizedUseCase(repository)
    )
}
