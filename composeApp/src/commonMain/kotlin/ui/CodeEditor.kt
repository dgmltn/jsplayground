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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.SyntaxLanguage
import dev.snipme.highlights.model.SyntaxThemes
import dev.snipme.kodeview.view.material3.CodeEditText
import jsplayground.composeapp.generated.resources.Res
import jsplayground.composeapp.generated.resources.fira_code_regular
import org.jetbrains.compose.resources.Font


@Composable
fun CodeEditor(
    code: String,
    onCodeChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val textStyle = TextStyle(
        fontFamily = FontFamily(
            Font(Res.font.fira_code_regular, FontWeight.Normal, FontStyle.Normal)
        ),
        fontSize = 18.sp
    )

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
        textStyle = textStyle
    )
}