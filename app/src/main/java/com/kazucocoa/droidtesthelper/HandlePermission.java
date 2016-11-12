package com.kazucocoa.droidtesthelper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.UserHandle;
import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HandlePermission {
    private static String TAG = HandlePermission.class.getSimpleName();

    // https://github.com/android/platform_frameworks_base/blob/android-7.1.0_r7/core/java/android/app/UiAutomation.java#L919
    @TargetApi(Build.VERSION_CODES.M)
    public boolean grantPermission(@NonNull Context context, @NonNull String packageName, @NonNull String permission) {
        PackageManager packageManager = context.getPackageManager();
        try {
            Method method = packageManager.getClass().getMethod("grantRuntimePermission", String.class, String.class, UserHandle.class);
            method.invoke(packageManager, packageName, permission, android.os.Process.myUserHandle());

        } catch (NoSuchMethodException e) {
            Log.e(TAG, "NoSuchMethodException in grantPermission", e);
            return false;
        } catch (InvocationTargetException e) {
            Log.e(TAG, "InvocationTargetException in grantPermission", e);
            return false;
        } catch (IllegalAccessException e) {
            Log.e(TAG, "IllegalAccessException in grantPermission", e);
            return false;
        }

        return true;
    }
}
