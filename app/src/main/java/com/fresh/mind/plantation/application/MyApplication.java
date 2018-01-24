package com.fresh.mind.plantation.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;
import org.acra.config.ACRAConfiguration;
import org.acra.config.ACRAConfigurationException;
import org.acra.config.ConfigurationBuilder;

/**
 * Created by AND I5 on 23-03-2017.
 */
@ReportsCrashes(
        mailTo = "subramani@kambaa.com"
)
public class MyApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(getBaseContext());
        final ACRAConfiguration config;
        try {
            config = new ConfigurationBuilder(this)
                    .build();
            ACRA.init(this, config);
        } catch (ACRAConfigurationException e) {
            e.printStackTrace();
        }


    }


    public static boolean activityVisible; // Variable that will check the
    // current activity state

    public static boolean isActivityVisible() {
        return activityVisible; // return true or false
    }

    public static void activityResumed() {
        activityVisible = true;// this will set true when activity resumed

    }

    public static void activityPaused() {
        activityVisible = false;// this will set false when activity paused

    }


}
