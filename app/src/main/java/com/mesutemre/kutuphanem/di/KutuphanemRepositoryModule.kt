package com.mesutemre.kutuphanem.di

import com.mesutemre.kutuphanem.login.data.remote.LoginService
import com.mesutemre.kutuphanem.login.data.repository.LoginRepository
import com.mesutemre.kutuphanem.login.domain.repository.ILoginRepository
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
}