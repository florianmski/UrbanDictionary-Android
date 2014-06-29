package com.florianmski.urbandictionary.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.florianmski.urbandictionary.R;
import com.florianmski.urbandictionary.adapters.base.AbstractRecyclerAdapter;
import com.florianmski.urbandictionary.data.entities.Definition;
import com.florianmski.urbandictionary.ui.widgets.RibbonView;

import java.util.List;

public class ListDefinitionRecyclerAdapter extends AbstractRecyclerAdapter<Definition, ListDefinitionRecyclerAdapter.ViewHolder>
{
    public ListDefinitionRecyclerAdapter(Context context, List<Definition> data)
    {
        super(context, data);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_definition, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(20, 20, 20, 20);
        cv.setLayoutParams(layoutParams);
        ViewHolder vh = new ViewHolder(cv);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Definition d = getItem(position);
        holder.tvWord.setText(d.getWord());
        holder.tvDefinition.setText(d.getDefinition());
        holder.tvExample.setText(d.getExample());
        holder.tvAuthor.setText("by " + d.getAuthor());

        holder.pbVotes.setMax(d.getThumbsUp() + d.getThumbsDown());
        holder.pbVotes.setProgress(d.getThumbsUp());

        holder.rv.setPosition(position+1);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public CardView cv;
        public RibbonView rv;
        public TextView tvWord, tvDefinition, tvExample, tvAuthor;
        public ProgressBar pbVotes;

        public ViewHolder(CardView v)
        {
            super(v);

            cv = v;
            rv = (RibbonView) cv.findViewById(R.id.ribbonView);
            tvWord = (TextView) cv.findViewById(R.id.textViewWord);
            tvDefinition = (TextView) cv.findViewById(R.id.textViewDefinition);
            tvExample = (TextView) cv.findViewById(R.id.textViewExample);
            tvAuthor = (TextView) cv.findViewById(R.id.textViewAuthor);
            pbVotes = (ProgressBar) cv.findViewById(R.id.progressBarVotes);
        }
    }
}
