package com.florianmski.urbandictionary.ui.activities;

import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.florianmski.urbandictionary.R;
import com.florianmski.urbandictionary.ui.widgets.DrawInsetsFrameLayout;

public class TranslucentActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID)
    {
        // if we can, do the translucent thing, if we don't do nothing
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            final View v = LayoutInflater.from(this).inflate(layoutResID, null);
            FrameLayout fl = new FrameLayout(this);
            DrawInsetsFrameLayout difl = new DrawInsetsFrameLayout(this);
            difl.setInsetBackground(new ColorDrawable(getResources().getColor(R.color.primaryDark)));
            difl.setOnInsetsCallback(new DrawInsetsFrameLayout.OnInsetsCallback()
            {
                @Override
                public void onInsetsChanged(Rect insets)
                {
                    v.setPadding(insets.left, insets.top, insets.right, insets.bottom);
                }
            });

            fl.addView(v);
            fl.addView(difl);
            setContentView(fl);
        }
        else
            super.setContentView(layoutResID);
    }
}
