package com.kazucocoa.droidtesthelper;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HandleAccountHelper {

    private static final String TAG = HandleAccountHelper.class.getSimpleName();

    private AccountManager accountManager;

    private Context context;

    public HandleAccountHelper(Context context) {
        this.context = context;
        this.accountManager = AccountManager.get(context.getApplicationContext());
    }

    public void removeAccount(@NonNull String accountType) {
        Account[] accountArray = accountManager.getAccountsByType(accountType);

        for(final Account account : accountArray) {
            accountManager.removeAccount(account,
                    new AccountManagerCallback<Boolean>() {
                        @Override
                        public void run(AccountManagerFuture future) {
                            try {
                                future.getResult(20L, TimeUnit.SECONDS);
                            } catch (OperationCanceledException oce) {
                                Log.d(TAG, String.valueOf(oce));
                            } catch (IOException ioe) {
                                Log.d(TAG, String.valueOf(ioe));
                            } catch (AuthenticatorException ae) {
                                Log.d(TAG, String.valueOf(ae));
                            }
                        }
                    }, null);
            showToast("remove accounts associated with: " + accountType);
            // TODO: We should use the following code when build with over android.os.Build.VERSION_CODES.LOLLIPOP_MR1
            // accountManager.removeAccount(account, null, null, null).getResult(20L, TimeUnit.SECONDS);
        }
    }

    private void showToast(String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
