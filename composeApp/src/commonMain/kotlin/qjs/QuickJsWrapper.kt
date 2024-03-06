package qjs

import com.dokar.quickjs.binding.define
import com.dokar.quickjs.binding.function
import com.dokar.quickjs.quickJs

class QuickJsWrapper(private val helpers: JsHelpers) {

    suspend fun evaluateJs(js: String): String =
        try {
            var result: Any? = null

            quickJs {
                define<JsHelpers>("host", helpers)
                function("returns") { result = it.first() }

                val helloModuleCode = """
                    export function greeting() {
                        return "Hi from the hello module!";
                    }
                """.trimIndent()
                val bytecode = compile(
                    code = helloModuleCode,
                    filename = "hello",
                    asModule = true,
                )
                addModule(bytecode)

                evaluate<Any?>(js).toString()
            }
        }
        catch (e: Exception) {
            e.toString()
        }

    companion object {
        val MODULE_CODE = """
            import * as hello from "hello";
            returns(hello.greeting());
        """.trimIndent()

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
            let gps = JSON.parse(host.gps());
            let url = 'https://aa.usno.navy.mil/api/rstt/oneday?date=2024-01-22&coords=' + gps.lat + ',' + gps.lng + '&tz=-8.0&dst=true';
            let response = host.requestJson(url);
            let parsed = JSON.parse(response);
            let sundata = parsed.properties.data.sundata;
            let result = 'sun ' + sundata[3].phen + ': ' + sundata[3].time;
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
