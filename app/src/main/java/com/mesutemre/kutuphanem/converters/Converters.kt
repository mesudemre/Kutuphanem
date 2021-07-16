package com.mesutemre.kutuphanem.converters

import androidx.databinding.InverseMethod
import androidx.room.TypeConverter
import com.mesutemre.kutuphanem.model.CinsiyetModel
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

        @TypeConverter
        @JvmStatic
        public fun cinsiyetToString(value:CinsiyetModel):String?{
            return value.toString();
        }

        @TypeConverter
        @JvmStatic
        public fun stringToCinsiyet(value: String):CinsiyetModel?{
            var cinsiyetModel:CinsiyetModel = CinsiyetModel(null,null);
            if(value == "ERKEK"){
                cinsiyetModel = CinsiyetModel("ERKEK","Bay");
            }else{
                cinsiyetModel = CinsiyetModel("KADIN","Bayan");
            }
            return cinsiyetModel;
        }
    }



}