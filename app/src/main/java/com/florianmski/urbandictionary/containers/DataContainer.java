package com.florianmski.urbandictionary.containers;

import android.database.Cursor;

import java.util.Collections;
import java.util.List;

public abstract class DataContainer<T> extends Container<T> implements ContainerInterface.DataContainerInterface<T>
{	
	public DataContainer()
	{
		init();
	}
	
	public static class CursorDataContainer extends DataContainer<Cursor>
	{
		@Override
		public boolean isEmpty() 
		{
			return data == null || data.getCount() == 0;
		}

		@Override
		public void init() 
		{
			// nothing to do
		}

		@Override
		public void clear() 
		{
			data = null;
		}
    }
	
	public static class ListDataContainer<E> extends DataContainer<List<E>>
	{
		public ListDataContainer()
		{
			super();
		}
		
		@Override
		public boolean isEmpty() 
		{
			return data.isEmpty();
		}

		@Override
		public void init() 
		{
			data = Collections.emptyList();
		}

		@Override
		public void clear() 
		{
			data.clear();
		}
	}
}
