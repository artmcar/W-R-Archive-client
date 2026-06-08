package com.artmcar.wrarchive.data.remote.api

import com.artmcar.wrarchive.data.remote.dto.UploadResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import java.io.File

class UploadApi(
    private val client: HttpClient
) {
    suspend fun uploadImage(
        token: String,
        filePath: String
    ): String {
        val file = File(filePath)
        val response =
            client.submitFormWithBinaryData(
                url = "${ApiConfig.BASE_URL}/upload",
                formData = formData {
                    append(
                        key = "file",
                        value = file.readBytes(),
                        headers = Headers.build {
                            append(
                                HttpHeaders.ContentType,
                                "image/jpeg"
                            )
                            append(
                                HttpHeaders.ContentDisposition,
                                "filename=${file.name}"
                            )
                        }
                    )
                }
            ) {
                bearerAuth(token)
            }.body<UploadResponseDto>()
        return response.imageUrl
    }
}