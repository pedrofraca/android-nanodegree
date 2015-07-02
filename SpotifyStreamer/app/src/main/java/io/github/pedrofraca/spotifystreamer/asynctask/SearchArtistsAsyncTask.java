package io.github.pedrofraca.spotifystreamer.asynctask;

import android.os.AsyncTask;

import io.github.pedrofraca.spotifystreamer.listener.SearchArtistInterface;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.RetrofitError;


public class SearchArtistsAsyncTask extends AsyncTask<Void,Void,Object>{

    private String mQuery;
    private SearchArtistInterface mListener;

    @Override
    protected Object doInBackground(Void... voids) {
        try {
            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            return spotify.searchArtists(mQuery);
        } catch (RetrofitError error){
            return error;
        }
    }

    @Override
    protected void onPostExecute(Object aVoid) {
        if(mListener==null) return;
        if(aVoid instanceof ArtistsPager){
            mListener.onSeachArtistSuccess((ArtistsPager) aVoid, mQuery);
        } else {
            mListener.onSearchArtistError((Exception) aVoid);
        }

    }

    public SearchArtistsAsyncTask setQuery(String query) {
        mQuery= query;
        return this;
    }

    public SearchArtistsAsyncTask listenedBy(SearchArtistInterface listener){
        mListener=listener;
        return this;
    }
}
