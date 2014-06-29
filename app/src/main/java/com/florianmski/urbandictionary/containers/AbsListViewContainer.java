package com.florianmski.urbandictionary.containers;

import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListView;

import com.florianmski.urbandictionary.R;
import com.florianmski.urbandictionary.adapters.base.AbstractBaseAdapter;

public abstract class AbsListViewContainer<E, V extends AbsListView> extends Container<V> implements ContainerInterface.ViewContainerInterface<AbstractBaseAdapter<E>, E, V>
{
    @Override
    public void setAdapter(AbstractBaseAdapter<E> adapter)
    {
        data.setAdapter(adapter);
    }

    @Override
    public AbstractBaseAdapter<E> getAdapter()
    {
        return (AbstractBaseAdapter<E>) data.getAdapter();
    }

    public static class ListViewContainer<E> extends AbsListViewContainer<E, ListView>
    {
        @Override
        public int getLayoutId()
        {
            return R.layout.view_list;
        }
    }

    public static class GridViewContainer<E> extends AbsListViewContainer<E, GridView>
    {
        @Override
        public int getLayoutId()
        {
            return R.layout.view_grid;
        }
    }
}
