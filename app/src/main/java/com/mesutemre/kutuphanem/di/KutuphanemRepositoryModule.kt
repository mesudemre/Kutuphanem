package com.mesutemre.kutuphanem.di

import com.mesutemre.kutuphanem.login.data.remote.LoginService
import com.mesutemre.kutuphanem.login.data.repository.LoginRepository
import com.mesutemre.kutuphanem.login.domain.repository.ILoginRepository
import com.mesutemre.kutuphanem.parameter.kitaptur.data.dao.IKitapTurDao
import com.mesutemre.kutuphanem.parameter.kitaptur.data.remote.dto.IKitapTurApi
import com.mesutemre.kutuphanem.parameter.kitaptur.data.repository.KitapTurRepository
import com.mesutemre.kutuphanem.parameter.kitaptur.domain.repository.KitapTurRepositoryImpl
import com.mesutemre.kutuphanem.parameter.yayinevi.data.dao.entity.IYayinEviDao
import com.mesutemre.kutuphanem.parameter.yayinevi.data.remote.dto.IYayinEviApi
import com.mesutemre.kutuphanem.parameter.yayinevi.data.repository.YayinEviRepositoryImpl
import com.mesutemre.kutuphanem.parameter.yayinevi.domain.repository.YayinEviRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Author: mesutemre.celenk
 * @Date: 27.02.2022
 */
@Module
@InstallIn(SingletonComponent::class)
class KutuphanemRepositoryModule {

    @Singleton
    @Provides
    fun provideLoginRepository(api: LoginService): ILoginRepository {
        return LoginRepository(api)
    }

    @Singleton
    @Provides
    fun provideParametreYayinEviRepository(api: IYayinEviApi,dao: IYayinEviDao): YayinEviRepository {
        return YayinEviRepositoryImpl(api,dao)
    }

    @Singleton
    @Provides
    fun provideParametreKitapTurRepository(api: IKitapTurApi, dao:IKitapTurDao): KitapTurRepository {
        return KitapTurRepositoryImpl(api,dao)
    }
}