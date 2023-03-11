package com.mesutemre.kutuphanem.profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.navigationBarsPadding
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.profile.presentation.components.ProfileAboutArea
import com.mesutemre.kutuphanem.profile.presentation.components.ProfileHeader
import com.mesutemre.kutuphanem.profile.presentation.components.ProfileItemCard
import com.mesutemre.kutuphanem.profile.presentation.components.ProfileItemTitle
import com.mesutemre.kutuphanem.util.KutuphanemAppState
import com.mesutemre.kutuphanem.util.rememberKutuphanemAppState
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_ui.button.ButtonSize
import com.mesutemre.kutuphanem_ui.button.KutuphanemBaseButton
import com.mesutemre.kutuphanem_ui.theme.colorPalette
import com.mesutemre.kutuphanem_ui.theme.sdp

@Composable
fun ProfileScreen(
    showSnackbar: (String, SnackbarDuration, Int) -> Unit,
    navController: NavController,
    viewModel: ProfileScreenViewModel = hiltViewModel()
) {
    val onBackPressed = remember<() -> Unit> {
        {
            navController.popBackStack()
        }
    }
    val state = viewModel.state.collectAsState()
    val kutuphanemAppState: KutuphanemAppState = rememberKutuphanemAppState()
    val scrollState = rememberLazyListState()

    Scaffold(modifier = Modifier.navigationBarsPadding(),
        scaffoldState = kutuphanemAppState.scaffoldState,
        topBar = {
            when (state.value.kullaniciBilgiResourceEvent) {
                is BaseResourceEvent.Success -> {
                    ProfileHeader(
                        adSoyad = state.value.kullaniciBilgiResourceEvent.data?.adSoyad ?: "",
                        kullaniciResim = state.value.kullaniciBilgiResourceEvent.data?.resim ?: "",
                        lazyScrollState = scrollState
                    ) {
                        onBackPressed()
                    }
                }
                else -> Unit
            }
        }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorPalette.white)
        ) {
            when (state.value.kullaniciBilgiResourceEvent) {
                is BaseResourceEvent.Success -> {
                    LazyColumn(
                        state = scrollState,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(32.sdp))
                        }
                        item {
                            ProfileItemTitle(title = stringResource(id = R.string.profilBilgiHakkinda))
                        }
                        item {
                            ProfileAboutArea(aboutText = stringResource(id = R.string.profilOrnekHakkinda))
                        }
                        item {
                            ProfileItemTitle(title = stringResource(id = R.string.profilBilgiKisiselBilgiler))
                        }
                        item {
                            Spacer(modifier = Modifier.height(16.sdp))
                        }
                        item {
                            ProfileItemCard(
                                icon = ImageVector.vectorResource(id = R.drawable.ic_kutuphanem_person),
                                title = stringResource(id = R.string.profilGuncellemeAdSoyadBilgiLabel)
                            ) {

                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(16.sdp))
                        }
                        item {
                            ProfileItemCard(
                                icon = ImageVector.vectorResource(id = R.drawable.ic_kutuphanem_birthday),
                                title = stringResource(id = R.string.profilGuncellemeCinsiyetDogumTarCinsiyetBilgiLabel)
                            ) {

                            }
                        }
                        item {
                            ProfileItemTitle(title = stringResource(id = R.string.profilGuncellemeTercihlerLabel))
                        }
                        item {
                            Spacer(modifier = Modifier.height(16.sdp))
                        }
                        item {
                            ProfileItemCard(
                                icon = ImageVector.vectorResource(id = R.drawable.ic_kutuphanem_interests),
                                title = stringResource(id = R.string.profilGuncellemeIlgiAlanlariLabel)
                            ) {

                            }
                        }
                        item {
                            ProfileItemTitle(title = stringResource(id = R.string.profilBilgiBildirim))
                        }
                        item {
                            Spacer(modifier = Modifier.height(16.sdp))
                        }
                        item {
                            ProfileItemCard(
                                icon = ImageVector.vectorResource(id = R.drawable.ic_kutuphanem_notification),
                                title = stringResource(id = R.string.profilBilgiBildirimItem)
                            ) {

                            }
                        }

                        item {
                            ProfileItemTitle(title = stringResource(id = R.string.profilBilgiAppAbout))
                        }
                        item {
                            Spacer(modifier = Modifier.height(16.sdp))
                        }
                        item {
                            ProfileItemCard(
                                icon = ImageVector.vectorResource(id = R.drawable.ic_kutuphanem_about_app),
                                title = stringResource(id = R.string.profilBilgiAppInfo)
                            ) {

                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(16.sdp))
                        }
                        item {
                            ProfileItemCard(
                                icon = ImageVector.vectorResource(id = R.drawable.ic_kutuphanem_about_version),
                                title = stringResource(id = R.string.profilBilgiAppVersionNotes)
                            ) {

                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(16.sdp))
                        }
                        item {
                            ProfileItemCard(
                                icon = ImageVector.vectorResource(id = R.drawable.ic_kutuphanem_about_bug),
                                title = stringResource(id = R.string.profilBilgiAppVersionBug)
                            ) {

                            }
                        }
                        item {
                            ProfileItemTitle(title = stringResource(id = R.string.profilBilgiLogout))
                        }
                        item {
                            Spacer(modifier = Modifier.height(16.sdp))
                        }
                        item {
                            KutuphanemBaseButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.sdp, end = 16.sdp, bottom = 48.sdp),
                                buttonSize = ButtonSize.LARGE,
                                text = stringResource(id = R.string.profilBilgiLogout),
                                textColor = MaterialTheme.colorPalette.white,
                                backgroundBrush = Brush.horizontalGradient(
                                    listOf(
                                        MaterialTheme.colorPalette.kirmizi,
                                        MaterialTheme.colorPalette.acik_kirmizi,
                                        MaterialTheme.colorPalette.white
                                    )
                                )
                            ) {

                            }
                        }
                    }
                }
                else -> Unit
            }
        }

    }
}