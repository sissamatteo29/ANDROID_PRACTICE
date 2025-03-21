package com.msissa.android_practice.data.helper

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


class DatabaseFileHelper {

    fun copyDatabaseToExternalStorage(databaseName: String) {
        val databasePath = "/data/data/ANDROID_PRACTICE/databases/$databaseName"
        val outputPath = "/sdcard/$databaseName"

        try {
            val inputFile = File(databasePath)
            val outputFile = File(outputPath)

            if (!inputFile.exists()) {
                println("Database file does not exist at: $databasePath")
                return
            }

            FileInputStream(inputFile).use { input ->
                FileOutputStream(outputFile).use { output ->
                    input.copyTo(output)
                }
            }

            println("Database copied successfully to: $outputPath")

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}