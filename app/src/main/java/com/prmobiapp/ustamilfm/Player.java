package com.prmobiapp.ustamilfm;

import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by yasar on 21/2/18.
 */

public class Player extends MediaPlayer implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {


    private MediaCallBack mediaCallBack;
    private String url = "http://streaming.radio.co/s0cfc93915/listen";
    private MediaPlayer mediaPlayer;

    private MediaState mediaState = MediaState.IDLE;

    public MediaState getMediaState() {
        return mediaState;
    }

    public Player(MediaCallBack mediaCallBack) {
        this.mediaCallBack = mediaCallBack;
        this.mediaPlayer = new MediaPlayer();
    }

    public void play() {
//        if (mediaPlayer.isPlaying()) {
        mediaState = MediaState.PROGRESS;
        mediaPlayer.stop();
        mediaPlayer.reset();
//        }
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {

        if (mediaPlayer.isPlaying()) {
            mediaState = MediaState.STOPED;
            mediaPlayer.setOnPreparedListener(null);
            mediaPlayer.stop();
            mediaPlayer.reset();
        }

    }

//    public void onDestory() {
//        mediaState = MediaState.STOPED;
//        if (mediaPlayer.isPlaying()) {
//            mediaPlayer.setOnPreparedListener(null);
//            mediaPlayer.stop();
//            mediaPlayer.release();
//        }
//
//
//
//    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaState = MediaState.PLAYING;
        mp.start();
        mediaCallBack.T();

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mediaState = MediaState.STOPED;
        mediaCallBack.StopMusic();
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }


    interface MediaCallBack {
        void T();

        void StopMusic();
    }
}
