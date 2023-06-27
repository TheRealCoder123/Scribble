package com.upnext.scribble.presentation.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

fun lightColors() = ScribbleColors(
    primary = Color(0xFF026AA7),
    text = Color(0xFF000000),
    secondaryColor = Color(0xFF89F889),
    selectedColor = Color(0xFF026AA7),
    background = Color(0xFFF5F5F5),
    onBackground = Color(0xFFFFFFFF),
    success = Color(0xFF2ECC71),
    error = Color(0xFFE74C3C),
    editTextSelectedTextColor = Color.Gray,
    editTextBackground = Color.LightGray,
    editTextColor = Color.Black,
    isLight = true,
)

fun darkColors() = ScribbleColors(
    primary = Color(0xE8313131),
    text = Color(0xFFFFFFFF),
    secondaryColor = Color(0xFF89F889),
    selectedColor = Color(0xFF026AA7),
    background = Black,
    onBackground = Color(0xFF1B1B1B),
    success = Color(0xFF44BD32),
    error = Color(0xFFC23616),
    editTextSelectedTextColor = Color.Gray,
    editTextBackground = Color(0xFF171717),
    editTextColor = Color.White,
    isLight = false,
)

val LocalSpaces = staticCompositionLocalOf { Spaces() }

val LocalColors = staticCompositionLocalOf { lightColors() }

val LocalTypography = staticCompositionLocalOf {
    Typography()
}

object ScribbleTheme {
    val colors: ScribbleColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val spaces: Spaces
        @Composable
        @ReadOnlyComposable
        get() = LocalSpaces.current
}




@Composable
fun ScribbleTheme(
    spaces: Spaces = ScribbleTheme.spaces,
    typography: Typography = ScribbleTheme.typography,
    colors: ScribbleColors = lightColors(),
    darkColors: ScribbleColors = darkColors(),
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val currentColors = remember { if (darkTheme) darkColors else colors }
    val rememberedColors = remember { currentColors.copy() }.apply { updateColorsFrom(currentColors) }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = if (!darkTheme) Color(0xFF026AA7).toArgb() else Color(0xE8313131).toArgb()
        }
    }


    CompositionLocalProvider(
        LocalColors provides rememberedColors,
        LocalSpaces provides spaces,
        LocalTypography provides typography,
    ) {
        ProvideTextStyle(typography.body1, content = content)
    }


}