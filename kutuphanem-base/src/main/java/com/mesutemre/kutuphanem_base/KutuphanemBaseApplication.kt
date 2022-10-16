package com.mesutemre.kutuphanem_base

import android.app.Application

/**
 * @Author: mesutemre.celenk
 * @Date: 16.10.2022
 */
open class KutuphanemBaseApplication: Application() {

    companion object {
        lateinit var instance:KutuphanemBaseApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}