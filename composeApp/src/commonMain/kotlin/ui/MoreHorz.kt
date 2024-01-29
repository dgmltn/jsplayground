package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

public val Icons.Filled.MoreHorz: ImageVector
    get() {
        if (_moreHorz != null) {
            return _moreHorz!!
        }
        _moreHorz = materialIcon(name = "Filled.MoreHorz") {
            materialPath {
                moveTo(6.0f, 14.0f)
                curveToRelative(1.1f, 0.0f, 2.0f, -0.9f, 2.0f, -2.0f)
                reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                reflectiveCurveToRelative(-2.0f, 0.9f, -2.0f, 2.0f)
                reflectiveCurveToRelative(0.9f, 2.0f, 2.0f, 2.0f)
                close()
                moveTo(12.0f, 10.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                reflectiveCurveToRelative(0.9f, 2.0f, 2.0f, 2.0f)
                reflectiveCurveToRelative(2.0f, -0.9f, 2.0f, -2.0f)
                reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                close()
                moveTo(18.0f, 10.0f)
                curveToRelative(-1.1f, 0.0f, -2.0f, 0.9f, -2.0f, 2.0f)
                reflectiveCurveToRelative(0.9f, 2.0f, 2.0f, 2.0f)
                reflectiveCurveToRelative(2.0f, -0.9f, 2.0f, -2.0f)
                reflectiveCurveToRelative(-0.9f, -2.0f, -2.0f, -2.0f)
                close()
            }
        }
        return _moreHorz!!
    }

private var _moreHorz: ImageVector? = null

@Composable
private fun Preview_MoreHorz() {
    val bgColor = if (isSystemInDarkTheme()) Color.Black else Color.White
    val fgColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    Box(modifier = Modifier.background(bgColor)) {
        CompositionLocalProvider(LocalContentColor provides fgColor) {
            Icon(Icons.Filled.MoreHorz, "")
        }
    }
}
