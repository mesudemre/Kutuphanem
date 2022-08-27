package com.mesutemre.kutuphanem.util.converters

import androidx.databinding.InverseMethod
import java.text.SimpleDateFormat
import java.util.*

object BindingConverters {

    @InverseMethod("stringToDate")
    @JvmStatic fun dateToString(value: Date?):String?{
        val sdf: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy");
        if(value != null){
            return sdf.format(value);
        }
        return "";
    }

    @JvmStatic fun stringToDate(value:String?):Date?{
        val sdf:SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy");
        if(value != null){
            return sdf.parse(value);
        }
        return null;
    }

}