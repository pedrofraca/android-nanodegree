package io.github.pedrofraca.spotifystreamer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import io.github.pedrofraca.spotifystreamer.R;
import io.github.pedrofraca.spotifystreamer.fragment.PlayerFragment;
import io.github.pedrofraca.spotifystreamer.model.ItemToDraw;

public class PlayerActivity extends AppCompatActivity {

    private static final String SONG_ATTR = "song_attr";
    private static final String SONG_ATTR_POSITION = "song_attr_position";
    private static final String ARTIST_NAME_ATTR = "artist_name_attr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        FragmentManager fragmentManager = getSupportFragmentManager();

        if(savedInstanceState==null){
            ArrayList<ItemToDraw> songs = getIntent().getExtras().getParcelableArrayList(SONG_ATTR);
            int songPosition = getIntent().getExtras().getInt(SONG_ATTR_POSITION);
            String artistName = getIntent().getExtras().getString(ARTIST_NAME_ATTR);
            PlayerFragment playerFragment = PlayerFragment.newInstance(songs,artistName,songPosition);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.activity_player_fragment_container, playerFragment).commit();
        }
    }

    public static void launch(Activity context, ArrayList<ItemToDraw> items, String artistName, int songPosition) {
        Intent intent = new Intent(context,PlayerActivity.class);
        intent.putParcelableArrayListExtra(SONG_ATTR, items);
        intent.putExtra(ARTIST_NAME_ATTR, artistName);
        intent.putExtra(SONG_ATTR_POSITION,songPosition);
        context.startActivity(intent);
    }


}
