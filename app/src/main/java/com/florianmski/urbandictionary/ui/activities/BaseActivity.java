package com.florianmski.urbandictionary.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public abstract class BaseActivity extends FragmentActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public static void launchActivity(Activity a, Class<?> activityToLaunch)
    {
        launchActivity(a, activityToLaunch, null);
    }
	
	public static void launchActivity(Activity a, Class<?> activityToLaunch, Bundle args)
	{
		Intent i = new Intent(a, activityToLaunch);
		if(args != null)
			i.putExtras(args);
		a.startActivity(i);
	}
}
