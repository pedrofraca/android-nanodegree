package io.github.pedrofraca.spotifystreamer.listener;

import io.github.pedrofraca.spotifystreamer.model.ItemToDraw;

/**
 * Interface to be implemented by the activity that uses the ItemListFragment.
 */
public interface OnSpotifyArtistListener {
    /**
     * Method executed when the user clicks on a item.
     */
    void onArtistClickListener(ItemToDraw item);
}
