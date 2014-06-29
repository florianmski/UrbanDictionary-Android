package com.florianmski.urbandictionary.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment
{
    public void launchActivity(Class<?> activityToLaunch, Bundle args)
    {
        Intent i = new Intent(getActivity(), activityToLaunch);
        if(args != null)
            i.putExtras(args);
        startActivity(i);
    }

    public void launchActivity(Class<?> activityToLaunch)
    {
        launchActivity(activityToLaunch, null);
    }
}
