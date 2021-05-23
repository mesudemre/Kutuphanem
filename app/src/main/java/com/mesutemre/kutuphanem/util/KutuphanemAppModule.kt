package com.mesutemre.kutuphanem.util

import android.content.Context
import androidx.room.Room
import com.mesutemre.kutuphanem.database.KutuphanemDatabase
import com.mesutemre.kutuphanem.service.IKitapService
import com.mesutemre.kutuphanem.service.IParametreService
import com.mesutemre.kutuphanem.service.KullaniciService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KutuphanemAppModule {

    @Singleton
    @Provides
    fun provideKutuphanemDatabase
                (@ApplicationContext context:Context) = Room.databaseBuilder(
        context,
        KutuphanemDatabase::class.java,
        KUTUPHANEM_DB_NAME
    ).build();

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient):Retrofit =
        Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    @Singleton
    @Provides
    fun provideHttpClient(customSharedPreferences: CustomSharedPreferences):OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(object:Interceptor{
            override fun intercept(chain: Interceptor.Chain): Response {
                val request:Request = chain.request().newBuilder().addHeader("Authorization",
                    "Bearer "+customSharedPreferences.getStringFromSharedPreferences(APP_TOKEN_KEY).trim()).build();
                return chain.proceed(request);
            }
        }).build();
    }

    @Singleton
    @Provides
    fun provideParametreDao(database: KutuphanemDatabase) = database.getParametreDao();

    @Singleton
    @Provides
    fun provideParametreApi(retrofit: Retrofit):IParametreService = retrofit.create(IParametreService::class.java);

    @Singleton
    @Provides
    fun provideKullaniciApi(retrofit: Retrofit):KullaniciService = retrofit.create(KullaniciService::class.java);

    @Singleton
    @Provides
    fun provideKitapApi(retrofit: Retrofit):IKitapService = retrofit.create(IKitapService::class.java);

    @Singleton
    @Provides
    fun provideKullaniciDao(database: KutuphanemDatabase) = database.getKullaniciDao();

    /*@FragmentScoped
    @Provides
    fun provideProfilIslemViewModel(application: Application,
                                    kullaniciService: KullaniciService,
                                    kullaniciDao: KullaniciDao,
                                    customSharedPreferences: CustomSharedPreferences):ViewModelProvider.Factory{
        return ProfilIslemViewModelFactory(application,kullaniciService,kullaniciDao,customSharedPreferences);
    }*/
}