package com.mesutemre.kutuphanem.converters

import androidx.databinding.InverseMethod
import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class Converters {

    companion object{

        @TypeConverter
        @JvmStatic
        public fun formatDate2String(value:Date):String?{
            val sdf:SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy");
            if(value != null){
                return sdf.format(value);
            }
            return null;
        }

        @TypeConverter
        @JvmStatic
        public fun formatString2Date(value:String):Date?{
            val sdf:SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy");
            if(value != null){
                return sdf.parse(value);
            }
            return null;
        }
    }



}