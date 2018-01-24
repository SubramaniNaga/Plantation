package com.fresh.mind.plantation.receiver;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;

import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.application.MyApplication;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.fragment.menu_page.SplashScreen;
import com.fresh.mind.plantation.sqlite.server.VerifyDetails;

import static com.fresh.mind.plantation.R.anim.slide_up;
import static com.fresh.mind.plantation.R.id.dialogAnimation;
import static com.fresh.mind.plantation.activity.SplashActivity.mEnglish;
import static com.fresh.mind.plantation.activity.SplashActivity.mTamil;

/**
 * Created by AND I5 on 26-04-2017.
 */

public class NetworkChechReceiver extends BroadcastReceiver {
    VerifyDetails verifyDetails;

    @Override
    public void onReceive(final Context context, Intent intent) {
        try {
            verifyDetails = new VerifyDetails(context);
            boolean isVisible = MyApplication.isActivityVisible();
            // Check ifactivity  is   visible   or not
            Log.i("Activity is Visible ", "Is activity visible : " + isVisible);

            // If it is visible then trigger the task else do nothing
            if (isVisible == true) {
                boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
                String reason = intent.getStringExtra(ConnectivityManager.EXTRA_REASON);
                boolean isFailover = intent.getBooleanExtra(ConnectivityManager.EXTRA_IS_FAILOVER, false);

                NetworkInfo currentNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                NetworkInfo otherNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO);
                //Log.d("currentNetworkInfo", "" + currentNetworkInfo + "  " + otherNetworkInfo);
                if (currentNetworkInfo == null) {
                    //Toast.makeText(context, "Not Connected", Toast.LENGTH_SHORT).show();
                    //SplashScreen.dialog.dismiss();
                    new SplashScreen().changeTextStatus(false);

                } else {
                    new SplashScreen().changeTextStatus(true);
                    // Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
