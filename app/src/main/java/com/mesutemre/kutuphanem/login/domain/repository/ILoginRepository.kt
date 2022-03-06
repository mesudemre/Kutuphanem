package com.mesutemre.kutuphanem.login.domain.repository

import com.mesutemre.kutuphanem.login.data.remote.dto.AccountCredentialsDto
import retrofit2.Response

interface ILoginRepository {

    suspend fun login(credentialsDto: AccountCredentialsDto):Response<String>
}