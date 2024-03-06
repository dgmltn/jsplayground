package ui

import Platform
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import qjs.QuickJsWrapper.Companion.DAD_JOKE_CODE
import qjs.QuickJsWrapper.Companion.DICTIONARY_API_CODE
import qjs.QuickJsWrapper.Companion.FIB_CODE
import qjs.QuickJsWrapper.Companion.JSON_CODE
import qjs.QuickJsWrapper.Companion.MODULE_CODE
import qjs.QuickJsWrapper.Companion.PROMISE_CODE
import qjs.QuickJsWrapper.Companion.SUNSET_CODE

private val TEMPLATES = listOf(
    "Module Test" to MODULE_CODE,
    "Fibonacci(10)" to FIB_CODE,
    "Json('result')" to JSON_CODE,
    "Dad Joke" to DAD_JOKE_CODE,
    "Promised Dad Joke" to PROMISE_CODE,
    "Sunset" to SUNSET_CODE,
    "Dictionary API" to DICTIONARY_API_CODE,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    platform: Platform,
    code: String,
    result: String,
    onCodeChanged: (String) -> Unit,
    onRun: () -> Unit,
) {
    var isSheetShowing by remember { mutableStateOf(false) }

    StatefulModalBottomSheet(
        isShowing = isSheetShowing,
        onDismissRequest = { isSheetShowing = false }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 24.dp, start = 16.dp, end = 16.dp)
        ) {
            Text("Choose a template:")
            TEMPLATES.forEach {
                TextButton({
                    onCodeChanged(it.second)
                    isSheetShowing = false
                }) { Text(it.first) }
            }
        }
    }

    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("JS Playground (${platform.name})") },
                    actions = {
                        IconButton(onClick = { isSheetShowing = true }) {
                            Icon(Icons.Default.List, null)
                        }
                    },
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = onRun) {
                    Icon(Icons.Default.PlayArrow, null)
                }
            },
        ) { scaffoldPadding ->
            TwoPaneLayout(
                pane1 = {
                    CodeEditor(
                        code = code,
                        onCodeChanged = onCodeChanged,
                        modifier = Modifier.fillMaxSize()
                    )
                },
                pane2 = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Text("Result:")
                        Text(result)
                    }
                },
                modifier = Modifier.padding(scaffoldPadding)
            )
        }
    }
}
