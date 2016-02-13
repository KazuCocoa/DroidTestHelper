package com.kazucocoa.droidtesthelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    static String intentExtraAccountType = "accountType";

    private String accountType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = this.getIntent();

        String action = intent.getAction();

        if (Intent.ACTION_MAIN.equals(action) && intent.hasExtra(intentExtraAccountType)) {
            accountType = intent.getStringExtra(intentExtraAccountType);
            removeAccount();
        }

        finish();
    }

    private void removeAccount() {
        HandleAccountHelper handleAccountHelper = new HandleAccountHelper(this);
        handleAccountHelper.removeAccount(accountType);
    }
}
