package qjs

import com.dokar.quickjs.binding.define
import com.dokar.quickjs.binding.function
import com.dokar.quickjs.binding.toJsObject
import com.dokar.quickjs.quickJs
import kotlinx.coroutines.delay

class QuickJsWrapper(private val helpers: JsHelpers) {

    suspend fun evaluateJs(js: String): String =
        try {
            quickJs {
                define("host") {
                    function("log") {
                        helpers.log(it)
                    }
                    asyncFunction("gps") {
                        delay(1000)
                        helpers.gps().toJsObject()
                    }
                    function("requestJson") { (url) ->
                        helpers.requestJson(url.toString())
                    }
                }
                evaluate<Any?>(js).toString()
            }
        }
        catch (e: Exception) {
            e.toString()
        }

    companion object {
        val FIB_CODE = """
            function fibonacci(n) {
              host.log(n);
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
            let response = host.requestJson('https://icanhazdadjoke.com/');
            let parsed = JSON.parse(response);
            parsed.joke;
        """.trimIndent()

        val PROMISE_CODE = """
            async function dadJoke() {
                let response = host.requestJson('https://icanhazdadjoke.com/');
                let parsed = JSON.parse(response);
                return parsed.joke;
            }
            await dadJoke();
        """.trimIndent()

        val SUNSET_CODE = """
let gps = await host.gps();
let date = new Date();
let tz = -date.getTimezoneOffset() / 60; 
let dateStr = date.toISOString().slice(0, 10);
let url = 'https://aa.usno.navy.mil/api/rstt/oneday?date=' + dateStr + '&coords=' + gps.lat + ',' + gps.lng + '&tz=' + tz +'&dst=true';
let response = host.requestJson(url);
let parsed = JSON.parse(response);
let sundata = parsed.properties.data.sundata;
let sunset = sundata.find(it => it.phen == "Set");
let result = 'sunset:' + sunset.time;
result;
        """.trimIndent()

        val DICTIONARY_API_CODE = """
            function dictionaryapi(term) {
              const response = host.requestJson('https://www.dictionaryapi.com/api/v3/references/collegiate/json/' + term + '?key=084589cb-0369-45e6-ade0-ebc7626f4b7b');
              try {
                const parsed = JSON.parse(response);
                return parsed.map(def => ({ 
                  "title": term,
                  "subtitle": def.shortdef[0],
                  "result": term + ": " + def.shortdef[0]
                }));
              }
              catch (error) {
                return null;
              }
            }
            JSON.stringify(dictionaryapi("test"));
        """.trimIndent()
    }
}
