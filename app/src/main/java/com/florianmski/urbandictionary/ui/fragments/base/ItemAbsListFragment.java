package com.florianmski.urbandictionary.ui.fragments.base;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.florianmski.urbandictionary.adapters.base.AbstractBaseAdapter;
import com.florianmski.urbandictionary.containers.AbsListViewContainer;

import java.util.ArrayList;
import java.util.List;

public abstract class ItemAbsListFragment<E, V extends AbsListView> extends ItemGroupFragment<AbstractBaseAdapter<E>, E, List<E>, V, AbsListView.OnScrollListener> implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener
{
    protected int lastFirstVisibleItem;
    protected int lastTop;
    protected int scrollPosition;
    protected int lastHeight;

    public ItemAbsListFragment(AbsListViewContainer<E, V> viewContainer)
    {
        super(viewContainer);
        data = new ArrayList<E>();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        getAbsListView().setOnItemClickListener(this);
        getAbsListView().setOnScrollListener(this);
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

    protected abstract AbstractBaseAdapter<E> createAdapter(final List<E> items);

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        viewContainer.setAdapter(createAdapter(data));
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    public V getAbsListView()
    {
        return getGroupView();
    }

    public E getItem(int position)
    {
        return data.get(position);
    }

    protected AbstractBaseAdapter<E> getAdapter()
    {
        return viewContainer.getAdapter();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState)
    {
        for(AbsListView.OnScrollListener listener : scrollListeners)
            listener.onScrollStateChanged(view, scrollState);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {
        for(AbsListView.OnScrollListener listener : scrollListeners)
            listener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);

        // based on https://github.com/ManuelPeinado/GlassActionBar/blob/master/library/src/com/manuelpeinado/glassactionbar/ListViewScrollObserver.java
        View firstChild = view.getChildAt(0);
        if (firstChild == null)
            return;
        int top = firstChild.getTop();
        int height = firstChild.getHeight();
        int delta;
        int skipped = 0;
        if (lastFirstVisibleItem == firstVisibleItem)
            delta = lastTop - top;
        else if (firstVisibleItem > lastFirstVisibleItem)
        {
            skipped = firstVisibleItem - lastFirstVisibleItem - 1;
            delta = skipped * height + lastHeight + lastTop - top;
        }
        else
        {
            skipped = lastFirstVisibleItem - firstVisibleItem - 1;
            delta = skipped * -height + lastTop - (height + top);
        }
        boolean exact = skipped == 0;
        scrollPosition += -delta;
        onScrollUpDownChanged(-delta, scrollPosition, exact);
        lastFirstVisibleItem = firstVisibleItem;
        lastTop = top;
        lastHeight = firstChild.getHeight();
    }

    protected void onScrollUpDownChanged(int delta, int scrollPosition, boolean exact) {}
}
