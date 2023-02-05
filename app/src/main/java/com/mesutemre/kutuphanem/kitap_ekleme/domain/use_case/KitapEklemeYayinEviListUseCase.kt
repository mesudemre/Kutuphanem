package com.mesutemre.kutuphanem.kitap_ekleme.domain.use_case

import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.kitap_ekleme.domain.model.KitapEklemeYayinEviItem
import com.mesutemre.kutuphanem.parameter.yayinevi.data.dao.entity.toKitapEklemeYayinEviItem
import com.mesutemre.kutuphanem.parameter.yayinevi.data.remote.dto.YayinEviDto
import com.mesutemre.kutuphanem.parameter.yayinevi.data.remote.dto.toKitapEklemeYayinEviItem
import com.mesutemre.kutuphanem.parameter.yayinevi.data.remote.dto.toYayinEviItem
import com.mesutemre.kutuphanem.parameter.yayinevi.domain.repository.YayinEviRepository
import com.mesutemre.kutuphanem.parameter.yayinevi.domain.use_case.StoreYayinEviParametre
import com.mesutemre.kutuphanem.util.PARAM_YAYINEVI_DB_KEY
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

class KitapEklemeYayinEviListUseCase @Inject constructor(
    private val yayinEviRepository: YayinEviRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val storeYayinEviParametre: StoreYayinEviParametre
) : IServiceCall by ServiceCallUseCase(), IDbCall by DbCallUseCase() {

    suspend operator fun invoke(): Flow<BaseResourceEvent<List<KitapEklemeYayinEviItem>>> {
        val isDbKayit = yayinEviRepository.checkYayinEviDbKayit(PARAM_YAYINEVI_DB_KEY)
        if (isDbKayit.not()) {
            var yayinEviDtoList: List<YayinEviDto>? = null
            val serviceListEvent = serviceCall {
                yayinEviRepository.getYayinEviLisetByAPI()
            }.map {
                yayinEviDtoList = it.data
                it.convertRersourceEventType {
                    it.data!!.map { k ->
                        k.toKitapEklemeYayinEviItem()
                    }
                }
            }.flowOn(ioDispatcher)
            serviceListEvent.collectLatest {
                if (it is BaseResourceEvent.Success) {
                    storeYayinEviParametre(yayinEviDtoList!!.map {
                        it.toYayinEviItem()
                    })
                }
            }
            return serviceListEvent
        } else {
            return dbCall {
                yayinEviRepository.getYayinEviListeByDAO()
            }.map {
                it.convertRersourceEventType {
                    it.data!!.map { k ->
                        k.toKitapEklemeYayinEviItem()
                    }
                }
            }.flowOn(ioDispatcher)
        }
    }
}