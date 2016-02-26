package com.kazucocoa.droidtesthelper;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
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

        MainActivityPermissionsDispatcher.showGetAccountAndRemoveWithCheck(this);

        showGetAccountAndRemove();
        finish();
    }

    @NeedsPermission(Manifest.permission.GET_ACCOUNTS)
    public void showGetAccountAndRemove() {
        removeAccount();
    }

    @OnShowRationale(Manifest.permission.GET_ACCOUNTS)
    void showRationaleForGetAccount(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.permission_get_account)
                .setPositiveButton(R.string.button_allow, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton(R.string.button_deny, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .show();
    }

    public void removeAccount() {
        HandleAccountHelper handleAccountHelper = new HandleAccountHelper(this);
        handleAccountHelper.removeAccount(accountType);
    }
}
