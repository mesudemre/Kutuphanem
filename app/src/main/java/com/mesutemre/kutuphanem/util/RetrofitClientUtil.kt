package com.mesutemre.kutuphanem.util

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitClientUtil {

    companion object{
        fun getClient(): Retrofit{
            return Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

        fun getClient(token:String): Retrofit{
            val httpClient: OkHttpClient.Builder = OkHttpClient.Builder();
            httpClient.addInterceptor(object:Interceptor{
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request:Request = chain.request().newBuilder().addHeader("Authorization",
                            "Bearer "+token.trim()).build();
                    return chain.proceed(request);
                }
            });
            return Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
    }

}