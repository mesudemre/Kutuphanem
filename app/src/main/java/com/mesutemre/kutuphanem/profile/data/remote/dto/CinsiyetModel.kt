package com.mesutemre.kutuphanem.profile.data.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CinsiyetModel(

    @SerializedName("value")
    @Expose
    var value:String?,

    @SerializedName("label")
    @Expose
    var label:String?
) {
    override fun toString(): String {
        return this.value!!;
    }

}