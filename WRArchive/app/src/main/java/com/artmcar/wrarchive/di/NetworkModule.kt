package com.artmcar.wrarchive.di

import com.artmcar.wrarchive.data.remote.api.AuthApi
import com.artmcar.wrarchive.data.remote.api.ReceiptApi
import com.artmcar.wrarchive.data.remote.api.UploadApi
import com.artmcar.wrarchive.data.remote.api.WarrantyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging) { level = LogLevel.ALL }
        }
    }

    @Provides
    @Singleton
    fun provideWarrantyApi(client: HttpClient): WarrantyApi {
        return WarrantyApi(client)
    }

    @Provides
    @Singleton
    fun provideAuthApi(client: HttpClient): AuthApi {
        return AuthApi(client)
    }

    @Provides
    @Singleton
    fun provideReceiptApi(client: HttpClient): ReceiptApi {
        return ReceiptApi(client)
    }

    @Provides
    @Singleton
    fun provideUploadApi(client: HttpClient): UploadApi {
        return UploadApi(client)
    }
}