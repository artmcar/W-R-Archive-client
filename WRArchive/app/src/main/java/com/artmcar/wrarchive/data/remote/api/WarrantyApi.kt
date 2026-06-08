package com.artmcar.wrarchive.data.remote.api

import com.artmcar.wrarchive.data.remote.dto.WarrantyRequestDto
import com.artmcar.wrarchive.data.remote.dto.WarrantyResponseDto
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

class WarrantyApi(
    private val client: HttpClient
) {
    suspend fun createWarranty(
        token: String,
        dto: WarrantyRequestDto
    ): WarrantyResponseDto {
        return client.post(
            "${ApiConfig.BASE_URL}/warranties"
        ) {
            contentType(ContentType.Application.Json)
            bearerAuth(token)
            setBody(dto)
        }.body()
    }
    suspend fun updateWarranty(
        token: String,
        id: Int,
        dto: WarrantyRequestDto
    ) {
        client.put(
            "${ApiConfig.BASE_URL}/warranties/$id"
        ) {
            contentType(ContentType.Application.Json)
            bearerAuth(token)
            setBody(dto)
        }
    }
    suspend fun deleteWarranty(
        token: String,
        id: Int
    ) {
        client.delete(
            "${ApiConfig.BASE_URL}/warranties/$id"
        ) {
            bearerAuth(token)
        }
    }
    suspend fun getAllWarranties(token: String): List<WarrantyResponseDto>
    {
        return client.get(
            "${ApiConfig.BASE_URL}/warranties"
        ) { bearerAuth(token) }.body()
    }
}