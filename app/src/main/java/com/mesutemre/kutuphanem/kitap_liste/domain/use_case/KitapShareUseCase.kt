package com.mesutemre.kutuphanem.kitap_liste.domain.use_case

import android.content.Context
import androidx.core.content.FileProvider
import com.mesutemre.kutuphanem.BuildConfig
import com.mesutemre.kutuphanem.R
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.kitap_liste.data.repository.KitapListeRepository
import com.mesutemre.kutuphanem.kitap_liste.domain.model.KitapShareModel
import com.mesutemre.kutuphanem.util.saveShareKitapFile
import com.mesutemre.kutuphanem_base.model.BaseResourceEvent
import com.mesutemre.kutuphanem_base.use_case.IServiceCall
import com.mesutemre.kutuphanem_base.use_case.ServiceCallUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 17.11.2022
 */
class KitapShareUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val kitapListeRepository: KitapListeRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : IServiceCall by ServiceCallUseCase() {

    suspend operator fun invoke(
        kitapId: Int,
        yazarAd: String,
        kitapAd: String,
        kitapResim: String
    ) = channelFlow<BaseResourceEvent<KitapShareModel>> {
        send(BaseResourceEvent.Loading())
        val kitapResimDownload = serviceCall {
            kitapListeRepository.downloadKitapResim(kitapResim)
        }.flowOn(ioDispatcher)
        kitapResimDownload.collectLatest { response ->
            when (response) {
                is BaseResourceEvent.Success -> {
                    response.data?.let {
                        val shareFile =
                            context.saveShareKitapFile(kitapId, response.data?.bytes()!!)
                        send(
                            BaseResourceEvent.Success(
                                data = KitapShareModel(
                                    content = context.getString(
                                        R.string.kitap_liste_share_content,
                                        yazarAd,
                                        kitapAd
                                    ),
                                    imageUri = FileProvider.getUriForFile(
                                        context,
                                        BuildConfig.APPLICATION_ID + ".provider",
                                        shareFile!!
                                    )
                                )
                            )
                        )
                    } ?: run {
                        send(BaseResourceEvent.Error(message = response.message))
                    }
                }
                is BaseResourceEvent.Error -> {
                    send(BaseResourceEvent.Error(message = response.message))
                }
                else -> {}
            }
        }
    }
}