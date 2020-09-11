package org.openredstone.handlers

import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.util.*

class DerpHandler(private val derpsFile: File) {
    private val rand = Random()
    private var derps = emptyList<String>()

    @Throws(LoadException::class)
    fun loadDerps() {
        derps = try {
            Files.readAllLines(derpsFile.toPath())
        } catch (e: IOException) {
            throw LoadException("Failed to load derps from $derpsFile", e)
        }
    }

    fun derpCount(): Int {
        return derps.size
    }

    fun randomDerp(): String {
        return derpByIndex(rand.nextInt(derps.size))
    }

    fun derpByIndex(index: Int): String {
        return derps[index]
    }

}
