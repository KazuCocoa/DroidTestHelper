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
public class HandleLocaleActivityTest {

     private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getContext();
    }

    @Test
    public void buildLaunchHandleLocalActivityIntent(){
        Intent intent = new Intent();
        intent.putExtra("LANG", "ja");
        intent.putExtra("COUNTRY", "JP");

        Intent handleLocalActivityIntent =
                HandleLocaleActivity.buildLaunchHandleLocalActivityIntent(context, intent);

        assertThat(handleLocalActivityIntent.getStringExtra("LANG"), is("ja"));
        assertThat(handleLocalActivityIntent.getStringExtra("COUNTRY"), is("JP"));
    }

    @Test
    public void defaultBuildLaunchHandleLocalActivityIntent(){
        Intent intent = new Intent();

        Intent handleLocalActivityIntent =
                HandleLocaleActivity.buildLaunchHandleLocalActivityIntent(context, intent);

        assertThat(handleLocalActivityIntent.getStringExtra("LANG"), is("en"));
        assertThat(handleLocalActivityIntent.getStringExtra("COUNTRY"), is("US"));
    }
}
