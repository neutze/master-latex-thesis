package me.neutze.licensetest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.amazon.android.Kiwi;
import com.google.android.vending.licensing.AESObfuscator;
import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.google.android.vending.licensing.ServerManagedPolicy;

public class MainActivity extends AppCompatActivity {
    private static final String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAo/iY2nt0OvE0DsOj0TBg+ue1s7NDjyzL+XXV8bz4EaWWIayFHFaMjJ6yX/XsL1uLpE6WqvVFrFwjcnGx4uP4YHpE3eTKMkV4ubMdUCEKkGu4t4NxnS46rOdEn8kouLx2katPF2HN5padsDgDj+51p0CEDewG3A2QST+EQK/fhds0TTP/nOgv8Qm8P7l0iayf6KbzY8E8oI422Ep7xqz1uFxmQn0VXETZnjcTh6SaA6k67lxk+vVHwDB44zgFp9HBGRAZC5gQEji483AyQCKs5z9WaFo2StEDiyeI11v+LT/bTEVhZZAGQHdR8nuJxh1Zfw6anuHcTtTfsuNzU7wn/wIDAQAB";
    private static final byte[] SALT;
    private Button mCheckLicenseButton;
    private LicenseChecker mChecker;
    private Handler mHandler;
    private MyLicenseCheckerCallback mLicenseCheckerCallback;
    private TextView mStatusText;
    private Toolbar toolbar_main;

    /* renamed from: me.neutze.licensetest.MainActivity.2 */
    class AnonymousClass2 implements Runnable {
        final /* synthetic */ String val$result;

        AnonymousClass2(String str) {
            this.val$result = str;
        }

        public void run() {
            MainActivity.this.mStatusText.setText(this.val$result);
            MainActivity.this.setProgressBarIndeterminateVisibility(false);
            MainActivity.this.mCheckLicenseButton.setEnabled(true);
        }
    }

    private class MyLicenseCheckerCallback implements LicenseCheckerCallback {
        private MyLicenseCheckerCallback() {
        }

        public void allow(int reason) {
            if (!MainActivity.this.isFinishing()) {
                MainActivity.this.displayResult(MainActivity.this.getApplicationContext().getResources().getString(R.string.allow));
                Log.e("JOHANNES", MainActivity.this.getApplicationContext().getResources().getString(R.string.allow));
            }
        }

        public void dontAllow(int reason) {
            if (!MainActivity.this.isFinishing()) {
                MainActivity.this.displayResult(MainActivity.this.getApplicationContext().getResources().getString(R.string.dont_allow));
                Log.e("JOHANNES", MainActivity.this.getApplicationContext().getResources().getString(R.string.allow));
            }
        }

        public void applicationError(int errorCode) {
            MainActivity.this.displayResult(MainActivity.this.getApplicationContext().getResources().getString(R.string.error));
            Log.e("JOHANNES", MainActivity.this.getApplicationContext().getResources().getString(R.string.error));
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!Kiwi.onActivityResult(this, requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onCreate(Bundle bundle) {
        onCreateMainActivity(bundle);
        \textit{Kiwi}.onCreate((Activity) this, true);
    }

    public Dialog onCreateDialog(int i) {
        Dialog dialog = \textit{Kiwi}.onCreateDialog(this, i);
        return dialog != null ? dialog : super.onCreateDialog(i);
    }

    public Dialog onCreateDialog(int i, Bundle bundle) {
        Dialog dialog = \textit{Kiwi}.onCreateDialog(this, i);
        return dialog != null ? dialog : super.onCreateDialog(i, bundle);
    }

    public void onDestroy() {
        onDestroyMainActivity();
        \textit{Kiwi}.onDestroy((Activity) this);
    }

    public void onPause() {
        super.onPause();
        \textit{Kiwi}.onPause(this);
    }

    public void onResume() {
        super.onResume();
        \textit{Kiwi}.onResume(this);
    }

    public void onStart() {
        super.onStart();
        \textit{Kiwi}.onStart(this);
    }

    public void onStop() {
        super.onStop();
        \textit{Kiwi}.onStop(this);
    }

    static {
        SALT = new byte[]{(byte) -20, (byte) 30, (byte) 50, (byte) -70, (byte) 33, (byte) -100, (byte) 32, (byte) -90, (byte) -88, (byte) 104, (byte) 12, (byte) -10, (byte) 72, (byte) -34, (byte) 115, (byte) 21, (byte) 62, (byte) 35, (byte) -12, (byte) 97};
    }

    private void onCreateMainActivity(Bundle icicle) {
        super.onCreate(icicle);
        setContentView((int) R.layout.activity_main);
        this.toolbar_main = (Toolbar) findViewById(R.id.toolbar_basic);
        this.mStatusText = (TextView) findViewById(R.id.text);
        this.mCheckLicenseButton = (Button) findViewById(R.id.button);
        initToolbar();
        ServerManagedPolicy serverPolicy = new ServerManagedPolicy(this, new AESObfuscator(SALT, getPackageName(), Secure.getString(getContentResolver(), "android_id")));
        this.mLicenseCheckerCallback = new MyLicenseCheckerCallback();
        this.mChecker = new LicenseChecker(this, serverPolicy, BASE64_PUBLIC_KEY);
        this.mCheckLicenseButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.mHandler = new Handler();
                MainActivity.this.doCheck();
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(this.toolbar_main);
    }

    private void doCheck() {
        this.mCheckLicenseButton.setEnabled(false);
        setProgressBarIndeterminateVisibility(true);
        this.mStatusText.setText(R.string.checking_license);
        this.mChecker.checkAccess(this.mLicenseCheckerCallback);
    }

    private void onDestroyMainActivity() {
        super.onDestroy();
        this.mChecker.onDestroy();
    }

    private void displayResult(String result) {
        this.mHandler.post(new AnonymousClass2(result));
    }
}
