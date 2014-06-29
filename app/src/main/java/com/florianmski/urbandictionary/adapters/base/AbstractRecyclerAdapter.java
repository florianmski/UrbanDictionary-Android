package com.florianmski.urbandictionary.adapters.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRecyclerAdapter<E, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements AbstractAdapterInterface<E>
{
    protected Context context;
    protected List<E> data;

    public AbstractRecyclerAdapter(Context context, List<E> data)
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

    public E getItem(int position)
    {
        return data.get(position);
    }

    @Override
    public int getItemCount()
    {
        return data == null ? 0 : data.size();
    }
}
