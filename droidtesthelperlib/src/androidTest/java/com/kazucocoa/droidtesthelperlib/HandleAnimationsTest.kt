package com.kazucocoa.droidtesthelperlib

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat

@RunWith(AndroidJUnit4::class)
class HandleAnimationsTest {
    @Test
    fun hasExtraRegardingAnimationTest() {
        val intent = Intent()
        intent.putExtra("ANIMATION", false)

        assertThat(HandleAnimations.hasExtraRegardingAnimation(intent), `is`(true))
    }

    @Test
    fun noHasExtraRegardingAnimationTest() {
        val intent = Intent()
        assertThat(HandleAnimations.hasExtraRegardingAnimation(intent), `is`(false))
    }
}
