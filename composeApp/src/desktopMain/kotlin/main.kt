import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import ui.App
import zipline.JsHelpers
import zipline.ZiplineWrapper

fun main() {

    val httpClient = HttpClient(CIO).config {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(HttpTimeout)
    }

    val helpers by lazy { JsHelpers(httpClient) }

    val ziplineWrapper = ZiplineWrapper(helpers, Dispatchers.IO)

    application {
        Window(onCloseRequest = ::exitApplication, title = "jsplayground") {
            App(ziplineWrapper)
        }
    }
}
