package com.mesutemre.kutuphanem.parametre.kitaptur.listener

import android.view.View
import com.mesutemre.kutuphanem.parametre.kitaptur.model.KitapturModel

interface KitapturDeleteClickListener {

    fun onKitapturDeleteClicked(view: View, selectedKitaptur: KitapturModel);
}