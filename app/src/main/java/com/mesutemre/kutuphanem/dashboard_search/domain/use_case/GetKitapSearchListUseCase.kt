package com.mesutemre.kutuphanem.dashboard_search.domain.use_case

import com.mesutemre.kutuphanem.dashboard_search.data.repository.SearchDashboardRepository
import com.mesutemre.kutuphanem.dashboard_search.domain.model.KitapSearchItem
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.kitap_liste.data.remote.dto.KitapDto
import com.mesutemre.kutuphanem.kitap_liste.data.remote.dto.convertKitapDtoToKitapSearchItem
import com.mesutemre.kutuphanem.util.convertRersourceEventType
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_base.use_case.IServiceCall
import com.mesutemre.kutuphanem_base.use_case.ServiceCallUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 8.10.2022
 */
class GetKitapSearchListUseCase @Inject constructor(
    private val searchDashboardRepository: SearchDashboardRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): IServiceCall by ServiceCallUseCase() {

    operator suspend fun invoke(searchText:String): Flow<BaseResourceEvent<List<KitapSearchItem>>> {
        delay(1000)
        val kitapDto = KitapDto(
            kitapAd = searchText,
            yazarAd = searchText
        )
        return serviceCall {
            searchDashboardRepository.getKitapSearchList(kitapDto)
        }.map {
            it.convertRersourceEventType {
                it.data?.let {list->
                    list.map { k ->
                        k.convertKitapDtoToKitapSearchItem()
                    }
                } ?: run {
                    emptyList()
                }
            }
        }.flowOn(ioDispatcher)
    }
}