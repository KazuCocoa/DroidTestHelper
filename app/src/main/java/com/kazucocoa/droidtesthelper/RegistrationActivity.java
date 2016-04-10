package com.kazucocoa.droidtesthelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity {

    private HandleSystemInfo systemInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        systemInfo = new HandleSystemInfo(this);

        TextView textView = (TextView) findViewById(R.id.registration_text_view);
        assert textView != null;
        setSystemInfoOnText(textView);
    }

    private void setSystemInfoOnText(TextView textView) {
        textView.setText("registration parameters\n");
        textView.append("モデル名: " + systemInfo.getModelName() + "\n");
        textView.append("SDKバージョン: " + systemInfo.getSDKVersion() + "\n");
        textView.append("OSバージョン: " + systemInfo.getVersionRelease() + "\n");
        textView.append("ビルドバージョン: " + systemInfo.getBuildId() + "\n");
        textView.append("MACアドレス: " + systemInfo.getMacAddress() + "\n");
        textView.append("解像度(width x height): " + systemInfo.getDisplayResolution() + "\n");
        textView.append("DPI(width x height): " + systemInfo.getDPI() + "\n");
        textView.append("SIMシリアル番号: " + systemInfo.getSIMSerialNumber() + "\n");
        textView.append("SIM電話番号: " + systemInfo.getSIMPhoneNumber() + "\n");
        textView.append("SIMプロバイダ: " + systemInfo.getSIMServiceProvider() + "\n");
    }
}
