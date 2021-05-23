package com.mesutemre.kutuphanem.listener

import android.view.View
import com.mesutemre.kutuphanem.model.KitapturModel

interface KitapturDeleteClickListener {

    fun onKitapturDeleteClicked(view: View, selectedKitaptur: KitapturModel);
}