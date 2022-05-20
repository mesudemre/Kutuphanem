package com.mesutemre.kutuphanem.login.domain.use_case.do_login

import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.BaseUseCase
import com.mesutemre.kutuphanem.di.IoDispatcher
import com.mesutemre.kutuphanem.login.data.remote.dto.AccountCredentialsDto
import com.mesutemre.kutuphanem.login.domain.repository.ILoginRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 27.02.2022
 */
class DoLoginUseCase @Inject constructor(
    private val kullaniciRepository:ILoginRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
):BaseUseCase(){

    operator fun invoke(accountCredentialsDto: AccountCredentialsDto): Flow<BaseResourceEvent<String?>> {
        return serviceCall {
            kullaniciRepository.login(accountCredentialsDto)
        }.flowOn(ioDispatcher)
    }
}