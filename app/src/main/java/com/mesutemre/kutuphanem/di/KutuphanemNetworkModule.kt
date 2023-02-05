package com.mesutemre.kutuphanem.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.gson.GsonBuilder
import com.mesutemre.kutuphanem.BuildConfig
import com.mesutemre.kutuphanem.auth.service.KullaniciService
import com.mesutemre.kutuphanem.dashboard.data.remote.IDashBoardApi
import com.mesutemre.kutuphanem.interceptors.HeaderInterceptor
import com.mesutemre.kutuphanem.interceptors.NetworkConnectionInterceptor
import com.mesutemre.kutuphanem.kitap.service.IKitapService
import com.mesutemre.kutuphanem.kitap_detay.data.remote.IKitapYorumApi
import com.mesutemre.kutuphanem.kitap_ekleme.data.remote.IKitapEkleApi
import com.mesutemre.kutuphanem.kitap_liste.data.remote.IKitapApi
import com.mesutemre.kutuphanem.login.data.remote.LoginService
import com.mesutemre.kutuphanem.parameter.kitaptur.data.remote.dto.IKitapTurApi
import com.mesutemre.kutuphanem.parameter.yayinevi.data.remote.dto.IYayinEviApi
import com.mesutemre.kutuphanem.profile.data.remote.IKullaniciApi
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
            .addConverterFactory(GsonConverterFactory.create(
                GsonBuilder().setDateFormat("yyyy-MM-dd").create()
            ))
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    @Singleton
    @Provides
    fun provideHttpClient(
        dataStore: DataStore<Preferences>,
        @ApplicationContext context: Context
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor(dataStore))
            .addInterceptor(NetworkConnectionInterceptor(context))
            .addInterceptor(loggingInterceptor)
            .build();
    }


    @Singleton
    @Provides
    fun provideKullaniciApi(retrofit: Retrofit): KullaniciService =
        retrofit.create(KullaniciService::class.java);

    @Singleton
    @Provides
    fun provideKitapApi(retrofit: Retrofit): IKitapService =
        retrofit.create(IKitapService::class.java);


    /*-------------------- Compose --------------------*/

    @Singleton
    @Provides
    fun provideLoginAPI(retrofit: Retrofit): LoginService =
        retrofit.create(LoginService::class.java)

    @Singleton
    @Provides
    fun provideParametreYayinEviAPI(retrofit: Retrofit): IYayinEviApi =
        retrofit.create(IYayinEviApi::class.java)

    @Singleton
    @Provides
    fun provideParametreKitapTurAPI(retrofit: Retrofit): IKitapTurApi =
        retrofit.create(IKitapTurApi::class.java)

    @Singleton
    @Provides
    fun providerKullaniciAPI(retrofit: Retrofit): IKullaniciApi =
        retrofit.create(IKullaniciApi::class.java)

    @Singleton
    @Provides
    fun providerDashBoardAPI(retrofit: Retrofit): IDashBoardApi =
        retrofit.create(IDashBoardApi::class.java)

    @Singleton
    @Provides
    fun providerKitapAPI(retrofit: Retrofit): IKitapApi = retrofit.create(IKitapApi::class.java)

    @Singleton
    @Provides
    fun providerKitapYorumAPI(retrofit: Retrofit): IKitapYorumApi =
        retrofit.create(IKitapYorumApi::class.java)

    @Singleton
    @Provides
    fun providerKitapEkleAPI(retrofit: Retrofit): IKitapEkleApi =
        retrofit.create(IKitapEkleApi::class.java)

}