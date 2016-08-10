package com.kazucocoa.droidtesthelper;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class HandleAccountReceiverTest {

    private Context context;

    private HandleBroadcastReceiver handleAccountReceiver;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getContext();
        handleAccountReceiver = new HandleBroadcastReceiver();
    }

    @Test
    public void buildLaunchingMainActivityIntentTest(){
        Intent intent = new Intent();
        intent.putExtra("ACCOUNT_TYPE", "my.account.type");

        Intent buildLaunchingMainActivityIntent =
                MainActivity.buildLaunchingMainActivityIntent(context, intent);

        assertThat(buildLaunchingMainActivityIntent.getStringExtra("accountType"), is("my.account.type"));
    }
}
