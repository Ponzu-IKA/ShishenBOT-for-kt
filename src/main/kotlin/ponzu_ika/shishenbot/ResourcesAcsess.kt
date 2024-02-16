package ponzu_ika.shishenbot

import java.io.BufferedReader
import java.io.BufferedWriter
import java.nio.file.Files
import java.nio.file.Paths


fun fileInputStream(path: String):BufferedReader {
    return Files.newBufferedReader(Paths.get(path))
}
fun fileOutputStream(path: String): BufferedWriter {
    return Files.newBufferedWriter(Paths.get("g_$path")) // gはgeneratedの意
}
