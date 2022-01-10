package com.mesutemre.kutuphanem.kitap.yorum.listener

import android.view.View

interface KitapYorumHeaderClickListener {

    fun kitapPuanla(view: View,puan:Float,isUser:Boolean);
}