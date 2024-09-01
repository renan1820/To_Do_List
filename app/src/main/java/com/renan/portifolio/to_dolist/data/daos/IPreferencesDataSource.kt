package com.renan.portifolio.to_dolist.data.daos

import android.content.SharedPreferences

interface IPreferencesDataSource {
    fun getPrivateSharedPreferences(): SharedPreferences
}