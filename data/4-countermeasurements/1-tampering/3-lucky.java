package me.neutze.licensetest.security.tampered;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class LuckyPatcher {

    public static boolean checkInstall(final Context context) {
        boolean result = false;
        String[] luckypatcher = new String[]{
                // Lucky patcher
                "com.dimonvideo.luckypatcher",
                // Another lucky patcher
                "com.chelpus.lackypatch",
                // Black Mart alpha
                "com.blackmartalpha",
                // Black Mart
                "org.blackmart.market",
                // Lucky patcher 5.6.8
                "com.android.vending.billing.InAppBillingService.LUCK",
                // Freedom
                "cc.madkite.freedom",
                // All-in-one Downloader
                "com.allinone.free",
                // Get Apk Market
                "com.repodroid.app",
                // CreeHack
                "org.creeplays.hack",
                // Game Hacker
                "com.baseappfull.fwd"
        };

        for (String string : luckypatcher) {
          if(checkInstallerName(context, string)){

          }
            result = checkInstallerName(context, string);

            if (result) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }

        return result;
    }

    private static boolean checkInstallerName(Context context, String string) {
        PackageInfo info;
        boolean result = false;

        try {
            info = context.getPackageManager().getPackageInfo(string, 0);

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
