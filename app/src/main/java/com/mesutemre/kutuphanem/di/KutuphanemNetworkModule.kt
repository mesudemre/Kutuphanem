package com.mesutemre.kutuphanem.di

import android.content.Context
import com.mesutemre.kutuphanem.BuildConfig
import com.mesutemre.kutuphanem.interceptors.HeaderInterceptor
import com.mesutemre.kutuphanem.interceptors.NetworkConnectionInterceptor
import com.mesutemre.kutuphanem.kitap.service.IKitapService
import com.mesutemre.kutuphanem.parametre.service.IParametreService
import com.mesutemre.kutuphanem.auth.service.KullaniciService
import com.mesutemre.kutuphanem.util.CustomSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

/**
 * @Author: mesutemre.celenk
 * @Date: 12.09.2021
 */
@Module
@InstallIn(SingletonComponent::class)
class KutuphanemNetworkModule {

    private val loggingInterceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            if (BuildConfig.DEBUG) {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    @Singleton
    @Provides
    fun provideHttpClient(customSharedPreferences: CustomSharedPreferences,
                          @ApplicationContext context: Context
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor(customSharedPreferences))
            .addInterceptor(NetworkConnectionInterceptor(context))
            .addInterceptor(loggingInterceptor)
            .build();
    }

    @Singleton
    @Provides
    fun provideParametreApi(retrofit: Retrofit): IParametreService = retrofit.create(
        IParametreService::class.java);

    @Singleton
    @Provides
    fun provideKullaniciApi(retrofit: Retrofit): KullaniciService = retrofit.create(KullaniciService::class.java);

    @Singleton
    @Provides
    fun provideKitapApi(retrofit: Retrofit): IKitapService = retrofit.create(IKitapService::class.java);
}