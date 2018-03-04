package me.neutze.licensetest;

import com.google.android.vending.licensing.AESObfuscator;
import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.google.android.vending.licensing.ServerManagedPolicy;

import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import me.neutze.licensetest.security.tampered.DebugCheck;
import me.neutze.licensetest.security.tampered.Lucky Patcher;
import me.neutze.licensetest.security.tampered.SignatureCheck;
import me.neutze.licensetest.security.tampered.SimpleCheck;
import me.neutze.licensetest.security.tampered.VerifyInstaller;
import me.neutze.licensetest.security.test.Test;
import me.neutze.licensetest.utils.LogToastHelper;
import me.neutze.lvltest.R;


public class MainActivity extends AppCompatActivity {

    public static final int VALID = 1337;
    private static final String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzxANViAFOc3cAXBWzhQiX4arP9o35JTduAUdw2Ph6hhjE6JTYcPcPl1Jx2GgCY//yvjSxpcjW76W9TqXjrImxbKZezS8WXfX/gLfvOeRn3IBBkLOGaNrMmLR8R2I8Z/qtUbrqFyDA2ShRFcGRbs5f157D11e3ZZXqz74c+j1EG9ojg+56T2XnU7/YcjJfrX8FIjQBGxeESDNYvfo2blXZZe4xzk/U4wF2be1qEnhk6EhI+lkZBkAyzQbCoVpsmaVfr+f3h2I7K6MWCGkzWyATp5H3oBNkwgn55Fqu+i0f2VpWRpbYyBMyim5OmxVQ+OeuU4VEw/XtMf9e+AOhMGySQIDAQAB";
    private static final byte[] SALT = new byte[]{
            -20, 30, 50, -70, 33, -100, 32, -90, -88, 104,
            12, -10, 72, -34, 115, 21, 62, 35, -12, 97};
    private static final String TAG = "MainActivity";
    private Handler mHandler;
    private TextView mStatusText;
    private Button mCheckLicenseButton;
    private Toolbar mToolbar;
    private MyLicenseCheckerCallback mLicenseCheckerCallback;
    private LicenseChecker mChecker;

    @Override
    public void onCreate(final Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_basic);
        mStatusText = (TextView) findViewById(R.id.text);
        mCheckLicenseButton = (Button) findViewById(R.id.button);

        tamperingDetetion();

        initToolbar();

        Test.suchTest();

        final String mAndroidId = Settings.Secure.getString(this.getContentResolverSettings.Secure.ANDROID_ID);
        final AESObfuscator mObsfuscator = new AESObfuscator(SALT, getPackageName(), mAndroidId);
        final ServerManagedPolicy serverPolicy = new ServerManagedPolicy(this, mObsfuscator);
        mLicenseCheckerCallback = new MyLicenseCheckerCallback();
        mChecker = new LicenseChecker(this, serverPolicy, BASE64_PUBLIC_KEY);

        mChecker.checkAccess(mLicenseCheckerCallback);

        mCheckLicenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler = new Handler();
                doCheck();
            }
        });
    }

    private void tamperingDetetion() {
        if(DebugCheck.isDebuggable(getApplicationContext())){
            LogToastHelper.feedback(getApplicationContext(), TAG, "DebugCheck.isDebuggable==true");
            //android.os.Process.killProcess(android.os.Process.myPid());
        }

        if (SimpleCheck.findBinary(getApplicationContext(), "su")) {
            LogToastHelper.feedback(getApplicationContext(), TAG, "SimpleCheck.findBinary(su)==true");
            //android.os.Process.killProcess(android.os.Process.myPid());
        }
        if (Lucky Patcher.checkInstall(this)) {
            LogToastHelper.feedback(getApplicationContext(), TAG, "Lucky Patcher.checkInstall()==true");
            //android.os.Process.killProcess(//android.os.Process.myPid());
        }

        if (!SignatureCheck.checkSignature(this)) {
            LogToastHelper.feedback(getApplicationContext(), TAG, "SignatureCheck.checkSignature() == false");
            //android.os.Process.killProcess(//android.os.Process.myPid());
        }
        if (SignatureCheck.checkAppSignature(this) != VALID) {
            LogToastHelper.feedback(getApplicationContext(), TAG, "SignatureCheck.checkAppSignature != 1337");
            //android.os.Process.killProcess(//android.os.Process.myPid());
        }

        // since the application is not downloaded from google play this is deactivated right nao
        if (!VerifyInstaller.verifyInstaller(this)) {
            LogToastHelper.feedback(getApplicationContext(), TAG, "VerifyInstaller.verifyInstaller==false");
            //android.os.Process.killProcess(//android.os.Process.myPid());
        }

    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
    }

    private void doCheck() {
        mCheckLicenseButton.setEnabled(false);
        setProgressBarIndeterminateVisibility(true);
        mStatusText.setText(R.string.checking_license);
        mChecker.checkAccess(mLicenseCheckerCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mChecker.onDestroy();
    }

    private void displayResult(final String result) {
        mHandler.post(new Runnable() {
            public void run() {
                mStatusText.setText(result);
                setProgressBarIndeterminateVisibility(false);
                mCheckLicenseButton.setEnabled(true);
            }
        });
    }

    private class MyLicenseCheckerCallback implements LicenseCheckerCallback {

        @Override
        public void allow(final int reason) {
            ...
        }

        @Override
        public void dontAllow(final int reason) {
            ...
        }

        @Override
        public void applicationError(final int errorCode) {
            ...
        }
    }
}
