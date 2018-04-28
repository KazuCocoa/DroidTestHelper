package com.kazucocoa.droidtesthelperlib;

import android.content.Intent;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class HandleAnimationsTest {
    @Test
    public void hasExtraRegardingAnimationTest(){
        Intent intent = new Intent();
        intent.putExtra("ANIMATION", false);

        assertThat(HandleAnimations.Companion.hasExtraRegardingAnimation(intent), is(true));
    }

    @Test
    public void noHasExtraRegardingAnimationTest(){
        Intent intent = new Intent();
        assertThat(HandleAnimations.Companion.hasExtraRegardingAnimation(intent), is(false));
    }


}
