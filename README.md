# DroidTestHelper

[![Build Status](https://www.bitrise.io/app/6841fdd15c749475.svg?token=q73f-gNh1bFHCjbSgn9h-A&branch=master)](https://www.bitrise.io/app/6841fdd15c749475)

Helper application to test android applications. This application handle some settings via adb broadcast.
So, you can handle some settings via adb broadcast.

Some features are similar to https://github.com/linkedin/test-butler published by LinkedIn.

# Feature

- Remove particular account from AccountManager via `adb` broadcast.
    - You can remove application local data via `adb shell am clear your.package.name`.
    - But if your application support account manager, the command can't remove the data.
- Change language/country setting via some adb commands
- Change animation scale
- Clear data

# How to use

- 1. Git clone this repository, build and install this apk into your target device.
    - run the apk once
    - `$ adb shell am start -n com.kazucocoa.droidtesthelper/.MainActivity`

## remove account from account manager

- 2. Execute the following command. ( Should replace `your.target.account.type` to fit your application. )
    - `$ adb shell am broadcast -a 'DroidTestHelper' --es ACCOUNT_TYPE your.target.account.type`
    - Managing account only for API level 22 and lower.
    - If you clone this repository and build apk with the same signature for your test target apk, you can manage the account without permissions.
        - https://github.com/KazuCocoa/DroidTestHelper/issues/25#issuecomment-350422302

## change locale

- 2. Execute the following command to allow permission.
    - ```$ adb shell pm grant com.kazucocoa.droidtesthelper android.permission.CHANGE_CONFIGURATION```
- 3. Set `ja_JP`
    - ```$ adb shell am broadcast -a 'DroidTestHelper' --es LANG ja --es COUNTRY JP```

### reference
- https://developer.android.com/reference/java/util/Locale.html
- http://stackoverflow.com/questions/7973023/what-is-the-list-of-supported-languages-locales-on-android

## Change animations

- 2. Execute the following command to allow permission.
    - ```$ adb shell pm grant com.kazucocoa.droidtesthelper android.permission.SET_ANIMATION_SCALE```
    - ```$ adb shell pm grant com.kazucocoa.droidtesthelper android.permission.WRITE_SECURE_SETTINGS```
- 3. enable/disable animation scale 1.0f
    - enable
        - ```$ adb shell am broadcast -a 'DroidTestHelper' --ez ANIMATION true```
    - disable
        - ```$ adb shell am broadcast -a 'DroidTestHelper' --ez ANIMATION false```

## Clear Data

- 2. clear data (Requires API level 21+)
    - ```$ adb shell am broadcast -a 'DroidTestHelper' --es CLEAR_DATA com.example```

# DroidTestHelperLib

- HandleAccountHelper
- HandleAnimations
- HandleLocale
- HandlePermission
- HandleClearData

## Embedded DroidTestHelperLib into your library
### from Jitpack
- https://jitpack.io/#KazuCocoa/DroidTestHelper
 ```
dependencies {
    compile 'com.github.KazuCocoa:DroidTestHelper:0.3.0'
}
```

### Need Permissions to enable the above handler in AndroidManifest.xml

```xml
    <uses-permission android:name="android.permission.GET_ACCOUNTS" android:maxSdkVersion="22"/>

    <uses-permission android:name="android.permission.CHANGE_CONFIGURATION"/>
    <uses-permission android:name="android.permission.SET_ANIMATION_SCALE"/>
    <uses-permission android:name="android.permission.WRITE_SECURE_SETTINGS"/>

    <!-- to grant permission -->
    <uses-permission android:name="android.permission.GRANT_REVOKE_PERMISSIONS"/>

    <uses-permission
        android:name="android.permission.MANAGE_ACCOUNTS"
        android:maxSdkVersion="22" />
```

### Implementations

A package `com.kazucocoa.droidtesthelper` is example to use `com.kazucocoa.droidtesthelperlib`.

# Note for Android P
- Android P has `Restrictions on non-SDK interfaces` feature.
    - https://developer.android.com/preview/restrictions-non-sdk-interfaces
- `java.lang.NoSuchMethodException: setAnimationScales [class [F]` happen when we call `setAnimationScales` in animation handler

- To available the interface, we need below adb commands
    ```
    adb shell settings put global hidden_api_policy_pre_p_apps  1
    adb shell settings put global hidden_api_policy_p_apps 1
    ```
- To disable again, we need below adb commands
    ```
    adb shell settings delete global hidden_api_policy_pre_p_apps
    adb shell settings delete global hidden_api_policy_p_apps
    ```


# load map

I will add additional feature if I need.

# LICENSE

MIT
