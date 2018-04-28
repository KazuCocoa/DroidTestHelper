package com.kazucocoa.droidtesthelperlib

import android.content.Intent
import android.os.IBinder
import android.util.Log

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.Arrays

class HandleAnimations {

    private val DISABLE = 0.0f

    private val ENABLE = 1.0f

    private lateinit var setAnimationScalesMethod: Method

    private lateinit var getAnimationScalesMethod: Method

    private lateinit var windowManagerObject: Any

    private fun animationHandler() {
        try {
            val asInterface = Class.forName("android.view.IWindowManager\$Stub").getDeclaredMethod("asInterface", IBinder::class.java)

            val getService = Class.forName("android.os.ServiceManager").getDeclaredMethod("getService", String::class.java)

            val windowManagerClazz = Class.forName("android.view.IWindowManager")

            setAnimationScalesMethod = Class.forName("android.view.IWindowManager").getDeclaredMethod("setAnimationScales", FloatArray::class.java)
            getAnimationScalesMethod = windowManagerClazz.getDeclaredMethod("getAnimationScales")
            windowManagerObject = asInterface.invoke(null, getService.invoke(null, "window") as IBinder)
        } catch (e: Exception) {
            throw RuntimeException("Failed to access animation methods", e)
        }
    }

    private fun disableAnimations() {
        try {
            setAnimationScaleWith(DISABLE)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to disable animations", e)
        }

    }

    fun enableAnimations() {
        try {
            setAnimationScaleWith(ENABLE)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to enable animations", e)
        }

    }

    @Throws(InvocationTargetException::class, IllegalAccessException::class)
    private fun setAnimationScaleWith(scaleFactor: Float) {
        (getAnimationScalesMethod.invoke(windowManagerObject) as FloatArray).let {
            Arrays.fill(it, scaleFactor)
            setAnimationScalesMethod.invoke(windowManagerObject, it)
        }
    }

    companion object {

        private val TAG = HandleAnimations::class.java.simpleName

        private const val animationExtra = "ANIMATION"

        fun hasExtraRegardingAnimation(intent: Intent): Boolean {
            return intent.hasExtra(animationExtra)
        }

        fun enableAnimationsWithIntent(intent: Intent) {
            val enableAnimation = intent.getBooleanExtra(animationExtra, false)
            val handleAnimations = HandleAnimations()
            handleAnimations.animationHandler()

            if (enableAnimation) {
                handleAnimations.enableAnimations()
            } else {
                handleAnimations.disableAnimations()
            }
        }
    }
}
