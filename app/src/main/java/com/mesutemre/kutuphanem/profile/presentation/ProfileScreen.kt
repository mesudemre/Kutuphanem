package com.mesutemre.kutuphanem.profile.presentation

import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

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

}