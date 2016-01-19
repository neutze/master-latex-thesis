package me.neutze.licensetest;


import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.samsung.zirconia.LicenseCheckListener;
import com.samsung.zirconia.Zirconia;

/**
 * http://developer.samsung.com/resources/zirconia
 **/

public class MainActivity extends AppCompatActivity {

    private Handler mHandler;
    private TextView mStatusText;
    private Button mCheckLicenseButton;
    private Toolbar mToolbar;

    @Override
    public void onCreate(final Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_basic);
        mStatusText = (TextView) findViewById(R.id.text);
        mCheckLicenseButton = (Button) findViewById(R.id.button);

        initToolbar();

        mCheckLicenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler = new Handler();
                doCheck();
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
    }

    private void doCheck() {
        mCheckLicenseButton.setEnabled(false);
        setProgressBarIndeterminateVisibility(true);
        mStatusText.setText(R.string.checking_license);

        final Zirconia zirconia = new Zirconia(this);
        final MyLicenseCheckListener listener = new MyLicenseCheckListener();
        listener.mHandler = mHandler;
        listener.mTextView = mStatusText;
        zirconia.setLicenseCheckListener(listener);
        zirconia.checkLicense(false, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    private class MyLicenseCheckListener implements LicenseCheckListener {

        Handler mHandler;
        TextView mTextView;

        /* In case the results of license verification and inquiry are normal */
        @Override
        public void licenseCheckedAsValid() {
            mHandler.post(new Runnable() {
                public void run() {
                  ...
                }
            });
        }

        @Override
        public void licenseCheckedAsInvalid() {
            mHandler.post(new Runnable() {
                public void run() {
                  ...
                }
            });
        }
    }
}
