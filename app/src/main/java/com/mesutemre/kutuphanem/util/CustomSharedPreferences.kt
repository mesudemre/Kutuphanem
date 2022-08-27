package com.mesutemre.kutuphanem.util

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomSharedPreferences @Inject constructor(@ApplicationContext context:Context) {

    val sharedPrefsFile = SHARED_PREF_FILE
    val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    val prefs:SharedPreferences = EncryptedSharedPreferences.create(sharedPrefsFile,masterKeyAlias,context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

    inline fun <reified T:Any> putToSharedPref(key:String, value:T) {
        when(T::class) {
            String::class->{
                putStringToSharedPreferences(key,value as String)
            }
            Boolean::class->{
                putBooleanToSharedPreferences(key,value as Boolean)
            }
        }
    }
    
    fun putStringToSharedPreferences(key:String,value:String){
        prefs.edit().putString(key, value).apply()
    }

    fun putBooleanToSharedPreferences(key:String,value:Boolean){
        prefs.edit().putBoolean(key,value).apply()
    }

    fun getStringFromSharedPreferences(key:String):String{
        return prefs.getString(key, "")!!
    }

    fun getBooleanFromSharedPreferences(key:String):Boolean{
        return prefs.getBoolean(key,false)
    }

    fun removeFromSharedPreferences(key:String){
        prefs.edit().remove(key).apply()
    }

    fun clearAllSharedPreferencesData(){
        prefs.edit().clear().apply()
    }
}