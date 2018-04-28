package com.kazucocoa.droidtesthelper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

import com.kazucocoa.droidtesthelperlib.HandleAnimations

class HandleBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (MainActivity.hasExtraRegardingAccountType(intent)) {
            MainActivity.buildLaunchingMainActivityIntent(context, intent).let { launchMainActivity(context, it) }
        }

        if (HandleLocaleActivity.hasExtraRegardingLocal(intent)) {
            HandleLocaleActivity.buildLaunchHandleLocalActivityIntent(context, intent).let { launchHandleLocalActivity(context, it) }
        }

        if (HandleAnimations.hasExtraRegardingAnimation(intent)) {
            HandleAnimations.enableAnimationsWithIntent(intent)
        }
    }

    private fun launchMainActivity(context: Context, intent: Intent) {
        context.startActivity(intent)
    }

    private fun launchHandleLocalActivity(context: Context, intent: Intent) {
        context.startActivity(intent)
    }

    companion object {

        private val TAG = HandleBroadcastReceiver::class.java.simpleName

        const val RECEIVER_NAME = "DroidTestHelper"
    }
}
