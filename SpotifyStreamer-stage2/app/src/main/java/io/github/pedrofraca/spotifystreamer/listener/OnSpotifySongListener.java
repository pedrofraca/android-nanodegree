package io.github.pedrofraca.spotifystreamer.listener;

import java.util.ArrayList;

import io.github.pedrofraca.spotifystreamer.model.ItemToDraw;

/**
 * Interface to be implemented by the activity that uses the ItemListFragment.
 */
public interface OnSpotifySongListener {

    /**
     * Method executed when the user clicks on a item.
     */
    void onSongClicked(ArrayList<ItemToDraw> items, String artistName, int position);
}
