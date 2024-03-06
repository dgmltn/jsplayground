package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import getPlatform
import qjs.QuickJsWrapper
import kotlinx.coroutines.launch

@Composable
fun App(quickJsWrapper: QuickJsWrapper) {
    var result by remember { mutableStateOf("") }
    var code by remember { mutableStateOf(QuickJsWrapper.DICTIONARY_API_CODE) }
    val scope = rememberCoroutineScope()
    val platform = remember { getPlatform() }

    val onRun = fun() {
        scope.launch {
            result = quickJsWrapper.evaluateJs(code)
        }
    }

    MainScreen(
        platform = platform,
        code = code,
        onCodeChanged = { code = it },
        result = result,
        onRun = onRun,
    )
}
