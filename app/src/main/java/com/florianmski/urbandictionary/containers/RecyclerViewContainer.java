package com.florianmski.urbandictionary.containers;

import android.support.v7.widget.RecyclerView;

import com.florianmski.urbandictionary.R;
import com.florianmski.urbandictionary.adapters.base.AbstractRecyclerAdapter;

public class RecyclerViewContainer<E> extends Container<RecyclerView> implements ContainerInterface.ViewContainerInterface<AbstractRecyclerAdapter<E, ?>, E, RecyclerView>
{
    @Override
    public void setAdapter(AbstractRecyclerAdapter<E, ?> adapter)
    {
        data.setAdapter(adapter);
    }

    @Override
    public AbstractRecyclerAdapter<E, ?> getAdapter()
    {
        return (AbstractRecyclerAdapter<E, ?>) data.getAdapter();
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.view_recycler;
    }
}
