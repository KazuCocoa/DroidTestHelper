package com.kazucocoa.droidtesthelper;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getSimpleName();

    public static String intentExtraAccountType = "accountType";

    private static String stringExtraViaReceiver = "ACCOUNT_TYPE";

    private String accountType = "";

    private Button button;

    private HandlePermission handlePermission;

    public static boolean hasExtraRegardingAccountType(Intent intent) {
        return intent.hasExtra(stringExtraViaReceiver);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handlePermission = new HandlePermission();

        Intent intent = this.getIntent();

        String action = intent.getAction();

        if (Intent.ACTION_MAIN.equals(action) && intent.hasExtra(intentExtraAccountType)) {
            accountType = intent.getStringExtra(intentExtraAccountType);

            MainActivityPermissionsDispatcher.showGetAccountAndRemoveWithCheck(this);
            showGetAccountAndRemove();

            finish();
        }

        MainActivityPermissionsDispatcher.showGetAccountAndRemoveWithCheck(this);
        showGetAccountAndRemove();

        setButtons();
    }

    private void setButtons() {
        button = (Button) findViewById(R.id.go_to_activity_handle_locale_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HandleLocaleActivity.class));
            }
        });
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
                .show();
    }

    public void removeAccount() {
        HandleAccountHelper handleAccountHelper = new HandleAccountHelper(this);
        handleAccountHelper.removeAccount(accountType);
    }

    public static Intent buildLaunchingMainActivityIntent(@NonNull Context context, @NonNull Intent intent) {
        String accountType = intent.getStringExtra(stringExtraViaReceiver);

        Intent returnIntent = new Intent(context, MainActivity.class);
        returnIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        returnIntent.setAction(Intent.ACTION_MAIN);
        returnIntent.putExtra(MainActivity.intentExtraAccountType, accountType);
        return returnIntent;
    }

    // TODO: implement https://developer.android.com/guide/components/aidl.html if you'd like to use
    // this method.
    private void grantAllPermissions() {
        grantPermission(this, Manifest.permission.GET_ACCOUNTS);
        grantPermission(getApplicationContext(), Manifest.permission.CHANGE_CONFIGURATION);
        grantPermission(getApplicationContext(), Manifest.permission.SET_ANIMATION_SCALE);
        grantPermission(this, Manifest.permission.WRITE_SECURE_SETTINGS);
    }

    // String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    // mainActivity.grantPermission(this, permission);
    public void grantPermission(@NonNull Context context, @NonNull String permission) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Log.i(TAG, "Don't need to grant permission because target apk is under API Level 23");
            return;
        }

        if (!handlePermission.grantPermission(context, context.getPackageName(), permission)) {
            throw new IllegalArgumentException("Failed to grant permission " + permission);
        }
    }
}
