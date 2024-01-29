package util

import app.cash.zipline.EngineApi
import app.cash.zipline.QuickJs
import zipline.JsHelpers


@OptIn(EngineApi::class)
inline fun <C : QuickJs, R> C.use(block: (C) -> R): R {
    var exception: Throwable? = null
    try {
        return block(this)
    } catch (e: Throwable) {
        exception = e
        throw e
    } finally {
        when {
            exception == null -> close()
            else ->
                try {
                    close()
                } catch (closeException: Throwable) {
                    // cause.addSuppressed(closeException) // ignored here
                }
        }
    }
}

@OptIn(EngineApi::class)
fun QuickJs.attachHelpers(helpers: JsHelpers) {
    //TODO: inject "kml" object into this QuickJs
//    createNewJSObject().run {
//        setProperty("requestJson") { args ->
//            helpers.requestJson(args[0] as String)
//        }
//
//        setProperty("gps") {
//            helpers.gps()
//        }
//
//        globalObject.setProperty("kml", this)
//    }
}