package io.github.pedrofraca.spotifystreamer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import java.util.HashMap;

import io.github.pedrofraca.spotifystreamer.R;
import io.github.pedrofraca.spotifystreamer.model.ItemToDraw;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class TopSongsActivity extends ArtistsActivity  {

    public static final String ATTR_ARTIST_NAME = "ATTR_ARTIST_NAME";
    public static final String ATTR_ARTIST_ID = "ATTR_ARTIST_ID";
    private String mArtistName;

    /**
     * Method to launch the activity.
     * @param context Activity to launch this new activity.
     * @param artistName String with the name of the artist to show.
     * @param artistId Id to do the search for the albums.
     */
    public static void launch(Context context, String artistName,String artistId){
        Intent intent = new Intent(context,TopSongsActivity.class);
        intent.putExtra(ATTR_ARTIST_NAME, artistName);
        intent.putExtra(ATTR_ARTIST_ID,artistId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArtistName=getIntent().getStringExtra(ATTR_ARTIST_NAME);
        getSupportActionBar().setSubtitle(mArtistName);
        if(savedInstanceState==null){
            showTopSongsForArtist(getIntent().getStringExtra(ATTR_ARTIST_ID));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //we don't want to add an options menu
        return true;
    }

    private void showTopSongsForArtist(String artistId){
        SpotifyApi api = new SpotifyApi();
        SpotifyService spotify = api.getService();
        HashMap<String, Object> params = new HashMap<String,Object>();
        params.put("country","es");
        mFragment.showProgressBar();
        spotify.getArtistTopTrack(artistId, params, new Callback<Tracks>() {
            @Override
            public void success(Tracks tracks, Response response) {
                mDataset.clear();
                for (Track track: tracks.tracks) {
                    String imgUrl = track.album.images.size()>0?track.album.images.get(0).url:getString(R.string.url_default_img);;
                    mDataset.add(new ItemToDraw(track.name,imgUrl,track.id,track.album.name));
                }
                mFragment.dataset(mDataset)
                        .emtyDatasetMessage(getString(R.string.no_song_found, mArtistName))
                        .refresh();
            }

            @Override
            public void failure(RetrofitError error) {
               mFragment.showMessage(error.getMessage());
            }
        });
    }

    @Override
    public boolean allowClicksOnItems() {
        return false;
    }
}
