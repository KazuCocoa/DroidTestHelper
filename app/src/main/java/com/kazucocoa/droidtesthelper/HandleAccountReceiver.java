package com.kazucocoa.droidtesthelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class HandleAccountReceiver extends BroadcastReceiver {

    private static final String TAG = HandleAccountReceiver.class.getSimpleName();

    private String stringExtraViaReceiver = "ACCOUNT_TYPE";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.hasExtra(stringExtraViaReceiver)) {
            String accountType = intent.getStringExtra(stringExtraViaReceiver);
            if (accountType == null) {
                accountType = "";
            }

            launchMainActivity(context,
                    buildLaunchingMainActivityIntent(context, accountType));
        } else if (HandleLocaleActivity.hasExtraRegardingLocal(intent)) {
            Intent handleLocalActivityIntent =
                    HandleLocaleActivity.buildLaunchHandleLocalActivityIntent(context, intent);
            launchHandleLocalActivity(context, handleLocalActivityIntent);
        }
    }

    public Intent buildLaunchingMainActivityIntent(Context context, String accountType) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_MAIN);
        intent.putExtra(MainActivity.intentExtraAccountType, accountType);
        return intent;
    }

    public void launchMainActivity(Context context, Intent intent) {
        context.startActivity(intent);
    }

    public void launchHandleLocalActivity(Context context, Intent intent) {
        context.startActivity(intent);
    }
}
