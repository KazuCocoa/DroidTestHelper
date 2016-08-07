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

    private HandleAccountReceiver handleAccountReceiver;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getContext();
        handleAccountReceiver = new HandleAccountReceiver();
    }

    @Test
    public void buildLaunchingMainActivityIntentTest(){
        Intent intent =
                handleAccountReceiver.buildLaunchingMainActivityIntent(context, "my.account.type");

        assertThat(intent.getStringExtra("accountType"), is("my.account.type"));
    }
}
