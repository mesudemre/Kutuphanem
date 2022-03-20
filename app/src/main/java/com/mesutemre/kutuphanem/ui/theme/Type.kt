package com.mesutemre.kutuphanem.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mesutemre.kutuphanem.R

val sourceSans = FontFamily(
    Font(R.font.source_sans_pro_regular),
    Font(R.font.source_sans_pro_bold, weight = FontWeight.Bold)
)

val fontAllegra = FontFamily(
    Font(R.font.alegreya_regular),
    Font(R.font.alegreya_bold, weight = FontWeight.Bold)
)

val fontUbuntu = FontFamily(
    Font(R.font.ubuntu_regular),
    Font(R.font.ubuntu_bold, weight = FontWeight.Bold)
)

val Typography = Typography(
    button = TextStyle()
)

val Typography.thinySourceSansLacivert: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.thinySourceSansLacivertBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.thinySourceSansTransparent: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.thinySourceSansTransparentBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.thinySourceSansBlack: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.thinySourceSansBlackBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.thinySourceSansWhite: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.thinySourceSansWhiteBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.thinySourceSansPrimary: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.thinySourceSansPrimaryBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.thinySourceSansError: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.thinySourceSansErrorBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.thinyAllegraLacivert: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.thinyAllegraLacivertBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.thinyAllegraTransparent: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.thinyAllegraTransparentBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.thinyAllegraBlack: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.thinyAllegraBlackBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.thinyAllegraWhite: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.thinyAllegraWhiteBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.thinyAllegraPrimary: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.thinyAllegraPrimaryBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.thinyAllegraError: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.thinyAllegraErrorBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.thinyUbuntuLacivert: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.thinyUbuntuLacivertBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.thinyUbuntuTransparent: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.thinyUbuntuTransparentBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.thinyUbuntuBlack: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.thinyUbuntuBlackBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.thinyUbuntuWhite: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.thinyUbuntuWhiteBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.thinyUbuntuPrimary: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.thinyUbuntuPrimaryBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.thinyUbuntuError: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.thinyUbuntuErrorBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 10.ssp,
            lineHeight = 20.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.smallSourceSansLacivert: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.smallSourceSansLacivertBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.smallSourceSansTransparent: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.smallSourceSansTransparentBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.smallSourceSansBlack: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.smallSourceSansBlackBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.smallSourceSansWhite: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.smallSourceSansWhiteBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.smallSourceSansPrimary: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.smallSourceSansPrimaryBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.smallSourceSansError: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.smallSourceSansErrorBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.smallAllegraLacivert: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.smallAllegraLacivertBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.smallAllegraTransparent: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.smallAllegraTransparentBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.smallAllegraBlack: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.smallAllegraBlackBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.smallAllegraWhite: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.smallAllegraWhiteBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.smallAllegraPrimary: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.smallAllegraPrimaryBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.smallAllegraError: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.smallAllegraErrorBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.smallUbuntuLacivert: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.smallUbuntuLacivertBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.smallUbuntuTransparent: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.smallUbuntuTransparentBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.smallUbuntuBlack: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.smallUbuntuBlackBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.smallUbuntuWhite: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.smallUbuntuWhiteBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.smallUbuntuPrimary: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.smallUbuntuPrimaryBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.smallUbuntuError: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.smallUbuntuErrorBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 12.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.normalSourceSansLacivert: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.normalSourceSansLacivertBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.normalSourceSansTransparent: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.normalSourceSansTransparentBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.normalSourceSansBlack: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.normalSourceSansBlackBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.normalSourceSansWhite: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.normalSourceSansWhiteBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.normalSourceSansPrimary: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.normalSourceSansPrimaryBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.normalSourceSansError: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.normalSourceSansErrorBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.normalAllegraLacivert: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.normalAllegraLacivertBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.normalAllegraTransparent: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.normalAllegraTransparentBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.normalAllegraBlack: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.normalAllegraBlackBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.normalAllegraWhite: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.normalAllegraWhiteBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.normalAllegraPrimary: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.normalAllegraPrimaryBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.normalAllegraError: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.normalAllegraErrorBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.normalUbuntuLacivert: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.normalUbuntuLacivertBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.normalUbuntuTransparent: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.normalUbuntuTransparentBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.normalUbuntuBlack: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.normalUbuntuBlackBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.normalUbuntuWhite: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.normalUbuntuWhiteBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.normalUbuntuPrimary: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.normalUbuntuPrimaryBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.normalUbuntuError: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.normalUbuntuErrorBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 14.ssp,
            lineHeight = 22.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.mediumSourceSansLacivert: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.mediumSourceSansLacivertBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.mediumSourceSansTransparent: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.mediumSourceSansTransparentBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.mediumSourceSansBlack: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.mediumSourceSansBlackBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.mediumSourceSansWhite: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.mediumSourceSansWhiteBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.mediumSourceSansPrimary: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.mediumSourceSansPrimaryBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.mediumSourceSansError: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.mediumSourceSansErrorBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.mediumAllegraLacivert: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.mediumAllegraLacivertBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.mediumAllegraTransparent: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.mediumAllegraTransparentBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.mediumAllegraBlack: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.mediumAllegraBlackBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.mediumAllegraWhite: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.mediumAllegraWhiteBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.mediumAllegraPrimary: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.mediumAllegraPrimaryBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.mediumAllegraError: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.mediumAllegraErrorBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.mediumUbuntuLacivert: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.mediumUbuntuLacivertBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.mediumUbuntuTransparent: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.mediumUbuntuTransparentBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.mediumUbuntuBlack: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.mediumUbuntuBlackBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.mediumUbuntuWhite: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.mediumUbuntuWhiteBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.mediumUbuntuPrimary: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.mediumUbuntuPrimaryBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.mediumUbuntuError: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.mediumUbuntuErrorBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 16.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.largeSourceSansLacivert: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.W700,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.largeSourceSansLacivertBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.largeSourceSansTransparent: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.W700,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.largeSourceSansTransparentBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.largeSourceSansBlack: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.W700,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.largeSourceSansBlackBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.largeSourceSansWhite: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.W700,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.largeSourceSansWhiteBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.largeSourceSansPrimary: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.W700,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.largeSourceSansPrimaryBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.largeSourceSansError: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.W700,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.largeSourceSansErrorBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.largeAllegraLacivert: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.W700,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.largeAllegraLacivertBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.largeAllegraTransparent: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.W700,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.largeAllegraTransparentBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.largeAllegraBlack: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.W700,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.largeAllegraBlackBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.largeAllegraWhite: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.W700,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.largeAllegraWhiteBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.largeAllegraPrimary: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.W700,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.largeAllegraPrimaryBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.largeAllegraError: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.W700,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.largeAllegraErrorBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.largeUbuntuLacivert: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.W700,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.largeUbuntuLacivertBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.largeUbuntuTransparent: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.W700,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.largeUbuntuTransparentBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.largeUbuntuBlack: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.W700,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.largeUbuntuBlackBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.largeUbuntuWhite: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.W700,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.largeUbuntuWhiteBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.largeUbuntuPrimary: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.W700,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.largeUbuntuPrimaryBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.largeUbuntuError: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.W700,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.largeUbuntuErrorBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 18.ssp,
            lineHeight = 24.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.xlargeSourceSansLacivert: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.W800,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.xlargeSourceSansLacivertBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.xlargeSourceSansTransparent: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.W800,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.xlargeSourceSansTransparentBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.xlargeSourceSansBlack: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.W800,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.xlargeSourceSansBlackBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.xlargeSourceSansWhite: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.W800,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.xlargeSourceSansWhiteBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.xlargeSourceSansPrimary: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.W800,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.xlargeSourceSansPrimaryBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.xlargeSourceSansError: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.W800,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.xlargeSourceSansErrorBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.xlargeAllegraLacivert: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.W800,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.xlargeAllegraLacivertBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.xlargeAllegraTransparent: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.W800,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.xlargeAllegraTransparentBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.xlargeAllegraBlack: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.W800,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.xlargeAllegraBlackBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.xlargeAllegraWhite: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.W800,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.xlargeAllegraWhiteBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.xlargeAllegraPrimary: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.W800,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.xlargeAllegraPrimaryBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.xlargeAllegraError: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.W800,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.xlargeAllegraErrorBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.xlargeUbuntuLacivert: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.W800,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.xlargeUbuntuLacivertBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.xlargeUbuntuTransparent: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.W800,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.xlargeUbuntuTransparentBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.xlargeUbuntuBlack: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.W800,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.xlargeUbuntuBlackBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.xlargeUbuntuWhite: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.W800,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.xlargeUbuntuWhiteBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.xlargeUbuntuPrimary: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.W800,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.xlargeUbuntuPrimaryBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.xlargeUbuntuError: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.W800,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.xlargeUbuntuErrorBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 20.ssp,
            lineHeight = 30.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.xxlargeSourceSansLacivert: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.W900,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.xxlargeSourceSansLacivertBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.xxlargeSourceSansTransparent: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.W900,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.xxlargeSourceSansTransparentBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.xxlargeSourceSansBlack: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.W900,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.xxlargeSourceSansBlackBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.xxlargeSourceSansWhite: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.W900,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.xxlargeSourceSansWhiteBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.xxlargeSourceSansPrimary: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.W900,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.xxlargeSourceSansPrimaryBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.xxlargeSourceSansError: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.W900,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.xxlargeSourceSansErrorBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.xxlargeAllegraLacivert: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.W900,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.xxlargeAllegraLacivertBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.xxlargeAllegraTransparent: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.W900,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.xxlargeAllegraTransparentBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.xxlargeAllegraBlack: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.W900,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.xxlargeAllegraBlackBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.xxlargeAllegraWhite: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.W900,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.xxlargeAllegraWhiteBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.xxlargeAllegraPrimary: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.W900,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.xxlargeAllegraPrimaryBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.xxlargeAllegraError: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.W900,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.xxlargeAllegraErrorBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.xxlargeUbuntuLacivert: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.W900,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.xxlargeUbuntuLacivertBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.xxlargeUbuntuTransparent: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.W900,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.xxlargeUbuntuTransparentBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.xxlargeUbuntuBlack: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.W900,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.xxlargeUbuntuBlackBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.xxlargeUbuntuWhite: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.W900,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.xxlargeUbuntuWhiteBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.xxlargeUbuntuPrimary: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.W900,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.xxlargeUbuntuPrimaryBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.xxlargeUbuntuError: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.W900,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.xxlargeUbuntuErrorBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 24.ssp,
            lineHeight = 32.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.bigSourceSansLacivert: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.bigSourceSansLacivertBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.bigSourceSansTransparent: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.bigSourceSansTransparentBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.bigSourceSansBlack: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.bigSourceSansBlackBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.bigSourceSansWhite: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.bigSourceSansWhiteBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.bigSourceSansPrimary: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.bigSourceSansPrimaryBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.bigSourceSansError: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Normal,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.bigSourceSansErrorBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = sourceSans,
            fontWeight = FontWeight.Bold,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.bigAllegraLacivert: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.bigAllegraLacivertBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.bigAllegraTransparent: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.bigAllegraTransparentBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.bigAllegraBlack: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.bigAllegraBlackBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.bigAllegraWhite: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.bigAllegraWhiteBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.bigAllegraPrimary: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.bigAllegraPrimaryBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.bigAllegraError: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Normal,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.bigAllegraErrorBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontAllegra,
            fontWeight = FontWeight.Bold,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.bigUbuntuLacivert: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.bigUbuntuLacivertBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.lacivert
        )
    }

val Typography.bigUbuntuTransparent: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.bigUbuntuTransparentBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.transparent
        )
    }

val Typography.bigUbuntuBlack: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.bigUbuntuBlackBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.black
        )
    }

val Typography.bigUbuntuWhite: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.bigUbuntuWhiteBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.white
        )
    }

val Typography.bigUbuntuPrimary: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.bigUbuntuPrimaryBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.primaryTextColor
        )
    }

val Typography.bigUbuntuError: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Normal,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }

val Typography.bigUbuntuErrorBold: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = fontUbuntu,
            fontWeight = FontWeight.Bold,
            fontSize = 48.ssp,
            lineHeight = 56.ssp,
            color = MaterialTheme.colorPalette.kirmizi
        )
    }