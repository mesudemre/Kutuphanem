package com.mesutemre.kutuphanem.auth.profil.ui

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

/**
 * @Author: mesutemre.celenk
 * @Date: 26.01.2022
 */
@Parcelize
class ProfilResimListener(val loadProfilResimLambda: (uri:Uri) -> Unit):Parcelable{

    fun onLoad(uri:Uri) = loadProfilResimLambda(uri)
}