package com.kazucocoa.droidtesthelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class HandleAccountReceiver extends BroadcastReceiver {

    private static final String TAG = HandleAccountReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (MainActivity.hasExtraRegardingLocal(intent)) {
            Intent buildLaunchingMainActivityIntent =
                    MainActivity.buildLaunchingMainActivityIntent(context, intent);
            launchMainActivity(context, buildLaunchingMainActivityIntent);
        } else if (HandleLocaleActivity.hasExtraRegardingLocal(intent)) {
            Intent handleLocalActivityIntent =
                    HandleLocaleActivity.buildLaunchHandleLocalActivityIntent(context, intent);
            launchHandleLocalActivity(context, handleLocalActivityIntent);
        }
    }

    public void launchMainActivity(Context context, Intent intent) {
        context.startActivity(intent);
    }

    public void launchHandleLocalActivity(Context context, Intent intent) {
        context.startActivity(intent);
    }
}
