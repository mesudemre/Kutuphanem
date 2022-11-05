package com.mesutemre.kutuphanem.kitap_liste.domain.use_case

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.mesutemre.kutuphanem.kitap_liste.data.remote.dto.convertKitapDtoToKitapListeItem
import com.mesutemre.kutuphanem.kitap_liste.domain.model.KitapListeItem
import com.mesutemre.kutuphanem_base.use_case.IServiceCall
import com.mesutemre.kutuphanem_base.use_case.ServiceCallUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 29.10.2022
 */
class GetKitapListeUseCase @Inject constructor(
    private val kitapListeDataSource: KitapListDataSource
) : IServiceCall by ServiceCallUseCase() {

    operator fun invoke(): Flow<PagingData<KitapListeItem>> {
        return Pager(
            PagingConfig(
                pageSize = 3
            )
        ) {
            kitapListeDataSource
        }.flow.map { it ->
            it.map { kitapDto ->
                kitapDto.convertKitapDtoToKitapListeItem()
            }
        }
    }
}