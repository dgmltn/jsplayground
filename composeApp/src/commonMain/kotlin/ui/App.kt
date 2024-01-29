package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import getPlatform
import zipline.ZiplineWrapper
import kotlinx.coroutines.launch

@Composable
fun App(ziplineWrapper: ZiplineWrapper) {
    var result by remember { mutableStateOf("") }
    var code by remember { mutableStateOf(ZiplineWrapper.FIB_CODE) }
    val scope = rememberCoroutineScope()
    val platform = remember { getPlatform() }

    val onRun = fun() {
        scope.launch {
            result = ziplineWrapper.evaluateJs(code)
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
