package util

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement


suspend fun HttpClient.requestJson(url: String): JsonElement? =
    request(url) {
        method = HttpMethod.Get
        headers {
            append("Accept", "application/json")
        }
    }
        .bodyAsJsonElement()

private suspend fun HttpResponse.bodyAsJsonElement(): JsonElement? =
    try {
        bodyAsText().let { Json.decodeFromString(it) }
    } catch (e: Exception) {
        Logger.e { "Could not parse json: $e" }
        null
    }