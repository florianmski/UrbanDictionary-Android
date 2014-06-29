package com.florianmski.urbandictionary.ui.fragments;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;

import com.florianmski.urbandictionary.R;
import com.florianmski.urbandictionary.adapters.ListDefinitionAdapter;
import com.florianmski.urbandictionary.adapters.base.AbstractBaseAdapter;
import com.florianmski.urbandictionary.api.UrbanDictionaryClient;
import com.florianmski.urbandictionary.data.entities.Definition;
import com.florianmski.urbandictionary.data.entities.Term;
import com.florianmski.urbandictionary.ui.fragments.base.ItemListFragment;

import java.util.List;

import retrofit.RetrofitError;
import rx.Observable;
import rx.functions.Func1;

public class TermFragment extends ItemListFragment<Definition> implements SearchView.OnQueryTextListener
{
    private final static String BUNDLE_TERM = "term";
    private final static int PADDING = 20;

    private SearchView searchView;

    private String term;

    public static TermFragment newInstance()
    {
        TermFragment f = new TermFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if(savedInstanceState == null)
            setRefreshOnStart(false);
        else
            term = savedInstanceState.getString(BUNDLE_TERM);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        getGroupView().setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        getGroupView().setDividerHeight(PADDING);
        getGroupView().setPadding(PADDING*2, PADDING*2, PADDING*2, PADDING*2);
        getGroupView().setClipToPadding(false);
    }

    @Override
    protected AbstractBaseAdapter<Definition> createAdapter(List<Definition> items)
    {
        return new ListDefinitionAdapter(getActivity(), items);
    }

    @Override
    protected Observable<List<Definition>> createObservable()
    {
        return UrbanDictionaryClient.INSTANCE.get().term(term).map(new Func1<Term, List<Definition>>()
        {
            @Override
            public List<Definition> call(Term term)
            {
                return term.getDefinitions();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);

        // create the search view
        searchView = new SearchView(getActivity().getActionBar().getThemedContext());
        searchView.setQueryHint("Look up any word!");
        searchView.setOnQueryTextListener(this);

        final MenuItem searchMenuItem = menu.add(Menu.NONE, R.id.menu_search, Menu.NONE, getString(android.R.string.search_go));
        searchMenuItem
                .setIcon(R.drawable.ic_action_search)
                .setActionView(searchView)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

        // collapse and clear the query as soon as searchView lose focus
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean queryTextFocused)
            {
                if (!queryTextFocused)
                    searchMenuItem.collapseActionView();
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                searchView.setQuery(term, false);
            }
        });

        if(term == null)
            searchMenuItem.expandActionView();
    }

    @Override
    protected String getEmptyText()
    {
        return "No result for \"" + term + "\"";
    }

    @Override
    protected String getErrorText(Throwable t)
    {
        if(t instanceof RetrofitError)
        {
            if(((RetrofitError) t).isNetworkError())
                return "No interwebz :(";
        }

        return super.getErrorText(t);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        outState.putString(BUNDLE_TERM, term);
    }

    @Override
    public boolean onQueryTextSubmit(String newText)
    {
        term = newText;
        refresh();

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
        return true;
    }
}
