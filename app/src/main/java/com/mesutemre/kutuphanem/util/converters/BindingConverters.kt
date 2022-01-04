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

    /*@InverseMethod("toCinsiyetSelected")
    @JvmStatic fun selectedCinsiyet(value:CinsiyetModel?):Int{
        if(value?.value.equals("ERKEK")){
            return R.id.erkekCinsiyetRadioButtonId;
        }else{
            return R.id.kadinCinsiyetRadioButtonId;
        }
    }

    @JvmStatic fun toCinsiyetSelected(id:Int):CinsiyetModel?{
        if(id == R.id.erkekCinsiyetRadioButtonId){
            return CinsiyetModel("ERKEK","Bay");
        }else{
            return CinsiyetModel("KADIN","Bayan");
        }

    }*/

}