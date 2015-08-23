package io.github.pedrofraca.spotifystreamer.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;

import java.io.IOException;

/**
 * Created by pedrofraca on 22/08/15.
 */
public class PlayerService extends Service implements MediaPlayer.OnPreparedListener,MediaPlayer.OnCompletionListener {
    public static final String PLAYER_SERVICE_ACTION = "player_receiver_action";
    public static final String PLAYING_ATTR = "player_service_playing";
    public static final String PAUSE_ATTR = "player_service_pause";
    public static final String STOP_ATTR = "player_service_stop";
    public static final String DURATION_ATTR = "player_service_duration";
    private MediaPlayer mMediaPlayer;

    private final IBinder playerServiceBinder = new PlayerServiceBinder();

    @Override
    public void onCreate() {
        createMediaPlayer();
        super.onCreate();
    }

    private void createMediaPlayer() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setWakeMode(getApplicationContext(),
                PowerManager.PARTIAL_WAKE_LOCK);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return playerServiceBinder;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        sendPlayBroadcast();
    }

    public void sendPauseBroadcast(){
        Intent serviceIntent = new Intent();
        serviceIntent.setAction(PLAYER_SERVICE_ACTION);
        serviceIntent.putExtra(PLAYING_ATTR, false);
        serviceIntent.putExtra(STOP_ATTR, false);
        sendBroadcast(serviceIntent);
    }

    public void sendStopBroadcast(){
        Intent serviceIntent = new Intent();
        serviceIntent.setAction(PLAYER_SERVICE_ACTION);
        serviceIntent.putExtra(STOP_ATTR, true);
        serviceIntent.putExtra(PLAYING_ATTR, false);
        sendBroadcast(serviceIntent);
    }

    public void sendPlayBroadcast(){
        Intent serviceIntent = new Intent();
        serviceIntent.setAction(PLAYER_SERVICE_ACTION);
        serviceIntent.putExtra(PLAYING_ATTR, true);
        serviceIntent.putExtra(STOP_ATTR, false);
        sendBroadcast(serviceIntent);
    }

    public void play(String songURL) {
        try {
            if(mMediaPlayer.isPlaying()) {
                mMediaPlayer.reset();
                mMediaPlayer.release();
                createMediaPlayer();
            }
            mMediaPlayer.setDataSource(songURL);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mMediaPlayer.prepareAsync();
    }

    public void pause() {
        mMediaPlayer.pause();
        sendPauseBroadcast();
    }

    public void seek(int where){
        mMediaPlayer.seekTo(where);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        sendStopBroadcast();
    }

    public class PlayerServiceBinder extends Binder {
        public PlayerService getService() {
            return PlayerService.this;
        }
    }

}