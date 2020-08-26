package com.mitchelletakuro.gbemisolatakurofilter.service

import com.mitchelletakuro.gbemisolatakurofilter.model.FilterItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class CarOwnersRepo(private val service: CarOwnerService) {

    suspend fun filter(filter: FilterItem, csvFile: File) = withContext(Dispatchers.IO) {
        if (!csvFile.exists()) {
            downloadCSV(csvFile)
        }

    }

    private suspend fun downloadCSV(csvFile: File) = withContext(Dispatchers.IO) {
        val responseStream =
            service.downloadFile("https://drive.google.com/drive/u/0/folders/15H31VJ1mBH4pgAir4BVmoGDeGLa4Avl4")
                .byteStream()

        responseStream.use { input ->
            val outputStream = FileOutputStream(csvFile)
            outputStream.use { output ->
                val buffer = ByteArray(4 * 1024)
                while (true) {
                    val byteCount = input.read(buffer)
                    if (byteCount < 0) break
                    output.write(buffer, 0, byteCount)
                }
                output.flush()
            }
        }
    }

}