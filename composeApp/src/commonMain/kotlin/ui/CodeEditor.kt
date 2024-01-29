package ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.SyntaxLanguage
import dev.snipme.highlights.model.SyntaxThemes
import dev.snipme.kodeview.view.material3.CodeEditText


@Composable
fun CodeEditor(
    code: String,
    onCodeChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val darkMode = isSystemInDarkTheme()
    var highlights by remember(darkMode) {
        mutableStateOf(
            Highlights
                .Builder(
                    code = "",
                    language = SyntaxLanguage.JAVASCRIPT,
                    theme = SyntaxThemes.darcula(darkMode),
                )
                .build()
        )
    }

    LaunchedEffect(key1 = code) {
        highlights = highlights
            .getBuilder()
            .code(code)
            .build()
    }

    CodeEditText(
        modifier = modifier,
        highlights = highlights,
        onValueChange = onCodeChanged,
        colors = TextFieldDefaults.colors(),
    )
}