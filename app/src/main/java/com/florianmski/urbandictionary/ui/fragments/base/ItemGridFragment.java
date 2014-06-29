package com.florianmski.urbandictionary.ui.fragments.base;

import android.widget.GridView;

import com.florianmski.urbandictionary.containers.AbsListViewContainer;

public abstract class ItemGridFragment<E> extends ItemAbsListFragment<E, GridView>
{
    public ItemGridFragment()
    {
        super(new AbsListViewContainer.GridViewContainer<E>());
    }
}
