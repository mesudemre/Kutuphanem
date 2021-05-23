package com.mesutemre.kutuphanem.listener

import android.view.View
import com.mesutemre.kutuphanem.model.YayineviModel

interface YayineviDeleteClickListener {

    fun onYayineviDeleteClicked(view:View,selectedYayinevi:YayineviModel);
}