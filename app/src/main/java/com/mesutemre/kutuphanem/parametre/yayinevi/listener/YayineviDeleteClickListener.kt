package com.mesutemre.kutuphanem.parametre.yayinevi.listener

import android.view.View
import com.mesutemre.kutuphanem.parametre.yayinevi.model.YayineviModel

interface YayineviDeleteClickListener {

    fun onYayineviDeleteClicked(view:View,selectedYayinevi: YayineviModel);
}