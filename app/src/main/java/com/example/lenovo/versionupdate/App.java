package com.example.lenovo.versionupdate;

import android.app.Application;

import org.xutils.x;

/**
 * Created by lenovo on 2017/9/11.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.setDebug(true);
        x.Ext.init(this);
    }
}
