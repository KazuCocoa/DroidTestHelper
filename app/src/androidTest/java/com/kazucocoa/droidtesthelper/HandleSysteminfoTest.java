package com.kazucocoa.droidtesthelper;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class HandleSysteminfoTest {

    private static HandleSystemInfo systemInfo;

    @BeforeClass
    public static void generateMockContext() {
        systemInfo = new HandleSystemInfo(InstrumentationRegistry.getContext());

    }

    @Test
    public void getModelName() {
        assertThat(systemInfo.getModelName(), is(notNullValue()));
    }

    @Test
    public void getSDKVersion() {
        assertThat(systemInfo.getSDKVersion(), is(notNullValue()));
    }

    @Test
    public void getBuildId() {
        assertThat(systemInfo.getBuildId(), is(notNullValue()));
    }

    @Test
    public void getVersionRelease() {
        assertThat(systemInfo.getVersionRelease(), is(notNullValue()));
    }

    @Test
    public void getNoMacAddress() {
        assertThat(systemInfo.getMacAddress(), is(notNullValue()));
    }

    @Test
    public void getDisplayResolution() {
        assertThat(systemInfo.getDisplayResolution(), is(notNullValue()));
    }

    @Test
    public void getDPI() {
        assertThat(systemInfo.getDPI(), is(notNullValue()));
    }

    @Test
    public void getSIMSerialNumber() {
        assertThat(systemInfo.getSIMSerialNumber(), is(notNullValue()));
    }

    @Test
    public void getSIMPhoneNumber() {
        assertThat(systemInfo.getSIMPhoneNumber(), is(notNullValue()));
    }

    @Test
    public void getSIMServiceProvider() {
        assertThat(systemInfo.getSIMServiceProvider(), is(notNullValue()));
    }
}
