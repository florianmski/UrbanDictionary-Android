package com.florianmski.urbandictionary.api;

import com.florianmski.urbandictionary.data.entities.Term;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public enum UrbanDictionaryClient
{
    INSTANCE;

    private UrbanDictionaryService service;

    private UrbanDictionaryClient()
    {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.urbandictionary.com/v0")
                .build();

        service = restAdapter.create(UrbanDictionaryService.class);
    }

    public UrbanDictionaryService get()
    {
        return service;
    }

    public interface UrbanDictionaryService
    {
        @GET("/define")
        Observable<Term> term(@Query("term") String term);
    }
}
