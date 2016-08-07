package com.kazucocoa.droidtesthelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class HandleAccountReceiver extends BroadcastReceiver {

    private static final String TAG = HandleAccountReceiver.class.getSimpleName();

    private String accountType;

    private String stringExtraViaReceiver = "ACCOUNT_TYPE";

    private String langExt = "LANG";

    private String countryExt = "COUNTRY";

    private String language;

    private String country;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.hasExtra(stringExtraViaReceiver)) {
            accountType = intent.getStringExtra(stringExtraViaReceiver);
            if (accountType == null) {
                accountType = "";
            }

            launchMainActivity(context);
        } else if (intent.hasExtra(langExt) && intent.hasExtra(countryExt)) {
            language = intent.getStringExtra(langExt);
            country = intent.getStringExtra(countryExt);

            launchHandleLocalActivity(context);
        }
    }

    public Intent buildLaunchingMainActivityIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_MAIN);
        intent.putExtra(MainActivity.intentExtraAccountType, accountType);
        return intent;
    }

    public void launchMainActivity(Context context) {
        context.startActivity(buildLaunchingMainActivityIntent(context));
    }

    public Intent buildlaunchHandleLocalActivityintent(Context context) {
        Intent intent = new Intent(context, HandleLocaleActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.putExtra(HandleLocaleActivity.intentLanguage, language);
        intent.putExtra(HandleLocaleActivity.intentCountry, country);
        return intent;
    }

    public void launchHandleLocalActivity(Context context) {
        context.startActivity(buildlaunchHandleLocalActivityintent(context));
    }
}
