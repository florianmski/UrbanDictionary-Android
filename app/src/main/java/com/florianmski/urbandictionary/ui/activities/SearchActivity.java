package com.florianmski.urbandictionary.ui.activities;

import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.florianmski.urbandictionary.R;
import com.florianmski.urbandictionary.ui.fragments.TermFragment;

public class SearchActivity extends TranslucentActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Crashlytics.start(this);
        setContentView(R.layout.activity_search);

        if(savedInstanceState == null)
        {
            TermFragment f = TermFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_term, f).commit();
        }
    }
}
