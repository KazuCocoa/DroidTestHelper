package com.kazucocoa.droidtesthelperlib

import android.content.Intent
import android.os.Build
import android.support.test.InstrumentationRegistry
import android.util.Log

class HandleClearData {
    companion object {
        private val TAG = HandleClearData::class.java.simpleName

        private const val clearDataExtra = "CLEAR_DATA"

        fun hasExtraRegardingHandleClearData(intent: Intent): Boolean {
            return intent.hasExtra(clearDataExtra)
        }

        fun clearData(intent: Intent) {
            val targetePackage = intent.getStringExtra(clearDataExtra)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand("pm clear $targetePackage")
            } else {
                Log.e(HandleClearData.TAG, "Failed to clear data because of API Level 21-")
            }

        }
    }
}
