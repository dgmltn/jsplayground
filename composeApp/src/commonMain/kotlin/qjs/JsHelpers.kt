package qjs

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import util.requestJson


class JsHelpers(private val httpClient: HttpClient) {
    fun log(args: Array<Any?>) {
        Logger.e { args.joinToString(" ") }
    }

    fun gps(): Map<String, Float> {
        return mapOf("lat" to 32.7153f, "lng" to -117.1573f)
    }

    fun delay(delay: Long): Unit =
        runBlocking {
            kotlinx.coroutines.delay(delay)
        }

    fun requestJson(url: String): String =
        runBlocking {
            httpClient
                .requestJson(url)
                .toString()
        }
}
