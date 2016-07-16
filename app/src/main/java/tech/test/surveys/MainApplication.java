package tech.test.surveys;

import android.app.Application;

import tech.test.surveys.util.Contextor;

/**
 * Created by GOLF on 7/16/2016.
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize thing(s) here
        Contextor.getInstance().init(getApplicationContext());
    }
}