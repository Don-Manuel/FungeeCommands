package org.openredstone.fungee.manager.handlers

import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.util.*

class DerpHandler(private val derpsFile: File) {
    var derps = emptyList<String>()

    fun loadDerps() {
        derps = try {
            derpsFile.readLines()
        } catch (e: IOException) {
            throw LoadException("Failed to load derps from $derpsFile", e)
        }
    }
}
