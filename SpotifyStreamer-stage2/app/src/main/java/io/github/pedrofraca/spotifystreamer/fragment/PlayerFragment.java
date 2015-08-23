package io.github.pedrofraca.spotifystreamer.fragment;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import io.github.pedrofraca.spotifystreamer.R;
import io.github.pedrofraca.spotifystreamer.model.ItemToDraw;
import io.github.pedrofraca.spotifystreamer.service.PlayerService;

public class PlayerFragment extends DialogFragment {

    private static final String SONG_EXTRA = "song_extra";
    private static final String SONG_ARTIST_NAME = "song_artist_name";
    private static final String SONG_ARTIST_POSITION = "song_artist_position";

    private ImageView mSongArt;
    private TextView mArtistName;
    private TextView mAlbumName;
    private TextView mSongName;
    private SeekBar mSeekBar;
    private Button mPlayButton;
    private Button mNextButton;
    private Button mPreviousButton;
    private String mArtistNameString;
    private View mProgessBar;
    private boolean mPlaying;
    public int mCurrentSongPosition;
    private ArrayList<ItemToDraw> mSongs;
    private PlayerService mPlayerService;
    private Timer mTimer = new Timer();
    private boolean mStopped;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_player, container, false);

        mSongArt = (ImageView) rootView.findViewById(R.id.fragment_player_album_image);
        mArtistName = (TextView) rootView.findViewById(R.id.fragment_player_artist_name);
        mAlbumName = (TextView) rootView.findViewById(R.id.fragment_player_album_name);
        mSongName = (TextView) rootView.findViewById(R.id.fragment_player_song_name);
        mSeekBar = (SeekBar) rootView.findViewById(R.id.fragment_player_seek_bar);
        mPlayButton = (Button) rootView.findViewById(R.id.fragment_player_play_button);
        mNextButton = (Button) rootView.findViewById(R.id.fragment_player_next_button);
        mPreviousButton = (Button) rootView.findViewById(R.id.fragment_player_previous_button);
        mProgessBar = rootView.findViewById(R.id.progress_bar_container);

        mSongs = getArguments().getParcelableArrayList(SONG_EXTRA);
        mCurrentSongPosition = getArguments().getInt(SONG_ARTIST_POSITION);
        mArtistNameString = getArguments().getString(SONG_ARTIST_NAME);
        applySongToUI(mSongs.get(mCurrentSongPosition));

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                showProgressBar();
                intent.setClass(getActivity(), PlayerService.class);
                if (mPlaying) {
                    mPlayerService.pause();
                } else {
                    mPlayerService.play(mSongs.get(mCurrentSongPosition).getSongURL());
                }
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentSongPosition++;
                applySongToUI(mSongs.get(mCurrentSongPosition));
            }
        });

        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentSongPosition--;
                applySongToUI(mSongs.get(mCurrentSongPosition));
            }
        });

        Intent serviceIntent = new Intent(getActivity(), PlayerService.class);
        getActivity().bindService(serviceIntent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                PlayerService.PlayerServiceBinder binder = (PlayerService.PlayerServiceBinder) iBinder;
                mPlayerService = binder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mPlayerService = null;
            }
        }, Context.BIND_AUTO_CREATE);
        getActivity().startService(serviceIntent);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mPlayerService.seek(seekBar.getProgress());
            }
        });

        hideProgressBar();
        return rootView;
    }

    public void showProgressBar(){
        mProgessBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        mProgessBar.setVisibility(View.GONE);
    }


    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mPlaying = intent.getExtras().getBoolean(PlayerService.PLAYING_ATTR);
            mStopped = intent.getExtras().getBoolean(PlayerService.STOP_ATTR);
            hideProgressBar();
            if(mPlaying){
                mPlayButton.setBackgroundResource(android.R.drawable.ic_media_pause);
                mTimer=new Timer();
                mTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mSeekBar.setProgress(mSeekBar.getProgress() + 1000);
                    }
                }, 0, 1000);//Update text every second
            } else if (mStopped){
                mSeekBar.setProgress(0);
                mTimer.cancel();
                mTimer.purge();
                mPlayButton.setBackgroundResource(android.R.drawable.ic_media_play);
            } else {
                mTimer.cancel();
                mTimer.purge();
                mPlayButton.setBackgroundResource(android.R.drawable.ic_media_play);
            }
        }
    };

    @Override
    public void onResume() {
        getActivity().registerReceiver(receiver, new IntentFilter(PlayerService.PLAYER_SERVICE_ACTION));
        super.onResume();
    }

    @Override
    public void onPause() {
        getActivity().unregisterReceiver(receiver);
        super.onPause();
    }

    private void applySongToUI(ItemToDraw song) {
        if(mCurrentSongPosition==0){
            mPreviousButton.setEnabled(false);
        } else {
            mPreviousButton.setEnabled(true);
        }

        if(mCurrentSongPosition<mSongs.size()-1){
            mNextButton.setEnabled(true);
        } else {
            mNextButton.setEnabled(false);
        }

        if(mPlaying){
            mTimer.cancel();
            mTimer.purge();
            mPlaying=false;
        }

        mPlayButton.setBackgroundResource(android.R.drawable.ic_media_play);
        mArtistName.setText(mArtistNameString);
        mSongName.setText(song.getTitle());
        mAlbumName.setText(song.getSubtitle());
        Picasso.with(getActivity()).load(song.getImgUrl()).into(mSongArt);
        mSeekBar.setMax(30000);
        mSeekBar.setProgress(0);
    }

    public static PlayerFragment newInstance(ArrayList<ItemToDraw> songs , String artistName, int songPosition) {
        PlayerFragment fragment = new PlayerFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(SONG_EXTRA, songs);
        args.putString(SONG_ARTIST_NAME, artistName);
        args.putInt(SONG_ARTIST_POSITION,songPosition);
        fragment.setArguments(args);
        return fragment;
    }
}
