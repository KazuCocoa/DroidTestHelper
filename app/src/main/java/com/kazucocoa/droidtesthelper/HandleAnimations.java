package com.kazucocoa.droidtesthelper;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class HandleAnimations {

    private static String TAG = HandleAnimations.class.getSimpleName();

    private final float DISABLE = 0.0f;

    private final float ENABLE = 1.0f;

    private static String animationExtra = "ANIMATION";

    private Method setAnimationScalesMethod;

    private Method getAnimationScalesMethod;

    private Object windowManagerObject;

    public static boolean hasExtraRegardingAnimation(Intent intent) {
        return intent.hasExtra(animationExtra);
    }

    public static void enableAnimationsWithIntent(Intent intent) {
        boolean enableAnimation = intent.getBooleanExtra(animationExtra, false);
        HandleAnimations handleAnimations = new HandleAnimations();
        handleAnimations.AnimationHandler();

        if (enableAnimation) {
            handleAnimations.enableAnimations();
        } else {
            handleAnimations.disableAnimations();
        }
    }

    public void AnimationHandler() {
        try {
            Class<?> windowManagerStubClazz = Class.forName("android.view.IWindowManager$Stub");
            Method asInterface = windowManagerStubClazz.getDeclaredMethod("asInterface", IBinder.class);

            Class<?> serviceManagerClazz = Class.forName("android.os.ServiceManager");
            Method getService = serviceManagerClazz.getDeclaredMethod("getService", String.class);

            Class<?> windowManagerClazz = Class.forName("android.view.IWindowManager");

            setAnimationScalesMethod = windowManagerClazz.getDeclaredMethod("setAnimationScales", float[].class);
            getAnimationScalesMethod = windowManagerClazz.getDeclaredMethod("getAnimationScales");

            IBinder windowManagerBinder = (IBinder) getService.invoke(null, "window");
            windowManagerObject = asInterface.invoke(null, windowManagerBinder);
        } catch (Exception e) {
            throw new RuntimeException("Failed to access animation methods", e);
        }
    }

    public void disableAnimations() {
        try {
            setAnimationScaleWith(DISABLE);
        } catch (Exception e) {
            Log.e(TAG, "Failed to disable animations", e);
        }
    }

    public void enableAnimations() {
        try {
            setAnimationScaleWith(ENABLE);
        } catch (Exception e) {
            Log.e(TAG, "Failed to enable animations", e);
        }
    }

    private void setAnimationScaleWith(float scaleFactor) throws InvocationTargetException, IllegalAccessException {
        float[] scaleFactors = (float[]) getAnimationScalesMethod.invoke(windowManagerObject);
        Arrays.fill(scaleFactors, scaleFactor);
        setAnimationScalesMethod.invoke(windowManagerObject, new Object[]{scaleFactors});
    }
}
