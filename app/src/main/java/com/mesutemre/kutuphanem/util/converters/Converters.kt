package com.mesutemre.kutuphanem.util.converters

import androidx.room.TypeConverter
import com.mesutemre.kutuphanem.auth.profil.model.CinsiyetModel
import com.mesutemre.kutuphanem.parametre.kitaptur.model.KitapturModel
import com.mesutemre.kutuphanem.parametre.yayinevi.model.YayineviModel
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
        fun cinsiyetToString(value: CinsiyetModel):String?{
            return value.toString();
        }

        @TypeConverter
        @JvmStatic
        fun stringToCinsiyet(value: String): CinsiyetModel?{
            var cinsiyetModel: CinsiyetModel = CinsiyetModel(null,null);
            if(value == "ERKEK"){
                cinsiyetModel = CinsiyetModel("ERKEK","Bay");
            }else{
                cinsiyetModel = CinsiyetModel("KADIN","Bayan");
            }
            return cinsiyetModel;
        }

        @TypeConverter
        @JvmStatic
        fun kitapTurToString(value: KitapturModel):String?{
            return value.toString();
        }

        @TypeConverter
        @JvmStatic
        fun stringToKitapTur(value: String): KitapturModel?{
            return KitapturModel(0,value);
        }

        @TypeConverter
        @JvmStatic
        fun yayinEviToString(value: YayineviModel):String?{
            return value.toString();
        }

        @TypeConverter
        @JvmStatic
        fun stringToYayinEvi(value: String): YayineviModel?{
            return YayineviModel(0,value);
        }
    }
}