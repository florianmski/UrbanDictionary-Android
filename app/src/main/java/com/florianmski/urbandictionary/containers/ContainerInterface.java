package com.florianmski.urbandictionary.containers;

import com.florianmski.urbandictionary.adapters.base.AbstractAdapterInterface;

public interface ContainerInterface<T>
{
    public T get();
    public void set(T data);

    public interface DataContainerInterface<T> extends ContainerInterface<T>
    {
        public boolean isEmpty();
        public void init();
        public void clear();
    }

    public interface ViewContainerInterface<A extends AbstractAdapterInterface<E>, E, V> extends ContainerInterface<V>
    {
        public void setAdapter(A adapter);
        public A getAdapter();
        public int getLayoutId();
    }
}
