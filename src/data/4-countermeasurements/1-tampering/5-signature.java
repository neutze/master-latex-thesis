package me.neutze.licensetest.security.tampered;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

/**
 * http://stackoverflow.com/questions/13445598/lucky-patcher-how-can-i-protect-from-it
 * https://www.airpair.com/android/posts/adding-tampering-detection-to-your-android-app
 */

public class MySignature {
    //Signature used to sign the application
    static final String mySignature = "...";

    public static boolean checkSignature(final Context context) {
        boolean result = false;

        try {
            final Signature[] mSignatures = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES).signatures;

            checkResult(context, mSignatures);

            result = true;
        } catch (final PackageManager.NameNotFoundException ex) {
            android.os.Process.killProcess(android.os.Process.myPid());
        }

        if(!result){
            android.os.Process.killProcess(android.os.Process.myPid());
        }

        return result;
    }

    private static boolean checkResult(final Context context, final Signature[] signatures) {
        boolean result = false;

        if (signatures[0].toCharsString().equals(mySignature)) {
            result = true;
        }

        if (!result) {
            android.os.Process.killProcess(android.os.Process.myPid());
        }

        return result;
    }

    public static boolean checkAppSignature(final Context context) {
        //Signature used to sign the application
        static final String mySignature = "...";
        boolean result = false;

        try {
            final PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);

            for (final Signature signature : packageInfo.signatures) {
                final String currentSignature = signature.toCharsString();
                if (mySignature.equals(currentSignature)) {
                    result = true;
                }
            }
        } catch (final Exception e) {
            android.os.Process.killProcess(android.os.Process.myPid());
        }

        if (!result) {
            android.os.Process.killProcess(android.os.Process.myPid());
        }

        return result;
    }
}
