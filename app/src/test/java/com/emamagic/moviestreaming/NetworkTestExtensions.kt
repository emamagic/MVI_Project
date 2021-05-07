package com.emamagic.moviestreaming

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import java.io.File
import java.nio.charset.StandardCharsets

internal fun MockWebServer.enqueueResponse(fileName: String, code: Int) {
    val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/$fileName")

    val source = inputStream?.let { inputStream.source().buffer() }
    source?.let {
        enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody(source.readString(StandardCharsets.UTF_8))
        )
    }
}

/**
 * Helper function which will load JSON from
 * the path specified
 *
 * @param path : Path of JSON file
 * @return json : JSON from file at given path
 */
//fun getJson(path : String) : String {
//    // Load the JSON response
//    val uri = this.javaClass.classLoader.getResource(path)
//    val file = File(uri.path)
//    return String(file.readBytes())
//}