package com.dgmltn.jsplayground

import ui.App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import zipline.JsHelpers
import zipline.ZiplineWrapper
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {

    private val httpClient = HttpClient(CIO).config {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(HttpTimeout)
    }

    private val helpers by lazy { JsHelpers(httpClient) }

    private val ziplineWrapper = ZiplineWrapper(helpers, Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(ziplineWrapper)
        }
    }
}
