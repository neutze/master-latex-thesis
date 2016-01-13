package me.neutze.licensetest.security.tampered;

import android.content.Context;

import java.io.File;

/**
 * http://stackoverflow.com/questions/10585961/way-to-protect-from-lucky-patcher-play-licensing
 *
 * http://forum.xda-developers.com/showthread.php?t=2279813 --see- might change name
 */


public class Root {

    public static boolean findBinary(Context context, final String binaryName) {
        boolean result = false;
        String[] places = {
                "/sbin/",
                "/system/bin/",
                "/system/xbin/",
                "/data/local/xbin/",
                "/data/local/bin/",
                "/system/sd/xbin/",
                "/system/bin/failsafe/",
                "/data/local/"
        };

        for (final String where : places) {
            if (new File(where + binaryName).exists()) {
                result = true;
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }

        return result;
    }
}
