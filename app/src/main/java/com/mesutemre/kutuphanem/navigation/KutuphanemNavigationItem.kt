package com.mesutemre.kutuphanem.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mesutemre.kutuphanem.R

sealed class KutuphanemNavigationItem(
    val screenRoute: String,
    val showBottomBar: Boolean = false,
    val data: Any? = null,
    @DrawableRes val icon: Int? = null,
    @StringRes val title: Int? = null,
    val showTopBar: Boolean = false,
    @StringRes val pageTitle: Int? = null
) {
    object LoginScreen : KutuphanemNavigationItem(screenRoute = "kutuphanem_login_screen")

    object DashboardScreen : KutuphanemNavigationItem(
        screenRoute = "kutuphanem_dashboard_screen",
        icon = R.drawable.ic_baseline_home_24,
        title = R.string.anasayfaItem,
        showBottomBar = true,
        pageTitle = R.string.anasayfaItem
    )

    object BookListScreen : KutuphanemNavigationItem(
        screenRoute = "kutuphanem_kitap_list_screen",
        icon = R.drawable.ic_baseline_view_list_24,
        showBottomBar = true,
        title = R.string.listeItem
    )

    object ParameterScreen : KutuphanemNavigationItem(
        screenRoute = "kutuphanem_parameter_screen",
        icon = R.drawable.ic_baseline_settings_24,
        title = R.string.parametreItem,
        showTopBar = true,
        showBottomBar = true,
        pageTitle = R.string.parametreItem
    )

    object ProfileScreen : KutuphanemNavigationItem(
        screenRoute = "kutuphanem_profile_screen",
        icon = R.drawable.ic_baseline_person_24,
        title = R.string.profilimItem,
        showBottomBar = true
    )

    object ParameterYayinEviScreen : KutuphanemNavigationItem(
        screenRoute = "kutuphanem_parameter_yayinevi_screen",
        showTopBar = true,
        pageTitle = R.string.yayinEviLabel
    )

    object ParameterKitapTurScreen : KutuphanemNavigationItem(
        screenRoute = "kutuphanem_parameter_kitaptur_screen",
        showTopBar = true,
        pageTitle = R.string.kitapTurLabel
    )

    object ParameterEklemeScreen : KutuphanemNavigationItem(
        screenRoute = "kutuphanem_parameter_ekleme",
        showTopBar = true,
        pageTitle = R.string.parameterAddLabel
    )

    object DashboardSearchScreen : KutuphanemNavigationItem(
        screenRoute = "kutuphanem_dashboard_search_screen"
    )

    object KitapDetayScreen : KutuphanemNavigationItem(
        screenRoute = "kutuphanem_kitap_detay?kitapId={kitapId}&isArsiv={isFromArsiv}"
    )
}
