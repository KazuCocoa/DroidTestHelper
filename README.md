# DroidTestHelper

Helper application to test android applications.

# Feature

- Remove particuler account from AccountManager via `adb` broadcast.
    - You can remve application local data via `adb shell am clear your.package.name`.
    - But if your application support account manager, the command can't remove the data.
- Change language/country setting via some sdb commands

# How to use

- 1. Git clone this repository, build and install this apk into your target device.
    - run the apk once
    - `$ adb shell am start -n com.kazucocoa.droidtesthelper/.MainActivity`

## remove account from account manager

- 2. Execute the following command. ( Should replace `your.target.account.type` to fit your application. )
    - `$ adb shell am broadcast -a 'DroidTestHelper' --es ACCOUNT_TYPE your.target.account.type`

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

# More featere ?

I will add additional feature if I need.

# LICENSE

MIT
