package com.mesutemre.kutuphanem.login.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AccountCredentialsDto(
    @SerializedName("username")
    var username:String?,

    @SerializedName("password")
    var password:String?
)
