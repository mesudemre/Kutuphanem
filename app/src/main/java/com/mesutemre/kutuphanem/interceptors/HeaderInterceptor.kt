package com.mesutemre.kutuphanem.interceptors

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mesutemre.kutuphanem.util.APP_TOKEN_KEY
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * @Author: mesutemre.celenk
 * @Date: 2.01.2022
 */
class HeaderInterceptor(
    private val dataStore: DataStore<Preferences>
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            dataStore.data.catch {
                emit(emptyPreferences())
            }.map {
                it[stringPreferencesKey(APP_TOKEN_KEY)] ?: ""
            }.first()
        }
        val request: Request = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                "Bearer " + token.trim()
            ).build();
        return chain.proceed(request);
    }
}