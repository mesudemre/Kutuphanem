package com.mesutemre.kutuphanem.login.data.repository

import com.mesutemre.kutuphanem.login.data.remote.LoginService
import com.mesutemre.kutuphanem.login.data.remote.dto.AccountCredentialsDto
import com.mesutemre.kutuphanem.login.domain.repository.ILoginRepository
import retrofit2.Response
import javax.inject.Inject

/**
 * @Author: mesutemre.celenk
 * @Date: 27.02.2022
 */
class LoginRepository @Inject constructor(
    private val kullaniciService: LoginService
):ILoginRepository {

    override suspend fun login(credentialsDto: AccountCredentialsDto): Response<String> {
        return kullaniciService.login(credentialsDto)
    }
}