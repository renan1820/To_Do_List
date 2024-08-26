package com.renan.portifolio.to_dolist.data.daos

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.renan.portifolio.to_dolist.BuildConfig
import com.renan.portifolio.to_dolist.util.EMPTY_STRING
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class PreferencesDataSource(
    val sharedPreferences: SharedPreferences,
) : KoinComponent, IPreferencesDataSource {

    companion object {
        const val PROJECT_PREF_FILE_NAME = "liAT0Zhw3J" + BuildConfig.FLAVOR
    }

    val gson = Gson()
    private var isEmployeeInitialized = false


    override fun getPriveteSharedPreferences(): SharedPreferences = sharedPreferences

    @Suppress("UNCHECKED_CAST")
    open fun <T : Any> getPref(key: String, defaultValue: T): T {
        return when (defaultValue::class.java.name) {
            java.lang.String::class.java.name -> sharedPreferences.getString(
                key,
                defaultValue as String
            )

            java.lang.Integer::class.java.name -> sharedPreferences.getInt(key, defaultValue as Int)
            java.lang.Boolean::class.java.name -> sharedPreferences.getBoolean(
                key,
                defaultValue as Boolean
            )

            java.lang.Float::class.java.name -> sharedPreferences.getFloat(
                key,
                defaultValue as Float
            )

            java.lang.Long::class.java.name -> sharedPreferences.getLong(key, defaultValue as Long)
            else -> throw Exception("Unhandled returning type")
        } as T
    }

    open fun getPrefEmployee() =
        sharedPreferences.getString("CURRENT_EMPLOYEE", EMPTY_STRING)

    open fun cleanPrefEmployee() {
        sharedPreferences.edit().putString("CURRENT_EMPLOYEE", EMPTY_STRING).apply()
    }

    open fun setPref(key: String, value: Any) {
        when (value::class.java.name) {
            String::class.java.name -> sharedPreferences.edit().putString(key, value as String)
                .apply()

            java.lang.Integer::class.java.name -> sharedPreferences.edit().putInt(key, value as Int)
                .apply()

            java.lang.Boolean::class.java.name -> sharedPreferences.edit()
                .putBoolean(key, (value as Boolean)).apply()

            java.lang.Float::class.java.name -> sharedPreferences.edit()
                .putFloat(key, value as Float).apply()

            java.lang.Long::class.java.name -> sharedPreferences.edit().putLong(key, value as Long)
                .apply()

            else -> throw Exception("Error trying to fetch ${value::class} type -> Unhandled returning type")
        }
    }

}