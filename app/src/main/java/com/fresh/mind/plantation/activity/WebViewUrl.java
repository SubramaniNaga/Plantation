package com.fresh.mind.plantation.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.customized.CustomTextView;

/**
 * Created by AND I5 on 13-01-2017.
 */
public class WebViewUrl extends Activity {

    private static final String TAG = "WebViewwww";
    private WebView webview;
    private ProgressDialog progressBar;
    private CustomTextView mUrlTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            // Do something for lollipop and above versions
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(getApplication(), R.color.colorPrimaryDark));
            //window.setNavigationBarColor(ContextCompat.getColor(getApplication(), R.color.colorPrimary));
        }
        setContentView(R.layout.ac_webviewurl);


        webview = (WebView) findViewById(R.id.view);
        mUrlTitle = (CustomTextView) findViewById(R.id.mUrlTitle);
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        settings.setDisplayZoomControls(true);
        settings.setSupportZoom(true);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        mUrlTitle.setText("" + url);
        progressBar = ProgressDialog.show(this, "Connecting", "Loading...");
        progressBar.setCancelable(true);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "Processing webview url click...");
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "Finished loading URL: " + url);
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(getApplicationContext(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
                alertDialog.setTitle("Error");
                alertDialog.setMessage(description);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alertDialog.show();
            }
        });
        webview.loadUrl(url);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            finish();
        }
    }

}
