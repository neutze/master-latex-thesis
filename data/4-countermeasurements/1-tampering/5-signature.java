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
    static final String M_SIGNATURE = "3082038b30820273a0030201020204277977f8300d06092a864886f70d01010b05003076310b3009060355040613024445310f300d0603550408130642617965726e3111300f06035504070c084dc3bc6e6368656e31143012060355040a0c0b5455204dc3bc6e6368656e31133011060355040b130a496e666f726d6174696b311830160603550403130f4a6f68616e6e6573204e6575747a65301e170d3135313032333136303435395a170d3430313031363136303435395a3076310b3009060355040613024445310f300d0603550408130642617965726e3111300f06035504070c084dc3bc6e6368656e31143012060355040a0c0b5455204dc3bc6e6368656e31133011060355040b130a496e666f726d6174696b311830160603550403130f4a6f68616e6e6573204e6575747a6530820122300d06092a864886f70d01010105000382010f003082010a0282010100818b8a40d171b30323dc7b13f160c3b4ca8071199e5c444d0cf55dbd5f73f356a530cb412fd701975f8b03dc2ddd46cfa482bbf7eb2221e66d723ba8ad08a7a09e9ecca33de9ff9235638bc39086f4b46b504a0c5cf65121eec2554e94da54b6b53915e4fb5a21005e7c0838109f846487d6fde6f7968dc42a7ce9de26ec763d806fdbb395d54b975600e7a5ababdc5716f2454f1eacaf4cf3cdfdb34d2fc83e8c763a71865bddded96d431e65089790975640f3008b097196aa1a24010d4abd2a25680dcfb0a7df7ceabd87be410c26e530ab100affb6e6066e3e99a53f65cedc3984ba725eee4621e31de5f2715042a916dd49845da6e096e248dc9a386e9b0203010001a321301f301d0603551d0e04160414b8cd6a00abc4761a37793a721e8424cd582b1744300d06092a864886f70d01010b0500038201010008b78349950cfe53117281d10102d21937ff7b13d5314072ab5b76fc774ae138931e89a4299442b72b12b0a89d4b691b3277a1a0cecfdcc35eed49c9b1bb479593e474965774d48e611bd8330d0afa01bc5590eb247a980b89627cb822545b581019fd2ce62dedf0f4229f7a940e301ebe383e020973073e2762bbe8b263ca66ea5cbb222721a8cab7b09440be0c58529a7ec740bc38023de7eefcb24ace1b36e5b4d4a47f47b7fb06c324ed452d8ba23bd7574ab9f3025de149985b6118739ea552e6c20a59e967d0fc44d71cd08281065730f1e6ff7ece48689b4309f040f9c858b1987016a89c5581f2d0913692bb10c76192ffc06407c160d68ed61f5619";

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

        if (signatures[0].toCharsString().equals(M_SIGNATURE)) {
            result = true;
        }

        if (!result) {
            android.os.Process.killProcess(android.os.Process.myPid());
        }

        return result;
    }

    public static boolean checkAppSignature(final Context context) {
        boolean result = false;

        try {
            final PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);

            for (final Signature signature : packageInfo.signatures) {
                final String currentSignature = signature.toCharsString();
                if (M_SIGNATURE.equals(currentSignature)) {
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
