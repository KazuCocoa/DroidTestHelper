package com.kazucocoa.droidtesthelper

import android.content.Context
import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat

@RunWith(AndroidJUnit4::class)
class HandleLocaleActivityTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun buildLaunchHandleLocalActivityIntent() {
        val intent = Intent()
        intent.putExtra("LANG", "ja")
        intent.putExtra("COUNTRY", "JP")

        val handleLocalActivityIntent =
            HandleLocaleActivity.buildLaunchHandleLocalActivityIntent(context, intent)

        assertThat(handleLocalActivityIntent.getStringExtra("LANG"), `is`("ja"))
        assertThat(handleLocalActivityIntent.getStringExtra("COUNTRY"), `is`("JP"))
    }

    @Test
    fun defaultBuildLaunchHandleLocalActivityIntent() {
        val intent = Intent()

        val handleLocalActivityIntent =
            HandleLocaleActivity.buildLaunchHandleLocalActivityIntent(context, intent)

        assertThat(handleLocalActivityIntent.getStringExtra("LANG"), `is`("en"))
        assertThat(handleLocalActivityIntent.getStringExtra("COUNTRY"), `is`("US"))
    }
}
