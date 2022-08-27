package com.mesutemre.kutuphanem.login.data.remote

import com.mesutemre.kutuphanem.login.data.remote.dto.AccountCredentialsDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("login")
    suspend fun login(@Body user: AccountCredentialsDto): Response<String>
}