package com.mesutemre.kutuphanem.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseStatusModel(
        @SerializedName("statusCode")
        @Expose
        var statusCode:String,

        @SerializedName("statusMessage")
        @Expose
        var statusMessage:String

)