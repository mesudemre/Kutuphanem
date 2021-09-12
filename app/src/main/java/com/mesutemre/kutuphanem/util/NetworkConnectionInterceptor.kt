package com.mesutemre.kutuphanem.util

import android.content.Context
import android.net.ConnectivityManager
import com.mesutemre.kutuphanem.R
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * @Author: mesutemre.celenk
 * @Date: 12.09.2021
 */
class NetworkConnectionInterceptor(context: Context) : Interceptor {
    private val mContext: Context = context;

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected)
            throw NoConnectionException()
        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    private val isConnected: Boolean
        get() {
            val connectivityManager =
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = connectivityManager.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
        }

    inner class NoConnectionException : IOException() {
        override val message: String
            get() = mContext.getString(R.string.noNetworkConnection);
    }

}