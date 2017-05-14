package com.kazucocoa.droidtesthelper;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.kazucocoa.droidtesthelperlib.HandleLocale;

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

        HandleLocale handleLocale = new HandleLocale();

        Intent intent = this.getIntent();
        TextView textView = (TextView) findViewById(R.id.set_for_change_locale_text);

        if (hasExtraRegardingLocal(intent)) {
            String lang = intent.getStringExtra(languageExt);
            String coun = intent.getStringExtra(countryExt);

            assert textView != null;
            textView.setText("set with: " + lang + "_" + coun);

            Locale locale = new Locale(lang, coun);
            handleLocale.setLocale(locale);
        } else {
            assert textView != null;
            textView.setText("set with: en_US(Default)");

            Locale locale = new Locale("en", "US");
            handleLocale.setLocale(locale);
        }
    }
}
