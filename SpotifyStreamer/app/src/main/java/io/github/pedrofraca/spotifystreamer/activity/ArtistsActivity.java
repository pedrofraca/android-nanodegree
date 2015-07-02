package io.github.pedrofraca.spotifystreamer.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import io.github.pedrofraca.spotifystreamer.R;
import io.github.pedrofraca.spotifystreamer.asynctask.SearchArtistsAsyncTask;
import io.github.pedrofraca.spotifystreamer.fragment.ItemListFragment;
import io.github.pedrofraca.spotifystreamer.listener.ItemListFragmentInterface;
import io.github.pedrofraca.spotifystreamer.listener.SearchArtistInterface;
import io.github.pedrofraca.spotifystreamer.model.ItemToDraw;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;


public class ArtistsActivity extends AppCompatActivity implements SearchArtistInterface,ItemListFragmentInterface {

    public static final String DATASET = "DATASET";

    protected ArrayList<ItemToDraw> mDataset = new ArrayList<ItemToDraw>();
    protected ItemListFragment mFragment;
    SearchArtistsAsyncTask searchArtistsAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragment = (ItemListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if(savedInstanceState==null){
            mFragment.showMessage(getString(R.string.start_message));
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mDataset = savedInstanceState.getParcelableArrayList(DATASET);
        if(mDataset.isEmpty()) {
            mFragment.showMessage(getString(R.string.start_message));
        } else {
            mFragment.dataset(mDataset)
                    .refresh();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(DATASET, mDataset);
        super.onSaveInstanceState(outState);
    }

    private void doSearch(final String query){
        if(query.isEmpty()) return;
        mFragment.showProgressBar();
        if(searchArtistsAsyncTask==null){
            searchArtistsAsyncTask= new SearchArtistsAsyncTask();
        } else {
            //Cancelling activity as we are going to start a new one.
            searchArtistsAsyncTask.cancel(true);
            searchArtistsAsyncTask= new SearchArtistsAsyncTask();
        }
        searchArtistsAsyncTask.setQuery(query).listenedBy(this).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                doSearch(newText);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSeachArtistSuccess(ArtistsPager artistsPager,String query) {
        mDataset.clear();
        for (Artist artist : artistsPager.artists.items) {
            String imgUrl = artist.images.size() > 0 ? artist.images.get(0).url : getString(R.string.url_default_img);
            mDataset.add(new ItemToDraw(artist.name, imgUrl, artist.id));
        }
        mFragment.dataset(mDataset)
                .emtyDatasetMessage(getString(R.string.no_results, query))
                .refresh();
    }

    @Override
    public void onSearchArtistError(Exception error) {
        mFragment.showMessage(error.getMessage());
    }

    @Override
    public boolean allowClicksOnItems() {
        return true;
    }

    @Override
    protected void onDestroy() {
        if(searchArtistsAsyncTask!=null){
            searchArtistsAsyncTask.cancel(true);
        }
        super.onDestroy();
    }
}
