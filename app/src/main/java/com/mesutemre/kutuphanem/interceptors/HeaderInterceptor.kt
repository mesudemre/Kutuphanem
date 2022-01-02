package com.mesutemre.kutuphanem.interceptors

import com.mesutemre.kutuphanem.util.APP_TOKEN_KEY
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import okhttp3.*
/**
 * @Author: mesutemre.celenk
 * @Date: 2.01.2022
 */
class HeaderInterceptor(val customSharedPreferences:CustomSharedPreferences) :Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request().newBuilder()
            .addHeader("Authorization",
            "Bearer "+customSharedPreferences.getStringFromSharedPreferences(APP_TOKEN_KEY).trim()).build();
        return chain.proceed(request);
    }
}