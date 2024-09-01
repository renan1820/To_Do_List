package com.renan.portifolio.to_dolist.data

import android.content.Context
import com.renan.portifolio.to_dolist.BuildConfig
import com.renan.portifolio.to_dolist.data.daos.IPreferencesDataSource
import com.renan.portifolio.to_dolist.data.daos.PreferencesDataSource

open class SecurityPreferences(
    private val preferences: IPreferencesDataSource,
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
        preferences.getPrivateSharedPreferences().all.forEach { map ->
            map.value?.let {
                setPref(map.key, it)
            }
        }
        cleanPreferencesNotSecurity()
    }

    private fun isPreferencesNotSecurity() = preferences.getPrivateSharedPreferences().all.isNotEmpty()

    private fun isPreferencesSecurity() = getPrivateSharedPreferences().all.isNotEmpty()

    private fun cleanPreferencesNotSecurity() = preferences.getPrivateSharedPreferences().edit().run {
        clear()
        commit()
    }
}