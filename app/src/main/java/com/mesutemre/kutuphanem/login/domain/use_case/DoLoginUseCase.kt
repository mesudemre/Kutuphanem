package com.mesutemre.kutuphanem.login.domain.use_case.do_login

import com.mesutemre.kutuphanem.base.BaseResourceEvent
import com.mesutemre.kutuphanem.base.BaseUseCase
import com.mesutemre.kutuphanem.login.data.remote.dto.AccountCredentialsDto
import com.mesutemre.kutuphanem.login.domain.repository.ILoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 27.02.2022
 */
class DoLoginUseCase @Inject constructor(
    private val kullaniciRepository:ILoginRepository
):BaseUseCase(){

    operator fun invoke(accountCredentialsDto: AccountCredentialsDto): Flow<BaseResourceEvent<String?>> {
        return serviceCall {
            kullaniciRepository.login(accountCredentialsDto)
        }
    }
}