package com.kazucocoa.droidtesthelperlib

import android.content.res.Configuration
import android.os.Build
import android.util.Log

import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.Locale

class HandleLocale {

    fun setLocale(locale: Locale) {
        try {
            setLocaleWith(locale)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to set locale", e)
        }

    }

    @Throws(
        ClassNotFoundException::class,
        NoSuchMethodException::class,
        InvocationTargetException::class,
        IllegalAccessException::class,
        NoSuchFieldException::class
    )
    private fun setLocaleWith(locale: Locale) {
        var activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative")

        val amn = activityManagerNativeClass.getMethod("getDefault").run {
            isAccessible = true
            invoke(activityManagerNativeClass)
        }

        // Build.VERSION_CODES.O
        if (Build.VERSION.SDK_INT >= 26) {
            // getConfiguration moved from ActivityManagerNative to ActivityManagerProxy
            activityManagerNativeClass = Class.forName(amn.javaClass.name)
        }

        val config = activityManagerNativeClass.getMethod("getConfiguration").run {
            isAccessible = true
            invoke(amn) as Configuration
        }

        val configClass = config.javaClass
        configClass.getField("userSetLocale").apply {
            setBoolean(config, true)
        }

        config.locale = locale
        activityManagerNativeClass.getMethod("updateConfiguration", Configuration::class.java).run {
            isAccessible = true
            invoke(amn, config)
        }
    }

    companion object {

        private val TAG = HandleLocale::class.java.simpleName
    }
}
