package com.mesutemre.kutuphanem.kitap_ekleme.domain.use_case

import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.kitap_ekleme.domain.model.KitapEklemeKitapTurItem
import com.mesutemre.kutuphanem.parameter.kitaptur.data.dao.entity.toKitapEklemeKitapTurItem
import com.mesutemre.kutuphanem.parameter.kitaptur.data.remote.dto.KitapTurDto
import com.mesutemre.kutuphanem.parameter.kitaptur.data.remote.dto.toKitapEklemeKitapTurItem
import com.mesutemre.kutuphanem.parameter.kitaptur.data.repository.KitapTurRepository
import com.mesutemre.kutuphanem.parameter.kitaptur.domain.use_case.StoreKitapTurParametre
import com.mesutemre.kutuphanem.util.PARAM_KITAPTUR_DB_KEY
import com.mesutemre.kutuphanem.util.convertRersourceEventType
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_base.use_case.DbCallUseCase
import com.mesutemre.kutuphanem_base.use_case.IDbCall
import com.mesutemre.kutuphanem_base.use_case.IServiceCall
import com.mesutemre.kutuphanem_base.use_case.ServiceCallUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class KitapEklemeKitapTurListUseCase @Inject constructor(
    private val kitapTurRepository: KitapTurRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val storeKitapTurParametre: StoreKitapTurParametre
) : IServiceCall by ServiceCallUseCase(), IDbCall by DbCallUseCase() {

    suspend operator fun invoke(): Flow<BaseResourceEvent<List<KitapEklemeKitapTurItem>>> {
        val isDbKayit = kitapTurRepository.checkKitapTurDbKayit(PARAM_KITAPTUR_DB_KEY)
        var kitapTurDtoList: List<KitapTurDto>? = null
        if (isDbKayit.not()) {
            val serviceEvent = serviceCall {
                kitapTurRepository.getKitapTurListeByAPI()
            }.map {
                kitapTurDtoList = it.data
                it.convertRersourceEventType {
                    it.data!!.map { k ->
                        k.toKitapEklemeKitapTurItem()
                    }
                }
            }.flowOn(ioDispatcher)
            serviceEvent.collectLatest {
                if (it is BaseResourceEvent.Success) {
                    storeKitapTurParametre(kitapTurDtoList!!)
                }
            }
            return serviceEvent
        } else {
            return dbCall {
                kitapTurRepository.getKitapTurListeByDAO()
            }.map {
                it.convertRersourceEventType {
                    it.data!!.map { k ->
                        k.toKitapEklemeKitapTurItem()
                    }
                }
            }.flowOn(ioDispatcher)
        }
    }
}