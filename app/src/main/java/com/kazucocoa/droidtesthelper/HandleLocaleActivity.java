package com.kazucocoa.droidtesthelper;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

public class HandleLocaleActivity extends AppCompatActivity {

    private static String TAG = HandleLocaleActivity.class.getSimpleName();

    private static String languageExt = "LANG";

    private static String countryExt = "COUNTRY";

    public static boolean hasExtraRegardingLocal(@NonNull Intent intent) {
        return intent.hasExtra(languageExt) && intent.hasExtra(countryExt);
    }

    public static Intent buildLaunchHandleLocalActivityIntent(@NonNull Context context, @NonNull Intent intent) {
        Intent returnIntent = new Intent(context, HandleLocaleActivity.class);
        returnIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        returnIntent.setAction(Intent.ACTION_VIEW);

        if(!hasExtraRegardingLocal(intent)) {
            returnIntent.putExtra(languageExt, "en");
            returnIntent.putExtra(countryExt, "US");
            return returnIntent;
        }

        String language = intent.getStringExtra(languageExt);
        String country = intent.getStringExtra(countryExt);
        returnIntent.putExtra(languageExt, language);
        returnIntent.putExtra(countryExt, country);

        return returnIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_locale);

        Intent intent = this.getIntent();
        TextView textView = (TextView) findViewById(R.id.set_for_change_locale_text);

        if (hasExtraRegardingLocal(intent)) {
            String lang = intent.getStringExtra(languageExt);
            String coun = intent.getStringExtra(countryExt);

            assert textView != null;
            textView.setText("set with: " + lang + "_" + coun);

            Locale locale = new Locale(lang, coun);
            setLocale(locale);
        } else {
            assert textView != null;
            textView.setText("set with: en_US(Default)");

            Locale locale = new Locale("en", "US");
            setLocale(locale);
        }
    }

    private void setLocale(@NonNull Locale locale) {
        try {
            setLocaleWith(locale);
        } catch (Exception e) {
            Log.e(TAG, "Failed to set locale", e);
        }
    }

    private void setLocaleWith(@NonNull Locale locale) throws
            ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Class<?> activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");
        Object amn = null;
        Configuration config = null;

        Method methodGetDefault = activityManagerNativeClass.getMethod("getDefault");
        methodGetDefault.setAccessible(true);
        amn = methodGetDefault.invoke(activityManagerNativeClass);

        Method methodGetConfiguration = activityManagerNativeClass.getMethod("getConfiguration");
        methodGetConfiguration.setAccessible(true);
        config = (Configuration) methodGetConfiguration.invoke(amn);

        Class<?> configClass = config.getClass();
        Field f = configClass.getField("userSetLocale");
        f.setBoolean(config, true);

        config.locale = locale;

        Method methodUpdateConfiguration = activityManagerNativeClass.getMethod("updateConfiguration", Configuration.class);
        methodUpdateConfiguration.setAccessible(true);
        methodUpdateConfiguration.invoke(amn, config);
    }
}
