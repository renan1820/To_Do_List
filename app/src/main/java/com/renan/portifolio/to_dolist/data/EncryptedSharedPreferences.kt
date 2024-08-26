package com.renan.portifolio.to_dolist.data

import android.content.Context
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE
import androidx.security.crypto.MasterKey.DEFAULT_MASTER_KEY_ALIAS
import com.renan.portifolio.to_dolist.util.tryCatchError
import com.renan.portifolio.to_dolist.util.tryCatchErrorReturn
import java.io.File
import java.security.KeyStore


class EncryptedSharedPreferences(
    private val context: Context
) {
    companion object {
        private const val KEYSTORE_PROVIDER = "AndroidKeyStore"
        private const val TAG = "EncryptedSharedPreferences"
    }

    private val masterKeyAlias =
        MasterKey.Builder(
            context,
            DEFAULT_MASTER_KEY_ALIAS
        ).setKeyGenParameterSpec(key())
            .build()

    fun build(prefName: String) = tryCatchErrorReturn(tag = TAG, runTry = {
        createSharedPreferences(prefName)
    }, runCatch = {
        deleteSharedPreferences(prefName)
        deleteMasterKey()
        createSharedPreferences(prefName)
    })

    private fun key(): KeyGenParameterSpec = KeyGenParameterSpec.Builder(
        DEFAULT_MASTER_KEY_ALIAS,
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
    ).setBlockModes(KeyProperties.BLOCK_MODE_GCM)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        .setKeySize(DEFAULT_AES_GCM_MASTER_KEY_SIZE)
        .build()

    private fun createSharedPreferences(prefName: String) = EncryptedSharedPreferences.create(
        context,
        prefName,
        masterKeyAlias,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private fun clearSharedPreference(prefName: String) {
        tryCatchError(TAG) {
            context.getSharedPreferences(prefName, Context.MODE_PRIVATE).edit().clear().apply()
        }
    }

    private fun deleteSharedPreferences(prefName: String) {
        tryCatchError(TAG) {
            clearSharedPreference(prefName)
            deleteFile("${context.filesDir.parent}/shared_prefs/$prefName.xml")
        }
    }

    private fun deleteMasterKey() {
        tryCatchError(TAG) {
            val keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER)
            keyStore.load(null)
            keyStore.deleteEntry(DEFAULT_MASTER_KEY_ALIAS)
        }
    }

    private fun deleteFile(filePath: String): Boolean = tryCatchErrorReturn(tag = TAG, runTry = {
        val file = File(filePath)
        if (file.exists()) {
            file.delete()
        } else {
            false
        }
    }, runCatch = {
        false
    })
}