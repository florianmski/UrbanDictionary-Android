package com.florianmski.urbandictionary.ui.fragments.base;

public interface ScrollListenerProvider<S>
{
    public void addScrollListener(S listener);
    public void removeScrollListener(S listener);
}
