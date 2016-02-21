package me.neutze.licensetest.security.tampered;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class Lucky Patcher {

    public static boolean checkInstall(final Context context) {
        boolean result = false;
        String luckypatcher =
                // Lucky patcher 6.0.4
                "com.android.vending.billing.InAppBillingService.LUCK",
        };

        try {
            info = context.getPackageManager().getPackageInfo(luckypatcher, 0);

            if (info != null) {
                android.os.Process.killProcess(android.os.Process.myPid());
                result = true;
            }

        } catch (final PackageManager.NameNotFoundException ignored) {
        }

        if (result) {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        
        return result;
    }
}
