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
import android.widget.ImageButton;
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

    private static final String ATTR_IS_PLAYING = "is_playing";
    private static final String ATTR_ELAPSED_TIME = "elapsed_time";
    private static final String START_SONG = "start_song";


    private ImageView mSongArt;
    private TextView mArtistName;
    private TextView mAlbumName;
    private TextView mSongName;
    private TextView mElapsedTime;
    private SeekBar mSeekBar;
    private ImageButton mPlayButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private ImageButton mShareButton;
    private String mArtistNameString;
    private View mProgessBar;
    private boolean mPlaying;
    public int mCurrentSongPosition;
    private ArrayList<ItemToDraw> mSongs;
    private PlayerService mPlayerService;
    private Timer mTimer = new Timer();
    private boolean mStopped;
    private boolean mStartSong = false;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_player, container, false);

        mSongArt = (ImageView) rootView.findViewById(R.id.fragment_player_album_image);
        mArtistName = (TextView) rootView.findViewById(R.id.fragment_player_artist_name);
        mAlbumName = (TextView) rootView.findViewById(R.id.fragment_player_album_name);
        mSongName = (TextView) rootView.findViewById(R.id.fragment_player_song_name);
        mElapsedTime =(TextView) rootView.findViewById(R.id.fragment_player_elapsed);
        mSeekBar = (SeekBar) rootView.findViewById(R.id.fragment_player_seek_bar);
        mPlayButton = (ImageButton) rootView.findViewById(R.id.fragment_player_play_button);
        mNextButton = (ImageButton) rootView.findViewById(R.id.fragment_player_next_button);
        mPreviousButton = (ImageButton) rootView.findViewById(R.id.fragment_player_previous_button);
        mShareButton = (ImageButton) rootView.findViewById(R.id.fragment_player_share_button);
        mProgessBar = rootView.findViewById(R.id.progress_bar_container);

        mSongs = getArguments().getParcelableArrayList(SONG_EXTRA);
        mCurrentSongPosition = getArguments().getInt(SONG_ARTIST_POSITION);
        mArtistNameString = getArguments().getString(SONG_ARTIST_NAME);
        mStartSong = getArguments().getBoolean(START_SONG);
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
                resetPlayerControls();
                playCurrentSong();
            }
        });

        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentSongPosition--;
                applySongToUI(mSongs.get(mCurrentSongPosition));
                resetPlayerControls();
                playCurrentSong();
            }
        });

        mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, getActivity().getString(R.string.share_text,
                        mSongs.get(mCurrentSongPosition).getTitle(),
                        mArtistNameString));
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getActivity().getString(R.string.share_with)));
            }
        });

        Intent serviceIntent = new Intent(getActivity(), PlayerService.class);
        getActivity().bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
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


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mPlaying=savedInstanceState.getBoolean(ATTR_IS_PLAYING);
            mCurrentSongPosition=savedInstanceState.getInt(SONG_ARTIST_POSITION);
            mSongs=savedInstanceState.getParcelableArrayList(SONG_EXTRA);
            applySongToUI(mSongs.get(mCurrentSongPosition));
            updateUIDependingOfStatus();
            mElapsedTime.setText(savedInstanceState.getString(ATTR_ELAPSED_TIME));
            mStartSong=false;
        }
    }

    ServiceConnection serviceConnection =  new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            PlayerService.PlayerServiceBinder binder = (PlayerService.PlayerServiceBinder) iBinder;
            mPlayerService = binder.getService();
            if(mStartSong){
                playCurrentSong();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mPlayerService = null;
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(ATTR_IS_PLAYING,mPlaying);
        outState.putInt(SONG_ARTIST_POSITION, mCurrentSongPosition);
        outState.putParcelableArrayList(SONG_EXTRA,mSongs);
        outState.putString(ATTR_ELAPSED_TIME,mElapsedTime.getText().toString());
        super.onSaveInstanceState(outState);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mPlaying = intent.getExtras().getBoolean(PlayerService.PLAYING_ATTR);
            mStopped = intent.getExtras().getBoolean(PlayerService.STOP_ATTR);
            hideProgressBar();
            updateUIDependingOfStatus();
        }
    };

    private void updateUIDependingOfStatus() {
        if(mPlaying){
            mPlayButton.setBackgroundResource(android.R.drawable.ic_media_pause);
            mTimer=new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(getActivity()!=null){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mElapsedTime.setText(String.format("0:%02d", mSeekBar.getProgress() / 1000));
                            }
                        });
                    }

                    mSeekBar.setProgress(mSeekBar.getProgress() + 1000);
                }
            }, 0, 1000);//Update text every second
        } else if (mStopped){
            mElapsedTime.setText(getString(R.string.elapsed_time));
            mSeekBar.setProgress(0);
            mTimer.cancel();
            mTimer.purge();
            mPlayButton.setBackgroundResource(android.R.drawable.ic_media_play);
        } else { //Pause
            mTimer.cancel();
            mTimer.purge();
            mPlayButton.setBackgroundResource(android.R.drawable.ic_media_play);
        }
    }

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

        mPlayButton.setBackgroundResource(android.R.drawable.ic_media_play);
        mArtistName.setText(mArtistNameString);
        mSongName.setText(song.getTitle());
        mAlbumName.setText(song.getSubtitle());
        Picasso.with(getActivity()).load(song.getImgUrl()).into(mSongArt);
        mSeekBar.setMax(30000);
        mSeekBar.setProgress(0);
    }

    private void playCurrentSong(){
        showProgressBar();
        mPlayerService.play(mSongs.get(mCurrentSongPosition).getSongURL());
    }

    private void resetPlayerControls(){
        if(mPlaying){
            mElapsedTime.setText(getString(R.string.elapsed_time));
            mTimer.cancel();
            mTimer.purge();
            mPlaying=false;
        }
    }
    public static PlayerFragment newInstance(ArrayList<ItemToDraw> songs , String artistName, int songPosition) {
        PlayerFragment fragment = new PlayerFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(SONG_EXTRA, songs);
        args.putString(SONG_ARTIST_NAME, artistName);
        args.putInt(SONG_ARTIST_POSITION, songPosition);
        args.putBoolean(START_SONG,true);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStop() {
        getActivity().unbindService(serviceConnection);
        super.onStop();
    }
}
