package com.mesutemre.kutuphanem.interceptors

import android.util.Log
import com.mesutemre.kutuphanem.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.lang.StringBuilder

/**
 * @Author: mesutemre.celenk
 * @Date: 2.01.2022
 */
class HttpLoggingInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request();
        if (BuildConfig.DEBUG) {
            val requestStringBuilder = StringBuilder();
            requestStringBuilder.append("Method -> ")
            requestStringBuilder.append(request.method());
            requestStringBuilder.append(" | Url -> ");
            requestStringBuilder.append(request.url().toString());
            Log.i("Service",requestStringBuilder.toString());
        }
        return chain.proceed(request);
    }
}