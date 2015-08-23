package io.github.pedrofraca.spotifystreamer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;

import java.util.ArrayList;
import java.util.HashMap;

import io.github.pedrofraca.spotifystreamer.R;
import io.github.pedrofraca.spotifystreamer.adapter.SpotifySongAdapter;
import io.github.pedrofraca.spotifystreamer.listener.OnSpotifySongListener;
import io.github.pedrofraca.spotifystreamer.model.ItemToDraw;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class TopSongsActivity extends MainActivity {

    public static final String ATTR_ARTIST_NAME = "ATTR_ARTIST_NAME";
    public static final String ATTR_ARTIST_ID = "ATTR_ARTIST_ID";
    private String mArtistId;

    /**
     * Method to launch the activity.
     * @param context Activity to launch this new activity.
     * @param item Item element with the information to show
     */
    public static void launch(Context context, ItemToDraw item){
        Intent intent = new Intent(context,TopSongsActivity.class);
        intent.putExtra(ATTR_ARTIST_NAME, item.getTitle().toString());
        intent.putExtra(ATTR_ARTIST_ID,item.getId());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArtistName=getIntent().getStringExtra(ATTR_ARTIST_NAME);
        getSupportActionBar().setSubtitle(mArtistName);
        if(savedInstanceState==null){
            mArtistId = getIntent().getStringExtra(ATTR_ARTIST_ID);
            if(mArtistId!=null){
                showTopSongsForArtist(mArtistId);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mArtistId=savedInstanceState.getString(ATTR_ARTIST_ID);
        mArtistName=savedInstanceState.getString(ATTR_ARTIST_NAME);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString(ATTR_ARTIST_ID,mArtistId);
        outState.putString(ATTR_ARTIST_NAME,mArtistName);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void showTopSongsForArtist(String artistId){
        SpotifyApi api = new SpotifyApi();
        SpotifyService spotify = api.getService();
        HashMap<String, Object> params = new HashMap<String,Object>();
        params.put("country","es");
        mArtistListFragment.showProgressBar();
        spotify.getArtistTopTrack(artistId, params, new Callback<Tracks>() {
            @Override
            public void success(Tracks tracks, Response response) {
                mDataset.clear();
                for (Track track: tracks.tracks) {
                    String imgUrl = track.album.images.size()>0?track.album.images.get(0).url:getString(R.string.url_default_img);
                    ItemToDraw song = new ItemToDraw(track.name,imgUrl,track.id,track.album.name);
                    song.setSongURL(track.preview_url);
                    song.setDuration(track.duration_ms);
                    mDataset.add(song);
                }
                mArtistListFragment.setAdapter(new SpotifySongAdapter(mDataset,TopSongsActivity.this,songListener, mArtistName))
                        .emtyDatasetMessage(getString(R.string.no_song_found, mArtistName))
                        .refresh();
            }

            @Override
            public void failure(RetrofitError error) {
               mArtistListFragment.showMessage(error.getMessage());
            }
        });
    }

    OnSpotifySongListener songListener = new OnSpotifySongListener() {
        @Override
        public void onSongClicked(ArrayList<ItemToDraw> items, String artistName, int position) {
            PlayerActivity.launch(TopSongsActivity.this,items, TopSongsActivity.this.mArtistName,position);
        }
    };

}
