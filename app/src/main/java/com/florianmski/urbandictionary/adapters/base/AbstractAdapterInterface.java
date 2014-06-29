package com.florianmski.urbandictionary.adapters.base;

import java.util.List;

public interface AbstractAdapterInterface<E>
{
    public void refresh(List<E> data);
    public void reset();
}
