package com.upnext.scribble.presentation.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color


class ScribbleColors(
    primary: Color,
    text: Color,
    secondaryColor: Color,
    selectedColor: Color,
    editTextBackground: Color,
    editTextSelectedTextColor: Color,
    editTextColor: Color,
    background: Color,
    onBackground: Color,
    success: Color,
    error: Color,
    drawerHeaderBackground: Color,
    drawerHeaderOnBackground: Color,
    drawerBodyBackground: Color,
    drawerBodyOnBackground: Color,
    isLight: Boolean,
) {
    var primary by mutableStateOf(primary)
        private set

    var editTextBackground by mutableStateOf(editTextBackground)
        private set

    var editTextSelectedTextColor by mutableStateOf(editTextSelectedTextColor)
        private set

    var editTextColor by mutableStateOf(editTextColor)
        private set

    var text by mutableStateOf(text)
        private set

    var success by mutableStateOf(success)
        private set

    var error by mutableStateOf(error)
        private set

    var background by mutableStateOf(background)
        private set

    var isLight by mutableStateOf(isLight)
        private set

    var secondaryColor by mutableStateOf(secondaryColor)
        private set

    var selectedColor by mutableStateOf(selectedColor)
        private set

    var onBackground by mutableStateOf(onBackground)
        private set

    var drawerHeaderBackground by mutableStateOf(drawerHeaderBackground)
        private set

    var drawerHeaderOnBackground by mutableStateOf(drawerHeaderOnBackground)
        private set



    var drawerBodyBackground by mutableStateOf(drawerBodyBackground)
        private set

    var drawerBodyOnBackground by mutableStateOf(drawerBodyOnBackground)
        private set


    fun copy(
        primary: Color = this.primary,
        text: Color = this.text,
        background: Color = this.background,
        secondaryColor: Color = this.secondaryColor,
        selectedColor: Color = this.selectedColor,
        editTextBackground: Color = this.editTextBackground,
        editTextSelectedTextColor: Color = this.editTextSelectedTextColor,
        editTextColor: Color = this.editTextColor,
        onBackground: Color = this.onBackground,
        success: Color = this.success,
        error: Color = this.error,
        drawerHeaderBackground: Color = this.drawerHeaderBackground,
        drawerHeaderOnBackground: Color = this.drawerHeaderOnBackground,
        drawerBodyBackground: Color = this.drawerBodyBackground,
        drawerBodyOnBackground: Color = this.drawerBodyOnBackground,
        isLight: Boolean = this.isLight,
    ) = ScribbleColors(
        primary = primary,
        text = text,
        secondaryColor = secondaryColor,
        selectedColor = selectedColor,
        editTextColor = editTextColor,
        editTextBackground = editTextBackground,
        editTextSelectedTextColor = editTextSelectedTextColor,
        background = background,
        onBackground = onBackground,
        success = success,
        error = error,
        drawerHeaderBackground = drawerHeaderBackground,
        drawerHeaderOnBackground = drawerHeaderOnBackground,
        drawerBodyBackground = drawerBodyBackground,
        drawerBodyOnBackground = drawerBodyOnBackground,
        isLight = isLight,
    )

    fun updateColorsFrom(other: ScribbleColors) {
        primary = other.primary
        text = other.text
        secondaryColor = other.secondaryColor
        editTextColor = other.editTextColor
        editTextBackground = other.editTextBackground
        editTextSelectedTextColor = other.editTextSelectedTextColor
        onBackground = other.onBackground
        success = other.success
        background = other.background
        drawerHeaderBackground = other.drawerHeaderBackground
        drawerHeaderOnBackground = other.drawerHeaderOnBackground
        drawerBodyBackground = other.drawerBodyBackground
        drawerBodyOnBackground = other.drawerBodyOnBackground
        error = other.error
        isLight = other.isLight
    }
}
