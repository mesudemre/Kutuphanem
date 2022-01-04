package com.mesutemre.kutuphanem.kitap.yorum.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mesutemre.kutuphanem.kitap.yorum.model.KitapYorumModel

data class YorumListeModel(
    @SerializedName("yorumListe")
    @Expose
    val yorumListe:List<KitapYorumModel>
)