package com.mesutemre.kutuphanem.login.domain.use_case

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.mesutemre.kutuphanem.util.APP_TOKEN_KEY
import com.mesutemre.kutuphanem.util.saveData
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 7.05.2022
 */
class WriteTokenToPrefUseCase @Inject constructor(private val dataStore: DataStore<Preferences>) {

    suspend operator fun invoke(token: String) {
        dataStore.saveData(APP_TOKEN_KEY, token)
    }
}