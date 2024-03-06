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

    fun gps(): String {
        return "{\"lat\":32.7153, \"lng\":-117.1573}"
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
