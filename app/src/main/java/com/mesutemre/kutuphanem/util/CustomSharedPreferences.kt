package com.mesutemre.kutuphanem.util

import android.content.Context
import android.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomSharedPreferences @Inject constructor(@ApplicationContext context:Context) {

    val prefs = PreferenceManager.getDefaultSharedPreferences(context);

    fun putStringToSharedPreferences(key:String,value:String){
        prefs.edit().putString(key, value).apply();
    }

    fun putBooleanToSharedPreferences(key:String,value:Boolean){
        prefs.edit().putBoolean(key,value).apply();
    }

    fun getStringFromSharedPreferences(key:String):String{
        return prefs.getString(key, "")!!;
    }

    fun getBooleanFromSharedPreferences(key:String):Boolean{
        return prefs.getBoolean(key,false);
    }

    fun removeFromSharedPreferences(key:String){
        prefs.edit().remove(key).apply();
    }

    fun clearAllSharedPreferencesData(){
        prefs.edit().clear().apply();
    }
}