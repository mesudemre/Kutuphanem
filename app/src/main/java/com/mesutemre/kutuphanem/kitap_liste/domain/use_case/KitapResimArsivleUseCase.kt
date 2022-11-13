package com.mesutemre.kutuphanem.kitap_liste.domain.use_case

import android.content.Context
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.kitap_liste.data.repository.KitapListeRepository
import com.mesutemre.kutuphanem.util.convertRersourceEventType
import com.mesutemre.kutuphanem.util.saveArsivFile
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_base.use_case.IServiceCall
import com.mesutemre.kutuphanem_base.use_case.ServiceCallUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.io.File
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 12.11.2022
 */
class KitapResimArsivleUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val kitapListeRepository: KitapListeRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : IServiceCall by ServiceCallUseCase() {

    operator fun invoke(
        imageUrl: String,
        kitapId: Int,
        kitapAd: String
    ): Flow<BaseResourceEvent<File>> {
        return serviceCall {
            kitapListeRepository.downloadKitapResim(imageUrl)
        }.map {
            it.convertRersourceEventType {
                context.saveArsivFile(
                    kitapId,
                    kitapAd,
                    true,
                    it.data?.bytes()!!
                )!!
            }
        }.flowOn(ioDispatcher)
    }
}