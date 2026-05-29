package com.artmcar.wrarchive.data.local

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class ImageStorageManager @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    fun saveImageFromUri(uri: Uri): String {
        val fileName = "${System.currentTimeMillis()}.jpg"
        val directory = File(context.filesDir, "images")
        if(!directory.exists()) {
            directory.mkdirs()
        }
        val file = File(directory, fileName)
        context.contentResolver
            .openInputStream(uri)
            ?.use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        return file.absolutePath
    }
}