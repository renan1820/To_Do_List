package com.renan.portifolio.to_dolist.util

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.setCustomKeys
import java.util.*

@SuppressLint("LogNotTimber")
object CustomLogger {

    private var shouldLogToCrashlytics = false

    fun initialize(sendExceptionsToCrashlytics: Boolean) {
        shouldLogToCrashlytics = true
    }

    @JvmStatic
    fun i(tag: String, msg: String?, exception: Exception? = null) {
        crashlytics(exception)
        Log.i(tag, handleMsg(msg))
        stacktrace(exception)
    }

    @JvmStatic
    fun d(tag: String, msg: String?, exception: Exception? = null) {
        crashlytics(exception)
        Log.d(tag, handleMsg(msg))
        stacktrace(exception)
    }

    @JvmStatic
    fun e(tag: String, msg: String?, exception: Exception? = null) {
        crashlytics(exception)
        Log.e(tag, handleMsg(msg))
        stacktrace(exception)
    }

    @JvmStatic
    fun wtf(tag: String, msg: String?, exception: Exception? = null) {
        crashlytics(exception)
        Log.wtf(tag, handleMsg(msg))
        stacktrace(exception)
    }

    @JvmStatic
    fun w(tag: String, msg: String?) {
        Log.w(tag, handleMsg(msg))
    }

    private fun handleMsg(msg: String?): String = msg ?: "ZigLogger[NO MESSAGE]"

    private fun stacktrace(exception: Exception?) = exception?.printStackTrace()

    private fun crashlytics(exception: Exception?) {
        try {
            setupTimeRelatedCustomKeysToFirebase()
        } catch (_: Exception) {}
        if (shouldLogToCrashlytics && exception != null) {
            FirebaseCrashlytics.getInstance().recordException(exception)
        }
    }

    fun setEmployeeIdAndEventIdAsCustomKeys(employeeId: String, eventId: String) {
        FirebaseCrashlytics.getInstance().setCustomKeys {
            key("employeeId", employeeId)
            key("eventId", eventId)
        }
    }

    private fun setupTimeRelatedCustomKeysToFirebase() {
        FirebaseCrashlytics.getInstance().setCustomKeys {
            key("time_in_millis_from_system", Calendar.getInstance().timeInMillis)
        }
    }
}