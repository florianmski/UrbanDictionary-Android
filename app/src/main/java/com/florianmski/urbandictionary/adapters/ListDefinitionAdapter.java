package com.florianmski.urbandictionary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.florianmski.urbandictionary.R;
import com.florianmski.urbandictionary.adapters.base.AbstractBaseAdapter;
import com.florianmski.urbandictionary.data.entities.Definition;
import com.florianmski.urbandictionary.ui.widgets.RibbonView;

import java.util.List;

public class ListDefinitionAdapter extends AbstractBaseAdapter<Definition>
{
    public ListDefinitionAdapter(Context context, List<Definition> data)
    {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;

        if (convertView == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_definition, parent, false);
            holder.rv = (RibbonView) convertView.findViewById(R.id.ribbonView);
            holder.tvWord = (TextView) convertView.findViewById(R.id.textViewWord);
            holder.tvDefinition = (TextView) convertView.findViewById(R.id.textViewDefinition);
            holder.tvExample = (TextView) convertView.findViewById(R.id.textViewExample);
            holder.tvAuthor = (TextView) convertView.findViewById(R.id.textViewAuthor);
            holder.pbVotes = (ProgressBar) convertView.findViewById(R.id.progressBarVotes);
            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder)convertView.getTag();

        Definition d = getItem2(position);
        holder.tvWord.setText(d.getWord());
        holder.tvDefinition.setText(d.getDefinition());
        holder.tvExample.setText(d.getExample());
        holder.tvAuthor.setText("by " + d.getAuthor());

        int max = d.getThumbsUp() + d.getThumbsDown();
        holder.pbVotes.setMax(max);
        holder.pbVotes.setProgress(d.getThumbsUp());

        holder.rv.setPosition(position+1);

        return convertView;
    }

    public static class ViewHolder
    {
        public RibbonView rv;
        public TextView tvWord, tvDefinition, tvExample, tvAuthor;
        public ProgressBar pbVotes;
    }
}
