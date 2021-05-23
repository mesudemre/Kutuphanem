package com.mesutemre.kutuphanem.model

import androidx.annotation.Nullable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DurumModel(
        @SerializedName("value")
        @Expose
        @Nullable
        var value:String,

        @SerializedName("label")
        @Expose
        var label:String
) {
}