package com.mesutemre.kutuphanem.util

import com.mesutemre.kutuphanem.service.IParametreService
import com.mesutemre.kutuphanem.service.KullaniciService

class WebApiUtil {

    companion object{

        fun getKullaniciService():KullaniciService{
            return RetrofitClientUtil.getClient().create(KullaniciService::class.java);
        }

        fun getParametreService(token:String):IParametreService{
            return RetrofitClientUtil.getClient(token).create(IParametreService::class.java);
        }
    }
}