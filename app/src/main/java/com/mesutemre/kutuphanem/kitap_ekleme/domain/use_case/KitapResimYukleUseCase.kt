package com.mesutemre.kutuphanem.kitap_ekleme.domain.use_case

import android.graphics.Bitmap
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.kitap_ekleme.data.repository.KitapEklemeRepository
import com.mesutemre.kutuphanem.model.ResponseStatusModel
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_base.use_case.IServiceCall
import com.mesutemre.kutuphanem_base.use_case.ServiceCallUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import javax.inject.Inject

class KitapResimYukleUseCase @Inject constructor(
    private val kitapEklemeRepository: KitapEklemeRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : IServiceCall by ServiceCallUseCase() {

    operator fun invoke(
        selectedFile: File,
        kitapId: String
    ): Flow<BaseResourceEvent<ResponseStatusModel?>> {
        return serviceCall {
            kitapEklemeRepository.kitapResimYukle(
                kitapResim = selectedFile,
                kitapId = kitapId
            )
        }.flowOn(ioDispatcher)
    }
}