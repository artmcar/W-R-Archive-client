package com.artmcar.wrarchive.data.remote.api

import com.artmcar.wrarchive.data.remote.dto.AuthResponseDto
import com.artmcar.wrarchive.data.remote.dto.LoginRequestDto
import com.artmcar.wrarchive.data.remote.dto.RegisterRequestDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthApi(
    private val client: HttpClient
) {
    suspend fun login(
        email: String,
        password: String
    ): AuthResponseDto {
        return client.post(
            "${ApiConfig.BASE_URL}/auth/login"
        ) {
            contentType(
                ContentType.Application.Json
            )
            setBody(
                LoginRequestDto(
                    email,
                    password
                )
            )
        }.body()
    }
    suspend fun register(
        email: String,
        password: String
    ): AuthResponseDto {
        return client.post(
            "${ApiConfig.BASE_URL}/auth/register"
        ) {
            contentType(
                ContentType.Application.Json
            )
            setBody(
                RegisterRequestDto(
                    email,
                    password
                )
            )
        }.body()
    }
}