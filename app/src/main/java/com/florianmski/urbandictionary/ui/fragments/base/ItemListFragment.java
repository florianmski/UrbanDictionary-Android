package com.florianmski.urbandictionary.ui.fragments.base;

import android.widget.ListView;

import com.florianmski.urbandictionary.containers.AbsListViewContainer;

public abstract class ItemListFragment<E> extends ItemAbsListFragment<E, ListView>
{
    public ItemListFragment()
    {
        super(new AbsListViewContainer.ListViewContainer<E>());
    }
}
