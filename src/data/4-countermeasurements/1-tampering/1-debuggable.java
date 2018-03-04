package me.neutze.licensetest.security.tampered;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import me.neutze.licensetest.utils.LogToastHelper;

/**
 * http://android-developers.blogspot.de/2010/09/securing-android-lvl-applications.html
 */

public class Debuggable {

    public static boolean isDebuggable(Context context) {
        boolean debuggable = (0 != (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));

        if (debuggable) {
            android.os.Process.killProcess(android.os.Process.myPid());
        }

        return debuggable;
    }
}
