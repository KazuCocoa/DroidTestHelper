package com.kazucocoa.droidtesthelperlib

import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.UserHandle
import android.util.Log

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

class HandlePermission {

    // https://github.com/android/platform_frameworks_base/blob/android-7.1.0_r7/core/java/android/app/UiAutomation.java#L919
    @TargetApi(Build.VERSION_CODES.M)
    fun grantPermission(context: Context, packageName: String, permission: String): Boolean {
        try {
            val packageManager = context.packageManager

            packageManager.javaClass.getMethod(
                "grantRuntimePermission",
                String::class.java,
                String::class.java,
                UserHandle::class.java
            ).run {
                invoke(
                    packageManager,
                    packageName,
                    permission,
                    android.os.Process.myUserHandle()
                )
            }
        } catch (e: NoSuchMethodException) {
            Log.e(TAG, "NoSuchMethodException in grantPermission", e)
            return false
        } catch (e: InvocationTargetException) {
            Log.e(TAG, "InvocationTargetException in grantPermission", e)
            return false
        } catch (e: IllegalAccessException) {
            Log.e(TAG, "IllegalAccessException in grantPermission", e)
            return false
        }

        return true
    }

    companion object {
        private val TAG = HandlePermission::class.java.simpleName
    }
}
