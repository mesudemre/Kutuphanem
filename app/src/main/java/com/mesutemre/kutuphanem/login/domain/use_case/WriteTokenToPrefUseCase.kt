package com.mesutemre.kutuphanem.login.domain.use_case

import com.mesutemre.kutuphanem.util.APP_TOKEN_KEY
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 7.05.2022
 */
class WriteTokenToPrefUseCase @Inject constructor(private val customSharedPreferences: CustomSharedPreferences) {

    operator fun invoke(token: String) {
        customSharedPreferences.putToSharedPref(APP_TOKEN_KEY, token)
    }
}