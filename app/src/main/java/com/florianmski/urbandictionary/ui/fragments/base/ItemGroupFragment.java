package com.florianmski.urbandictionary.ui.fragments.base;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.florianmski.urbandictionary.R;
import com.florianmski.urbandictionary.adapters.base.AbstractAdapterInterface;
import com.florianmski.urbandictionary.containers.ContainerInterface;
import com.florianmski.urbandictionary.ui.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class ItemGroupFragment<A extends AbstractAdapterInterface<T>, T, E, V extends ViewGroup, S> extends BaseFragment implements Observer<E>, ScrollListenerProvider<S>
{
    protected boolean refreshOnStart = true;
    protected E data;
    protected ContainerInterface.ViewContainerInterface<A, T, V> viewContainer;
    protected List<S> scrollListeners = new ArrayList<S>();

    protected TextView emptyView;
    protected TextView errorView;
    protected ProgressBar progressBar;

    protected Subscription subscription;

    public ItemGroupFragment(ContainerInterface.ViewContainerInterface<A, T, V> viewContainer)
    {
        this.viewContainer = viewContainer;
    }

    protected abstract Observable<E> createObservable();
    protected abstract void refreshView(E data);
    protected abstract boolean isEmpty(E data);

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        if(subscription != null)
            subscription.unsubscribe();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        if(refreshOnStart)
            refresh();
    }

    protected View getCustomLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_item_group, container, false);
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = getCustomLayout(inflater, container, savedInstanceState);
        ViewStub vs = (ViewStub) view.findViewById(R.id.viewStub);
        vs.setLayoutResource(viewContainer.getLayoutId());
        vs.inflate();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        viewContainer.set((V) view.findViewById(android.R.id.list));
        progressBar = (ProgressBar) view.findViewById(R.id.pb_loading);
        emptyView = (TextView) view.findViewById(android.R.id.empty);
        errorView = (TextView) view.findViewById(R.id.error);

        errorView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                refresh();
            }
        });
    }

    public void refresh()
    {
        // avoid flickering of the progressbar when we get the results almost immediately
        showProgressBar(new SimpleAnimatorListener()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                subscription = createObservable()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(ItemGroupFragment.this);
            }
        });
    }

    public V getGroupView()
    {
        return viewContainer.get();
    }

    public E getData()
    {
        return data;
    }

    protected String getEmptyText()
    {
        return "Nothing to see here";
    }

    protected String getErrorText(Throwable t)
    {
        return t == null ? "Unknown Error" : t.getMessage();
    }

    protected void showProgressBar(Animator.AnimatorListener listener)
    {
        show(progressBar, listener, viewContainer.get(), emptyView, errorView);
    }

    protected void showView()
    {
        show(viewContainer.get(), emptyView, errorView, progressBar);
    }

    protected void showEmptyView()
    {
        emptyView.setText(getEmptyText());
        show(emptyView, viewContainer.get(), errorView, progressBar);
    }

    protected void showErrorView(Throwable t)
    {
        errorView.setText(getErrorText(t) + "\nTap to retry");
        show(errorView, viewContainer.get(), emptyView, progressBar);
    }

    private void show(View viewToShow, View... viewsToHide)
    {
        show(viewToShow, null, viewsToHide);
    }

    private void show(View viewToShow, Animator.AnimatorListener listener, View... viewsToHide)
    {
        // if the view is already visible, nothing to do!
        if(viewToShow.getVisibility() == View.VISIBLE)
            return;

        AnimatorSet fade = new AnimatorSet();
        if(viewsToHide.length == 0)
            fade.play(changeVisibility(View.VISIBLE, viewToShow));
        else
        {
            AnimatorSet.Builder builder = fade.play(changeVisibility(View.GONE, viewsToHide[0]));
            if(viewsToHide.length > 1)
            {
                for(int i = 1; i < viewsToHide.length; i++)
                    builder.with(changeVisibility(View.GONE, viewsToHide[i]));
            }

            builder.before(changeVisibility(View.VISIBLE, viewToShow));
        }

        if(listener != null)
            fade.addListener(listener);

        fade.start();
    }

    private Animator changeVisibility(final int visibility, final View v)
    {
        float startAlpha = visibility == View.VISIBLE ? 0f : 1f;
        float endAlpha = visibility == View.VISIBLE ? 1f : 0f;

        ValueAnimator fadeAnim = ObjectAnimator.ofFloat(v, "alpha", startAlpha, endAlpha);
        fadeAnim.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {
                if(visibility == View.VISIBLE)
                    v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                if(visibility != View.VISIBLE)
                    v.setVisibility(visibility);
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        return fadeAnim;
    }

    protected void setRefreshOnStart(boolean refreshOnStart)
    {
        this.refreshOnStart = refreshOnStart;
    }

    @Override
    public void onNext(E item)
    {
        data = item;

        if(!isEmpty(item))
        {
            refreshView(item);
            showView();
        }
        else
            showEmptyView();
    }

    @Override
    public void onCompleted() {}

    @Override
    public void onError(Throwable throwable)
    {
        Log.e("ItemGroupFragment", "onError", throwable);
        showErrorView(throwable);
    }

    @Override
    public void addScrollListener(S listener)
    {
        scrollListeners.add(listener);
    }

    @Override
    public void removeScrollListener(S listener)
    {
        scrollListeners.remove(listener);
    }

    private abstract class SimpleAnimatorListener implements Animator.AnimatorListener
    {
        @Override
        public void onAnimationStart(Animator animation) {}

        @Override
        public void onAnimationCancel(Animator animation) {}

        @Override
        public void onAnimationRepeat(Animator animation) {}
    }
}
