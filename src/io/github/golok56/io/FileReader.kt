package io.github.golok56.io

import java.io.File

class FileReader {
    @Throws(IllegalArgumentException::class)
    fun read(filename: String): String {
        validateFilename(filename)
        return File(filename).readBytes().toString(Charsets.UTF_8).trim()
    }

    private fun validateFilename(filename: String) {
        val filenames = filename.split(".")
        if (filenames[filenames.size - 1] != "c") {
            throw IllegalArgumentException("Pastikan berkas sumber menggunakan bahasa C dan memiliki ekstensi .c!")
        }
    }
}