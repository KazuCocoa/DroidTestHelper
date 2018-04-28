package com.kazucocoa.droidtesthelperlib

import android.Manifest
import android.accounts.Account
import android.accounts.AccountManager
import android.accounts.AccountManagerCallback
import android.accounts.AccountManagerFuture
import android.accounts.AuthenticatorException
import android.accounts.OperationCanceledException
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.widget.Toast

import java.io.IOException
import java.util.concurrent.TimeUnit

class HandleAccountHelper(private val context: Context) {

    private val accountManager: AccountManager = AccountManager.get(context.applicationContext)

    fun removeAccount(accountType: String) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.GET_ACCOUNTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e(TAG, "Can't granted Manifest.permission.GET_ACCOUNTS")
            return
        }
        val accountArray = accountManager.getAccountsByType(accountType)

        for (account in accountArray) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    accountManager.removeAccount(account, null,
                        { future ->
                            try {
                                future.getResult(20L, TimeUnit.SECONDS)
                            } catch (oce: OperationCanceledException) {
                                Log.d(TAG, oce.toString())
                            } catch (oce: AuthenticatorException) {
                                Log.d(TAG, oce.toString())
                            } catch (oce: IOException) {
                                Log.d(TAG, oce.toString())
                            }
                        }, null
                    )
                } catch (se: SecurityException) {
                    showToast("Can't remove account because of security exception.")
                    return
                }
            } else {
                accountManager.removeAccount(account,
                    { future ->
                        try {
                            future.getResult(20L, TimeUnit.SECONDS)
                        } catch (oce: OperationCanceledException) {
                            Log.d(TAG, oce.toString())
                        } catch (oce: IOException) {
                            Log.d(TAG, oce.toString())
                        } catch (oce: AuthenticatorException) {
                            Log.d(TAG, oce.toString())
                        }
                    }, null
                )
            }
            showToast("remove accounts associated with: $accountType")
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    companion object {

        private val TAG = HandleAccountHelper::class.java.simpleName
    }
}
