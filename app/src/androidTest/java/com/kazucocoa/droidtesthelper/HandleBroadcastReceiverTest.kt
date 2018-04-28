package com.kazucocoa.droidtesthelper

import android.content.Context
import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat

@RunWith(AndroidJUnit4::class)
class HandleBroadcastReceiverTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getContext()
    }

    @Test
    fun buildLaunchingMainActivityIntentTest() {
        val intent = Intent()
        intent.putExtra("ACCOUNT_TYPE", "my.account.type")

        val buildLaunchingMainActivityIntent =
            MainActivity.buildLaunchingMainActivityIntent(context, intent)

        assertThat(
            buildLaunchingMainActivityIntent.getStringExtra("accountType"),
            `is`("my.account.type")
        )
    }
}
