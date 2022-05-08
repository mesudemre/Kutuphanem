package com.mesutemre.kutuphanem.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mesutemre.kutuphanem.R

sealed class KutuphanemNavigationItem(
    val screenRoute: String,
    val showBottomBar: Boolean = false,
    val data: Any? = null,
    @DrawableRes val icon: Int? = null,
    @StringRes val title: Int? = null
) {
    object LoginScreen : KutuphanemNavigationItem(screenRoute = "kutuphanem_login_screen")
    object MainScreen : KutuphanemNavigationItem(
        screenRoute = "kutuphanem_main_screen",
        icon = R.drawable.ic_baseline_home_24,
        title = R.string.anasayfaItem
    )

    object BookListScreen : KutuphanemNavigationItem(
        screenRoute = "kutuphanem_kitap_list_screen",
        icon = R.drawable.ic_baseline_view_list_24,
        title = R.string.listeItem
    )

    object ParameterScreen : KutuphanemNavigationItem(
        screenRoute = "kutuphanem_parameter_screen",
        icon = R.drawable.ic_baseline_settings_24,
        title = R.string.parametreItem
    )

    object ProfileScreen : KutuphanemNavigationItem(
        screenRoute = "kutuphanem_profile_screen",
        icon = R.drawable.ic_baseline_person_24,
        title = R.string.profilimItem
    )
}
