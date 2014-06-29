package com.florianmski.urbandictionary;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

public class UrbanDictionaryApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Crashlytics.start(this);
    }
}
