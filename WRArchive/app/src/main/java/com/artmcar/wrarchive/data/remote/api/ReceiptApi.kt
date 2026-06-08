package com.artmcar.wrarchive.data.remote.api

import com.artmcar.wrarchive.data.remote.dto.ReceiptRequestDto
import com.artmcar.wrarchive.data.remote.dto.ReceiptResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ReceiptApi(
    private val client: HttpClient
) {
    suspend fun createReceipt(
        token: String,
        dto: ReceiptRequestDto
    ): ReceiptResponseDto {
        return client.post(
            "${ApiConfig.BASE_URL}/receipts"
        ) {
            contentType(
                ContentType.Application.Json
            )
            bearerAuth(token)
            setBody(dto)
        }.body()
    }
    suspend fun updateReceipt(
        token: String,
        id: Int,
        dto: ReceiptRequestDto

    ) {
        client.put(
            "${ApiConfig.BASE_URL}/receipts/$id"
        ) {
            contentType(
                ContentType.Application.Json
            )
            bearerAuth(token)
            setBody(dto)
        }
    }
    suspend fun deleteReceipt(
        token: String,
        id: Int

    ) {
        client.delete(
            "${ApiConfig.BASE_URL}/receipts/$id"
        ) {
            bearerAuth(token)
        }
    }
    suspend fun getAllReceipts(token: String): List<ReceiptResponseDto>
    {
        return client.get(
            "${ApiConfig.BASE_URL}/receipts"
        ) { bearerAuth(token) }.body()
    }
}