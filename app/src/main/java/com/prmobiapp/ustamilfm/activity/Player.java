package com.prmobiapp.ustamilfm.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.prmobiapp.ustamilfm.R;
import com.prmobiapp.ustamilfm.Utils;
import com.prmobiapp.ustamilfm.MyService;

/**
 * Created by Karthik on 3/30/2016.
 */
public class Player extends Fragment implements View.OnClickListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        handler = new Handler();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.player, container, false);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
//        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
//                mMessageReceiver, Utils.registerIntentFilter());
//        Intent in = new Intent(getActivity(), MyService.class);
//        in.setAction(Utils.PLAYPAUSEIMAGECHANGE);
//        getActivity().startService(in);
    }

    @Override
    public void onPause() {
        super.onPause();
//        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(
//                mMessageReceiver);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            if (intent.getAction().equals(Utils.PLAY)) {
                changePlayPauseImage(message);
            } else if (intent.getAction().equals(Utils.PAUSE)) {
                changePlayPauseImage(message);
            } else if (intent.getAction().equals(Utils.PROGRESS)) {
                changePlayPauseImage(message);
            } else if (intent.getAction().equals(Utils.PLAYPAUSEIMAGECHANGE)) {
                changePlayPauseImage(message);
            }
            Log.e("receiver", "Got message: " + message);
        }
    };


    @Override
    public void onClick(View v) {

    }

    private void changePlayPauseImage(final String message) {
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//        if (message.contentEquals("Play")) {
//            playPause.setImageResource(R.drawable.pause);
//            progressBar.setVisibility(View.INVISIBLE);
//            playPause.setVisibility(View.VISIBLE);
//        } else if (message.contentEquals("Pause")) {
//            playPause.setImageResource(R.drawable.play);
//            progressBar.setVisibility(View.INVISIBLE);
//            playPause.setVisibility(View.VISIBLE);
//        } else if (message.contentEquals("Progress")) {
//            Log.e("HI", "run: " + message);
//            progressBar.setVisibility(View.VISIBLE);
//            playPause.setVisibility(View.INVISIBLE);
//        }
//        Log.e("Testing", "run: Testing      " + message);
//            }
//        });

    }


}
