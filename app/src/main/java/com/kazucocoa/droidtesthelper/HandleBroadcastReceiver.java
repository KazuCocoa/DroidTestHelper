package com.kazucocoa.droidtesthelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

public class HandleBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = HandleBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (MainActivity.hasExtraRegardingAccountType(intent)) {
            Intent buildLaunchingMainActivityIntent =
                    MainActivity.buildLaunchingMainActivityIntent(context, intent);
            launchMainActivity(context, buildLaunchingMainActivityIntent);
        }

        if (HandleLocaleActivity.hasExtraRegardingLocal(intent)) {
            Intent handleLocalActivityIntent =
                    HandleLocaleActivity.buildLaunchHandleLocalActivityIntent(context, intent);
            launchHandleLocalActivity(context, handleLocalActivityIntent);
        }

        if (HandleAnimations.hasExtraRegardingAnimation(intent)) {
            HandleAnimations.enableAnimationsWithIntent(intent);
        }
    }

    public void launchMainActivity(@NonNull Context context, @NonNull Intent intent) {
        context.startActivity(intent);
    }

    public void launchHandleLocalActivity(@NonNull Context context, @NonNull Intent intent) {
        context.startActivity(intent);
    }
}
