package com.mesutemre.kutuphanem.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AccountCredentials(
    @SerializedName("username")
    @Expose
    var username:String?,

    @SerializedName("password")
    @Expose
    var password:String?

) {
}