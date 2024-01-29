package zipline

import app.cash.zipline.EngineApi
import app.cash.zipline.QuickJs
import app.cash.zipline.QuickJsException
import app.cash.zipline.Zipline
import zipline.JsHelpers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import util.attachHelpers
import util.use
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ZiplineWrapper(private val helpers: JsHelpers, private val dispatcher: CoroutineDispatcher) {

    @OptIn(EngineApi::class)
    suspend fun evaluateJs(js: String): String =
        withContext(dispatcher) {
            suspendCoroutine { continuation ->
                val zipline = Zipline.create(dispatcher)
                zipline.quickJs.use { context ->
                    context.attachHelpers(helpers)
                    val result = try {
                        val compiled = context.evaluate(js, "script.js")
                        compiled.toString()
                    } catch (e: QuickJsException) {
                        "ERROR: ${e.message}"
                    }
                    continuation.resume(result)
                }
            }
        }

    companion object {
        val FIB_CODE = """
            function fibonacci(n) {
              if (n == 0 || n == 1) return n;
              return fibonacci(n - 1) + fibonacci(n - 2);
            }
            fibonacci(10);
        """.trimIndent()

        val JSON_CODE = """
            let json = {'result':{'foo':['bar','baz']}};
            json.result.foo[1];
        """.trimIndent()

        val DAD_JOKE_CODE = """
            let response = kml.requestJson('https://icanhazdadjoke.com/');
            let parsed = JSON.parse(response);
            parsed.joke;
        """.trimIndent()

        val SUNSET_CODE = """
            let gps = JSON.parse(kml.gps());
            let url = 'https://aa.usno.navy.mil/api/rstt/oneday?date=2024-01-22&coords=' + gps.lat + ',' + gps.lng + '&tz=-8.0&dst=true';
            let response = kml.requestJson(url);
            let parsed = JSON.parse(response);
            let sundata = parsed.properties.data.sundata;
            let result = 'sun ' + sundata[3].phen + ': ' + sundata[3].time;
            result;
        """.trimIndent()
    }
}
