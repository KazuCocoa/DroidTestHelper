package com.kazucocoa.droidtesthelper;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;

public class HandleLocaleActivity extends AppCompatActivity {

    private static String languageExt = "LANG";

    private static String countryExt = "COUNTRY";

    public static boolean hasExtraRegardingLocal(Intent intent) {
        return intent.hasExtra(languageExt) && intent.hasExtra(countryExt);
    }

    public static Intent buildLaunchHandleLocalActivityIntent(Context context, Intent intent) {
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
