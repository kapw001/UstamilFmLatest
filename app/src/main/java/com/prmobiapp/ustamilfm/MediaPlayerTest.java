package com.prmobiapp.ustamilfm;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by Karthik on 11/3/2015.
 */
public class MediaPlayerTest {
    MediaPlayer mp;
    MediaPlayerTest(){
        mp=new MediaPlayer();
    }

    public void start(){
        try {
            mp.setDataSource("http://192.184.9.158:8329");
        } catch (IOException e) {
            e.printStackTrace();
        }

        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
       mp.prepareAsync();
    }

    public void stop(){
        if(mp!=null){
            mp.stop();
        }
    }

    public void pause(){
        mp.pause();
    }

    public void play(){
        mp.start();
    }


}
