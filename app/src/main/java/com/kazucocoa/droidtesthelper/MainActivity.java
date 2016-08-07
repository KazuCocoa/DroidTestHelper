package com.kazucocoa.droidtesthelper;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    public static String intentExtraAccountType = "accountType";

    private static String stringExtraViaReceiver = "ACCOUNT_TYPE";

    private String accountType = "";

    private Button button;

    public static boolean hasExtraRegardingLocal(Intent intent) {
        return intent.hasExtra(stringExtraViaReceiver);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    public static Intent buildLaunchingMainActivityIntent(Context context, Intent intent) {
        String accountType = intent.getStringExtra(stringExtraViaReceiver);

        Intent returnIntent = new Intent(context, MainActivity.class);
        returnIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        returnIntent.setAction(Intent.ACTION_MAIN);
        returnIntent.putExtra(MainActivity.intentExtraAccountType, accountType);
        return returnIntent;
    }

}
