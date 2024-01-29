package zipline

import io.ktor.client.HttpClient
import kotlinx.coroutines.runBlocking
import util.requestJson


class JsHelpers(private val httpClient: HttpClient) {
    fun gps(): String {
        return "{\"lat\":32.7153, \"lng\":-117.1573}"
    }

    fun requestJson(url: String): String {
        return runBlocking {
            httpClient
                .requestJson(url)
                .toString()
        }
    }
}
