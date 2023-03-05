package com.mesutemre.kutuphanem.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.mesutemre.kutuphanem.dashboard.data.dao.entity.IDashBoardDao
import com.mesutemre.kutuphanem.dashboard.data.remote.IDashBoardApi
import com.mesutemre.kutuphanem.dashboard.data.repository.DashBoardRepository
import com.mesutemre.kutuphanem.dashboard.domain.repository.DashBoardRepositoryImpl
import com.mesutemre.kutuphanem.dashboard_search.data.dao.IDashBoardSearchHistoryDao
import com.mesutemre.kutuphanem.dashboard_search.data.repository.SearchDashboardRepository
import com.mesutemre.kutuphanem.dashboard_search.domain.repository.SearchDashBoardRepositoryImpl
import com.mesutemre.kutuphanem.kitap_detay.data.dao.IKitapDetayDao
import com.mesutemre.kutuphanem.kitap_detay.data.remote.IKitapYorumApi
import com.mesutemre.kutuphanem.kitap_detay.data.repository.KitapDetayRepository
import com.mesutemre.kutuphanem.kitap_detay.domain.repository.KitapDetayRepositoryImpl
import com.mesutemre.kutuphanem.kitap_ekleme.data.remote.IKitapEkleApi
import com.mesutemre.kutuphanem.kitap_ekleme.data.repository.KitapEklemeRepository
import com.mesutemre.kutuphanem.kitap_ekleme.domain.repository.KitapEklemeRepositoryImpl
import com.mesutemre.kutuphanem.kitap_liste.data.dao.IKitapDao
import com.mesutemre.kutuphanem.kitap_liste.data.remote.IKitapApi
import com.mesutemre.kutuphanem.kitap_liste.data.repository.KitapListeRepository
import com.mesutemre.kutuphanem.kitap_liste.domain.repository.KitapListeRepositoryImpl
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
import com.mesutemre.kutuphanem.profile.data.dao.IKullaniciDao
import com.mesutemre.kutuphanem.profile.data.remote.IKullaniciApi
import com.mesutemre.kutuphanem.profile.data.repository.KullaniciRepository
import com.mesutemre.kutuphanem.profile.domain.repository.KullaniciRepositoryImpl
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
    fun provideParametreYayinEviRepository(
        api: IYayinEviApi,
        dao: IYayinEviDao,
        dataStore: DataStore<Preferences>
    ): YayinEviRepository {
        return YayinEviRepositoryImpl(api, dao, dataStore)
    }

    @Singleton
    @Provides
    fun provideParametreKitapTurRepository(
        api: IKitapTurApi,
        dao: IKitapTurDao,
        dataStore: DataStore<Preferences>
    ): KitapTurRepository {
        return KitapTurRepositoryImpl(api, dao, dataStore)
    }

    @Singleton
    @Provides
    fun provideKullaniciRepository(
        api: IKullaniciApi,
        dataStore: DataStore<Preferences>,
        dao: IKullaniciDao
    ): KullaniciRepository {
        return KullaniciRepositoryImpl(api, dataStore, dao)
    }

    @Singleton
    @Provides
    fun provideDashBoardRepository(
        api: IDashBoardApi,
        dao: IDashBoardDao,
        dataStore: DataStore<Preferences>
    ): DashBoardRepository {
        return DashBoardRepositoryImpl(api, dao, dataStore)
    }

    @Singleton
    @Provides
    fun provideSearchDashBoardRepository(
        api: IKitapApi,
        dao: IDashBoardSearchHistoryDao
    ): SearchDashboardRepository {
        return SearchDashBoardRepositoryImpl(api, dao)
    }

    @Singleton
    @Provides
    fun provideKitapListeRepository(api: IKitapApi, dao: IKitapDao): KitapListeRepository {
        return KitapListeRepositoryImpl(api, dao)
    }

    @Singleton
    @Provides
    fun provideKitapDetayRepository(
        api: IKitapApi,
        dao: IKitapDetayDao,
        yorumApi: IKitapYorumApi,
        dataStore: DataStore<Preferences>
    ): KitapDetayRepository {
        return KitapDetayRepositoryImpl(api, dao, yorumApi, dataStore)
    }

    @Singleton
    @Provides
    fun provideKitapEklemeRepository(
        api: IKitapEkleApi
    ): KitapEklemeRepository {
        return KitapEklemeRepositoryImpl(api)
    }
}