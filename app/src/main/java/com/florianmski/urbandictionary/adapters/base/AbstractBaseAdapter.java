package com.florianmski.urbandictionary.adapters.base;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBaseAdapter<E> extends BaseAdapter implements AbstractAdapterInterface<E>
{
    protected Context context;
    protected List<E> data;

    public AbstractBaseAdapter(Context context, List<E> data)
    {
        this.context = context;
        this.data = data;
    }

    @Override
    public void refresh(List<E> data)
    {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public void reset()
    {
        this.data = new ArrayList<E>();
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position)
    {
        return data.get(position);
    }

    public E getItem2(int position)
    {
        return data.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }
}
