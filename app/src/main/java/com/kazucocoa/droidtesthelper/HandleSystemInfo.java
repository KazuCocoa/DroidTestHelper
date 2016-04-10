package com.kazucocoa.droidtesthelper;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class HandleSystemInfo {

    private WifiInfo wifiInfo;

    private Display display;

    public TelephonyManager telephonyManager;

    public HandleSystemInfo(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiInfo = wifiManager.getConnectionInfo();

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();

        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    public String getModelName() {
        return Build.MODEL;
    }

    public String getSDKVersion() {
        return String.valueOf(Build.VERSION.SDK_INT);
    }

    public String getBuildId() {
        return Build.DISPLAY;
    }

    public String getVersionRelease() {
        return Build.VERSION.RELEASE;
    }


    public String getMacAddress() {
        String macAddress = wifiInfo.getMacAddress();
        return macAddress == null ? "" : macAddress;
    }

    // format is : (width)x(height)
    public String getDisplayResolution() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        String widthPixel = String.valueOf(displayMetrics.widthPixels);
        String heightPixel = String.valueOf(displayMetrics.heightPixels);
        return widthPixel + "x" + heightPixel;
    }

    public String getDPI() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        String xdpi = String.valueOf(displayMetrics.xdpi);
        String ydpi = String.valueOf(displayMetrics.ydpi);
        return xdpi + "x" + ydpi;
    }


    public String getSIMSerialNumber() {
        String simSerial = telephonyManager.getSimSerialNumber();
        return simSerial == null ? "" : simSerial;
    }

    public String getSIMPhoneNumber() {
        String simPhoneNumber = telephonyManager.getLine1Number();
        return simPhoneNumber == null ? "" : simPhoneNumber;
    }

    public String getSIMServiceProvider() {
        String simOperatorName = telephonyManager.getSimOperatorName();
        return simOperatorName == null ? "" : simOperatorName;
    }
}
