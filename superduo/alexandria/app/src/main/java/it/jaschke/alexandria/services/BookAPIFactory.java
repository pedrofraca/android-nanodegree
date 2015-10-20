package it.jaschke.alexandria.services;

import retrofit.RestAdapter;

/**
 * Created by pedrofraca on 19/10/15.
 */
public class BookAPIFactory {
    public BookAPI getBookAPIService(){
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("https://www.googleapis.com")
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();

            return restAdapter.create(BookAPI.class);
        }
    }
