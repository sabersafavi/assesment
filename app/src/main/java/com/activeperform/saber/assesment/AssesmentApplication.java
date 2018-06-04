package com.activeperform.saber.assesment;

import android.app.Application;

public class AssesmentApplication extends Application {

    static AssesmentApplication instance;

    public static AssesmentApplication getInstance() {
        return instance;
    }

    public void setInstance(AssesmentApplication instance) {
        this.instance = instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}