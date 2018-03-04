package me.neutze.licensetest.security.tampered;

import android.content.Context;

/**
 * https://www.airpair.com/android/posts/adding-tampering-detection-to-your-android-app
 *
 * Samsung:
 *
 * http://stackoverflow.com/questions/13289748/can-packagemanager-getinstallerpackagename-tell-me-that-my-app-was-installed-f
 *
 * SafetyNet API can also be fooled
 */

public class Sideload {
    private static final String PLAYSTORE_ID = "com.android.vending";
    private static final String AMAZON_ID = "com.amazon.venezia";
    private static final String SAMSUNG_ID = "com.sec.android.app.samsungapps";

    public static boolean verifyInstaller(final Context context) {
        boolean result = false;
        final String installer = context.getPackageManager().getInstallerPackageName(context.getPackageName());

        if (installer != null) {
            if (installer.startsWith(PLAYSTORE_ID)) {
                result = true;
            }
            if (installer.startsWith(AMAZON_ID)) {
                result = true;
            }
            if (installer.startsWith(SAMSUNG_ID)) {
                result = true;
            }
        }
        if(!result){
            android.os.Process.killProcess(android.os.Process.myPid());
        }

        return result;
    }
}
