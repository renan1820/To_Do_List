package com.renan.portifolio.to_dolist.data

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.renan.portifolio.to_dolist.BuildConfig
import com.renan.portifolio.to_dolist.data.daos.IPreferencesDataSource
import com.renan.portifolio.to_dolist.data.daos.PreferencesDataSource
import java.security.KeyStore

open class SecurityPreferences(
    val preferences: IPreferencesDataSource,
    context: Context,
) : PreferencesDataSource(
    EncryptedSharedPreferences(context).build(PREF_SECURITY_FILE_NAME)
) {
    companion object {
        const val TAG = "SecurityPreferences"
        private const val PREF_SECURITY_FILE_NAME = "prefs" + BuildConfig.FLAVOR
    }

    init {
        if (isPreferencesNotSecurity() && !isPreferencesSecurity()) setup()
    }

    private fun setup() {
        preferences.getPriveteSharedPreferences().all.forEach { map ->
            map.value?.let {
                setPref(map.key, it)
            }
        }
        cleanPreferencesNotSecurity()
    }

    private fun isPreferencesNotSecurity() = preferences.getPriveteSharedPreferences().all.isNotEmpty()

    private fun isPreferencesSecurity() = getPriveteSharedPreferences().all.isNotEmpty()

    private fun cleanPreferencesNotSecurity() = preferences.getPriveteSharedPreferences().edit().run {
        clear()
        commit()
    }
}