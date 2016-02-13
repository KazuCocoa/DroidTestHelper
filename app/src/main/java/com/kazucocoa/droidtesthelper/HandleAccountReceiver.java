package com.kazucocoa.droidtesthelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class HandleAccountReceiver extends BroadcastReceiver {

    private static final String TAG = HandleAccountReceiver.class.getSimpleName();

    private String accountType;

    private String stringExtraViaReceiver = "ACCOUNT_TYPE";

    @Override
    public void onReceive(Context context, Intent intent) {

        accountType = intent.getStringExtra(stringExtraViaReceiver);
        if (accountType == null) {
            accountType = "";
        }

        launchMainActivity(context);
    }

    public void launchMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_MAIN);
        intent.putExtra(MainActivity.intentExtraAccountType, accountType);
        context.startActivity(intent);
    }
}
