package com.mesutemre.kutuphanem.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

suspend inline fun <reified T> DataStore<Preferences>.saveData(key: String, value: T) {
    this.edit { pref ->
        when (T::class) {
            String::class -> {
                pref[stringPreferencesKey(key)] = value as String
            }
            Boolean::class -> {
                pref[booleanPreferencesKey(key)] = value as Boolean
            }
            Int::class -> {
                pref[intPreferencesKey(key)] = value as Int
            }
            Float::class -> {
                pref[floatPreferencesKey(key)] = value as Float
            }
        }
    }
}

suspend fun DataStore<Preferences>.readString(
    key: String,
    defaultValue: String = ""
): Flow<String> {
    return this.data.catch {
        emit(emptyPreferences())
    }.map { pref ->
        pref[stringPreferencesKey(key)] ?: defaultValue
    }
}

suspend fun DataStore<Preferences>.readBoolean(
    key: String,
    defaultValue: Boolean = false
): Flow<Boolean> {
    return this.data.catch {
        emit(emptyPreferences())
    }.map { pref ->
        pref[booleanPreferencesKey(key)] ?: defaultValue
    }
}

fun DataStore<Preferences>.readInt(key: String, defaultValue: Int = 0): Flow<Int> {
    return this.data.catch {
        emit(emptyPreferences())
    }.map { pref ->
        pref[intPreferencesKey(key)] ?: defaultValue
    }
}

fun DataStore<Preferences>.readFloat(key: String, defaultValue: Float = 0f): Flow<Float> {
    return this.data.catch {
        emit(emptyPreferences())
    }.map { pref ->
        pref[floatPreferencesKey(key)] ?: defaultValue
    }
}

suspend inline fun <reified T> DataStore<Preferences>.removeFromDataStore(key: String) {
    this.edit { pref ->
        when (T::class) {
            String::class -> {
                pref.remove(stringPreferencesKey(key))
            }
            Boolean::class -> {
                pref.remove(booleanPreferencesKey(key))
            }
            Int::class -> {
                pref.remove(intPreferencesKey(key))
            }
            Float::class -> {
                pref.remove(floatPreferencesKey(key))
            }
        }
    }
}
