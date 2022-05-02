package com.mesutemre.kutuphanem.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = ColorPalette(
    material = darkColors(
        primary = primaryTextColor,
        primaryVariant = lacivert,
        secondary = loginShapeBackground,
        secondaryVariant = lacivert,
        background = white,
        surface = transparent,
        error = kirmizi
    ),
    loginShapeBackground = loginShapeBackground,
    black = black,
    white = white,
    bottom_start_color = bottom_start_color,
    bottom_center_color = bottom_center_color,
    bottom_end_color = bottom_end_color,
    turuncu = turuncu,
    nav_bottom_gri = nav_bottom_gri,
    nav_item_icon_tint = nav_item_icon_tint,
    nav_item_text = nav_item_text,
    vbsari = vbsari,
    transparent = transparent,
    loginBackColor = loginBackColor,
    primaryTextColor = primaryTextColor,
    kirmizi = kirmizi,
    lacivert = lacivert,
    fistikYesil = fistikYesil,
    acikTuruncu = acikTuruncu,
    kategoriBackground = kategoriBackground,
    acikSari = acikSari,
    sari = sari,
    imageShadow = imageShadow,
    shrim_gray = shrim_gray,
    kitap_detay_item_background = kitap_detay_item_background,
    acik_kirmizi = acik_kirmizi,
    kahverengi = kahverengi,
    acikGri = acikGri,
    ratingYellow = ratingYellow,
    acikLacivert = acikLacivert,
    googleDarkGray = googleDarkGray,
    otherGrayLight = OtherGrayLight,
    secondaryGray = SecondaryGray
)

private val LightColorPalette = ColorPalette(
    material = darkColors(
        primary = primaryTextColor,
        primaryVariant = lacivert,
        secondary = loginShapeBackground,
        secondaryVariant = lacivert,
        background = white,
        surface = transparent,
        error = kirmizi
    ),
    loginShapeBackground = loginShapeBackground,
    black = black,
    white = white,
    bottom_start_color = bottom_start_color,
    bottom_center_color = bottom_center_color,
    bottom_end_color = bottom_end_color,
    turuncu = turuncu,
    nav_bottom_gri = nav_bottom_gri,
    nav_item_icon_tint = nav_item_icon_tint,
    nav_item_text = nav_item_text,
    vbsari = vbsari,
    transparent = transparent,
    loginBackColor = loginBackColor,
    primaryTextColor = primaryTextColor,
    kirmizi = kirmizi,
    lacivert = lacivert,
    fistikYesil = fistikYesil,
    acikTuruncu = acikTuruncu,
    kategoriBackground = kategoriBackground,
    acikSari = acikSari,
    sari = sari,
    imageShadow = imageShadow,
    shrim_gray = shrim_gray,
    kitap_detay_item_background = kitap_detay_item_background,
    acik_kirmizi = acik_kirmizi,
    kahverengi = kahverengi,
    acikGri = acikGri,
    ratingYellow = ratingYellow,
    acikLacivert = acikLacivert,
    googleDarkGray = googleDarkGray,
    otherGrayLight = OtherGrayLight,
    secondaryGray = SecondaryGray
)

private val LocalColors = staticCompositionLocalOf { LightColorPalette }

@Composable
fun KutuphanemTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val systemUiController = rememberSystemUiController()
    if (darkTheme) {
        systemUiController.setStatusBarColor(color = Color.Transparent)
        systemUiController.setNavigationBarColor(color = Color.Black)
    } else {
        systemUiController.setStatusBarColor(color = Color.Transparent)
        systemUiController.setNavigationBarColor(color = Color.Black)
    }

    CompositionLocalProvider(LocalColors provides colors) {
        MaterialTheme(
            colors = colors.material,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

val MaterialTheme.colorPalette: ColorPalette
    @Composable
    get() = LocalColors.current