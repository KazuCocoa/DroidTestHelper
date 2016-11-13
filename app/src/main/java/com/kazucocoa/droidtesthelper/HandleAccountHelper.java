package com.kazucocoa.droidtesthelper;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HandleAccountHelper {

    private static final String TAG = HandleAccountHelper.class.getSimpleName();

    private AccountManager accountManager;

    private Context context;

    public HandleAccountHelper(@NonNull Context context) {
        this.context = context;
        this.accountManager = AccountManager.get(context.getApplicationContext());
    }

    public void removeAccount(@NonNull String accountType) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Can't granted Manifest.permission.GET_ACCOUNTS");
            return;
        }
        Account[] accountArray = accountManager.getAccountsByType(accountType);

        for (final Account account : accountArray) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    accountManager.removeAccount(account,
                            null,
                            new AccountManagerCallback<Bundle>() {
                                @Override
                                public void run(AccountManagerFuture future) {
                                    try {
                                        future.getResult(20L, TimeUnit.SECONDS);
                                    } catch (OperationCanceledException | AuthenticatorException | IOException oce) {
                                        Log.d(TAG, String.valueOf(oce));
                                    }
                                }
                            }, null);
                } catch (SecurityException se) {
                    showToast("Can't remove account because of security exception.");
                    return;
                }
            } else {
                accountManager.removeAccount(account,
                        new AccountManagerCallback<Boolean>() {
                            @Override
                            public void run(AccountManagerFuture future) {
                                try {
                                    future.getResult(20L, TimeUnit.SECONDS);
                                } catch (OperationCanceledException | IOException | AuthenticatorException oce) {
                                    Log.d(TAG, String.valueOf(oce));
                                }
                            }
                        }, null);
            }
            showToast("remove accounts associated with: " + accountType);
        }
    }

    private void showToast(@NonNull String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
