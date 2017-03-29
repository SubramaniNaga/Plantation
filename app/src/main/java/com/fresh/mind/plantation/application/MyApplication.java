package com.fresh.mind.plantation.application;

import android.app.Application;
import android.content.Context;

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
        final ACRAConfiguration config;
        try {
            config = new ConfigurationBuilder(this)
                    .build();
            ACRA.init(this, config);
        } catch (ACRAConfigurationException e) {
            e.printStackTrace();
        }


    }

}
