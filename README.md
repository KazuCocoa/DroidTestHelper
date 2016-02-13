# DroidTestHelper

Helper application to test android applications.

# Feature

- Remove particuler account from AccountManager via `adb` broadcast.
    - You can remve application local data via `adb shell am clear your.package.name`.
    - But if your application support account manager, the command can't remove the data.

# How to use

1. Git clone this repository, build and install this apk into your target device.
2. Execute the following command. ( Should replace `your.target.account.type` to fit your application. )
    - `$ adb shell am broadcast -a 'DroidTestHelper' --es ACCOUNT_TYPE your.target.account.type`

# More featere ?

I will add additional feature if I need.

# LICENSE

MIT
