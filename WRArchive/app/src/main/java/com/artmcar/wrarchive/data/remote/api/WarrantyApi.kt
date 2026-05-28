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
            "http://10.0.2.2:8080/warranties"
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
            "http://10.0.2.2:8080/warranties/$id"
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
            "http://10.0.2.2:8080/warranties/$id"
        ) {
            bearerAuth(token)
        }
    }
    suspend fun getAllWarranties(token: String): List<WarrantyResponseDto>
    {
        return client.get(
            "http://10.0.2.2:8080/warranties"
        ) { bearerAuth(token) }.body()
    }
}