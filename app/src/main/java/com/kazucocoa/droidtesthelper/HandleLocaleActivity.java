package com.kazucocoa.droidtesthelper;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;

public class HandleLocaleActivity extends AppCompatActivity {

    public static String intentLanguage = "language";

    public static String intentCountry = "country";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_locale);

        Intent intent = this.getIntent();

        if (intent.hasExtra(intentLanguage) && intent.hasExtra(intentCountry)) {
            String lang = intent.getStringExtra(intentLanguage);
            String coun = intent.getStringExtra(intentCountry);
            // Locale(String language, String country)
            Locale locale = new Locale(lang, coun);
            setLocale(locale);
        } else {
            // Locale(String language, String country)
            Locale locale = new Locale("en", "US");
            setLocale(locale);
        }
    }

    private void setLocale(Locale locale) {
        try {
            Class amnClass = Class.forName("android.app.ActivityManagerNative");
            Object amn = null;
            Configuration config = null;

            // amn = ActivityManagerNative.getDefault();
            Method methodGetDefault = amnClass.getMethod("getDefault");
            methodGetDefault.setAccessible(true);
            amn = methodGetDefault.invoke(amnClass);

            // config = amn.getConfiguration();
            Method methodGetConfiguration = amnClass.getMethod("getConfiguration");
            methodGetConfiguration.setAccessible(true);
            config = (Configuration) methodGetConfiguration.invoke(amn);

            // config.userSetLocale = true;
            Class configClass = config.getClass();
            Field f = configClass.getField("userSetLocale");
            f.setBoolean(config, true);

            // set the locale to the new value
            config.locale = locale;

            // amn.updateConfiguration(config);
            Method methodUpdateConfiguration = amnClass.getMethod("updateConfiguration", Configuration.class);
            methodUpdateConfiguration.setAccessible(true);
            methodUpdateConfiguration.invoke(amn, config);
        } catch (Exception e) {
            // error
        }
    }
}
