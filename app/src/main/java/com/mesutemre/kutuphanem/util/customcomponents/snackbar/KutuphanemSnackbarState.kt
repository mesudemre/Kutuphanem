package com.mesutemre.kutuphanem.util.customcomponents.snackbar

import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mesutemre.kutuphanem.model.SUCCESS
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * @Author: mesutemre.celenk
 * @Date: 1.05.2022
 */
@Stable
class KutuphanemSnackbarState(
    val snackbarHostState: SnackbarHostState
) {
    private val mutex = Mutex()

    var currentSnackbarType by mutableStateOf<Int>(SUCCESS)
        private set

    suspend fun showSnackbar(
        message: String,
        actionLabel: String? = null,
        duration: SnackbarDuration = SnackbarDuration.Long,
        type: Int
    ): SnackbarResult = mutex.withLock {
        try {
            currentSnackbarType = type
            snackbarHostState.showSnackbar(message, actionLabel, duration)
        } finally {
        }
    }
}