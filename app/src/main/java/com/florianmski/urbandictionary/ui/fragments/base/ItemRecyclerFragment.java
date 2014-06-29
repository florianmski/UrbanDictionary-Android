package com.florianmski.urbandictionary.ui.fragments.base;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.florianmski.urbandictionary.adapters.base.AbstractRecyclerAdapter;
import com.florianmski.urbandictionary.containers.RecyclerViewContainer;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemRecyclerFragment<E> extends ItemGroupFragment<AbstractRecyclerAdapter<E, ?>, E, List<E>, RecyclerView, RecyclerView.OnScrollListener> implements RecyclerView.OnScrollListener
{
    public ItemRecyclerFragment()
    {
        super(new RecyclerViewContainer<E>());
        data = new ArrayList<E>();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        getGroupView().setOnScrollListener(this);
    }

    @Override
    protected void refreshView(List<E> data)
    {
        getAdapter().refresh(data);
    }

    @Override
    protected boolean isEmpty(List<E> data)
    {
        return data.isEmpty();
    }

    protected abstract AbstractRecyclerAdapter<E, ?> createAdapter(final List<E> items);

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        getGroupView().setLayoutManager(getLayoutManager());
        viewContainer.setAdapter(createAdapter(data));
    }

    public E getItem(int position)
    {
        return data.get(position);
    }

    protected AbstractRecyclerAdapter<E, ?> getAdapter()
    {
        return viewContainer.getAdapter();
    }

    protected RecyclerView.LayoutManager getLayoutManager()
    {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        return llm;
    }

    @Override
    public void onScrollStateChanged(int i)
    {

    }

    @Override
    public void onScrolled(int i, int i2)
    {

    }
}
