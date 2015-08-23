package io.github.pedrofraca.spotifystreamer.listener;

import kaaes.spotify.webapi.android.models.ArtistsPager;

/**
 * Interface that should be implemented  by the object which wants to listen for the result/error of
 * {@link io.github.pedrofraca.spotifystreamer.asynctask.SearchArtistsAsyncTask}.
 */
public interface SearchArtistInterface {
    void onSeachArtistSuccess(ArtistsPager artistsPager,String query);
    void onSearchArtistError(Exception error);
}
