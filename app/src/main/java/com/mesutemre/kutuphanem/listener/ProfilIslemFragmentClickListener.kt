package com.mesutemre.kutuphanem.listener

import android.view.View
import com.google.android.material.textfield.TextInputLayout
import java.util.*

interface ProfilIslemFragmentClickListener {

    fun openDatePicker(view:View,dogumTarihi:Date);
    fun openProfilResimDegistirme(view: View,profilImageView:View);
    fun kullaniciGuncelle(view: View);
    fun logoutFromApplication(view: View);
}