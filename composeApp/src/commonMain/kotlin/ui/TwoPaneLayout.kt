package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun TwoPaneLayout(
    pane1: @Composable BoxScope.() -> Unit,
    pane2: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier,
) {
    val fraction = 0.8f

    Box(modifier = modifier) {
        Column {
            Box(modifier = Modifier.weight(fraction)) {
                pane1()
            }
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f))
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.MoreHorz,
                    contentDescription = "",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Box(modifier = Modifier.weight(1f - fraction)) {
                pane2()
            }
        }
    }

}
