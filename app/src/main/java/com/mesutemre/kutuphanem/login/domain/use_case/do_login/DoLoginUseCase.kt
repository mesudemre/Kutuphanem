package com.mesutemre.kutuphanem.login.domain.use_case.do_login

import com.mesutemre.kutuphanem.login.data.remote.dto.AccountCredentialsDto
import com.mesutemre.kutuphanem.login.domain.repository.ILoginRepository
import com.mesutemre.kutuphanem.base.BaseResourceEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 27.02.2022
 */
class DoLoginUseCase @Inject constructor(
    private val kullaniciRepository:ILoginRepository
){

    operator fun invoke(accountCredentialsDto: AccountCredentialsDto):
            Flow<BaseResourceEvent<String>> = flow {
        emit(BaseResourceEvent.Loading())
        val loginResponse = kullaniciRepository.login(accountCredentialsDto)
        if (loginResponse.isSuccessful) {
            emit(BaseResourceEvent.Success(loginResponse.body() ?: ""))
        }else {
            emit(BaseResourceEvent.Error(loginResponse.errorBody().toString()))
        }
    }
}