package io.github.pedrofraca.spotifystreamer.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;

import io.github.pedrofraca.spotifystreamer.R;
import io.github.pedrofraca.spotifystreamer.adapter.SpotifyArtistAdapter;
import io.github.pedrofraca.spotifystreamer.adapter.SpotifySongAdapter;
import io.github.pedrofraca.spotifystreamer.asynctask.SearchArtistsAsyncTask;
import io.github.pedrofraca.spotifystreamer.fragment.ItemListFragment;
import io.github.pedrofraca.spotifystreamer.fragment.PlayerFragment;
import io.github.pedrofraca.spotifystreamer.listener.OnSpotifyArtistListener;
import io.github.pedrofraca.spotifystreamer.listener.OnSpotifySongListener;
import io.github.pedrofraca.spotifystreamer.listener.SearchArtistInterface;
import io.github.pedrofraca.spotifystreamer.model.ItemToDraw;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity implements SearchArtistInterface {

    public static final String DATASET = "DATASET";
    public static final String SONGS_DATASET = "SONG-DATASET";
    public static final String ARTIST_NAME = "ARTIST-NAME";

    public static final String SONGS_FRAGMENT = "songs-fragment";

    protected ArrayList<ItemToDraw> mDataset = new ArrayList<ItemToDraw>();
    protected ArrayList<ItemToDraw> mSongs = new ArrayList<ItemToDraw>();

    protected ItemListFragment mArtistListFragment;
    SearchArtistsAsyncTask searchArtistsAsyncTask;
    private boolean mTwoPane=false;
    protected String mArtistName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mArtistListFragment = (ItemListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (findViewById(R.id.search_detail_container) != null) {
            mTwoPane=true;
        } else {
            mTwoPane=false;
        }
        if(savedInstanceState==null){
            mArtistListFragment.showMessage(getString(R.string.start_message));
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mDataset = savedInstanceState.getParcelableArrayList(DATASET);
        mArtistName = savedInstanceState.getString(ARTIST_NAME);
        if(mDataset.isEmpty()) {
            mArtistListFragment.showMessage(getString(R.string.start_message));
        } else {
            if(this instanceof  TopSongsActivity){
                mArtistListFragment.setAdapter(new SpotifySongAdapter(mDataset,MainActivity.this,songListener,mArtistName))
                        .refresh();
            } else {
                mArtistListFragment.setAdapter(new SpotifyArtistAdapter(mDataset, MainActivity.this, artistListener))
                        .refresh();
            }

        }
        mSongs= savedInstanceState.getParcelableArrayList(SONGS_DATASET);
        //Cheking if we have songs too
        if(!mSongs.isEmpty()){
            ItemListFragment fragment = (ItemListFragment) getSupportFragmentManager().findFragmentByTag(SONGS_FRAGMENT);
            if (fragment!=null){
                fragment.setAdapter(new SpotifySongAdapter(mSongs,MainActivity.this,songListener, mArtistName))
                        .refresh();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(DATASET, mDataset);
        outState.putParcelableArrayList(SONGS_DATASET, mSongs);
        outState.putString(ARTIST_NAME, mArtistName);
        super.onSaveInstanceState(outState);
    }

    private void doSearch(final String query){
        if(query.isEmpty()) return;
        mArtistListFragment.showProgressBar();
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
        mArtistListFragment.setAdapter(new SpotifyArtistAdapter(mDataset,MainActivity.this,artistListener))
                .emtyDatasetMessage(getString(R.string.no_results, query))
                .refresh();
    }

    @Override
    public void onSearchArtistError(Exception error) {
        mArtistListFragment.showMessage(error.getMessage());
    }


    OnSpotifyArtistListener artistListener = new OnSpotifyArtistListener() {
        @Override
        public void onArtistClickListener(final ItemToDraw item) {
            mArtistName=item.getTitle();
            if(mTwoPane){
                final ItemListFragment detailFragment = new ItemListFragment();
                SpotifyApi api = new SpotifyApi();
                SpotifyService spotify = api.getService();
                HashMap<String, Object> params = new HashMap<String,Object>();
                params.put("country", "es");
                getSupportFragmentManager().beginTransaction().replace(R.id.search_detail_container, detailFragment,SONGS_FRAGMENT).commit();
                spotify.getArtistTopTrack(item.getId(), params, new Callback<Tracks>() {
                    @Override
                    public void success(Tracks tracks, Response response) {
                        mSongs.clear();
                        for (Track track : tracks.tracks) {
                            String imgUrl = track.album.images.size() > 0 ? track.album.images.get(0).url : getString(R.string.url_default_img);
                            ItemToDraw song = new ItemToDraw(track.name, imgUrl, track.id, track.album.name);
                            song.setSongURL(track.preview_url);
                            song.setDuration(track.duration_ms);
                            mSongs.add(song);
                        }
                        detailFragment.setAdapter(new SpotifySongAdapter(mSongs, MainActivity.this, songListener, item.getTitle()))
                                .emtyDatasetMessage(getString(R.string.no_song_found, item.getTitle()))
                                .refresh();

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        detailFragment.showMessage(error.getMessage());
                    }
                });
            } else {
                TopSongsActivity.launch(MainActivity.this, item);
            }
        }
    };

    OnSpotifySongListener songListener = new OnSpotifySongListener() {
        @Override
        public void onSongClicked(ArrayList<ItemToDraw> items, String artistName, int position) {
            if(mTwoPane){
                DialogFragment newFragment = PlayerFragment.newInstance(items,artistName,position);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                newFragment.show(ft, "dialog");
            } else {
                PlayerActivity.launch(MainActivity.this,items,mArtistName,position);
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(searchArtistsAsyncTask!=null){
            searchArtistsAsyncTask.cancel(true);
        }
    }
}
