package com.mesutemre.kutuphanem.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class YorumListeModel(
    @SerializedName("yorumListe")
    @Expose
    val yorumListe:List<KitapYorumModel>
)