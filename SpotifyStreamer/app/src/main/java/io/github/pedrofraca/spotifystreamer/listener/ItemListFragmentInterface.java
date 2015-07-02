package io.github.pedrofraca.spotifystreamer.listener;

/**
 * Interface to be implemented by the activity that uses the ItemListFragment.
 */
public interface ItemListFragmentInterface {
    /**
     * Method to tell to the fragment if the items on the recycler view are clickable.
     * @return true if the item should be clickable, false if not.
     */
    boolean allowClicksOnItems();
}
