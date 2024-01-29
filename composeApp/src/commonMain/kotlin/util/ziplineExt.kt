package util

import app.cash.zipline.EngineApi
import app.cash.zipline.QuickJs


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
