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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.prmobiapp.ustamilfm.R;
import com.prmobiapp.ustamilfm.Utils;
import com.prmobiapp.ustamilfm.MyService;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Karthik on 3/30/2016.
 */
public class ImageList extends Fragment {

//    private Handler handler;

    private static final String TAG = "ImageList";

    public MaterialFavoriteButton favorites;
    public ImageView rjbgimage, bgimagetest;
    public CircleImageView rjimage;
    public TextView rjname, showname;
    public ProgressBar spinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        handler=new Handler();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.imagelist, container, false);
        spinner = (ProgressBar) rootView.findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE);

        rjbgimage = (ImageView) rootView.findViewById(R.id.rjbgimage);
        bgimagetest = (ImageView) rootView.findViewById(R.id.bgimagetest);
        rjimage = (CircleImageView) rootView.findViewById(R.id.rjimage);
        rjname = (TextView) rootView.findViewById(R.id.rjname);
        showname = (TextView) rootView.findViewById(R.id.showname);
        favorites = (MaterialFavoriteButton) rootView.findViewById(R.id.favior);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                mMessageReceiver, Utils.registerIntentFilterIMAGECHANGE());
        Intent in = new Intent(getActivity(), MyService.class);
        in.setAction(Utils.CHANGEPROGRAMIMAGE);
        getActivity().startService(in);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(
                mMessageReceiver);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            // Get extra data included in the Intent
//            int position = intent.getIntExtra("message",0);
            if (intent.getAction().equals(Utils.CHANGEPROGRAMIMAGE)) {
                changePlayPauseImage(intent.getStringExtra("bgimage"), intent.getStringExtra("rjname"), intent.getStringExtra("program"), intent.getStringExtra("rjimage"));
            } else if (intent.getAction().equals(Utils.CLOSESPINNER)) {
                Log.e("Test", "onReceive: Work ");
                spinner.setVisibility(View.GONE);
            }
//            Log.e("receiver", "Got message: " + position+" Kkk "+Utils.SONGS_LIST.size());
            Log.e("Test", "makeJsonArrayRequest: " + Utils.SONGS_LIST.size());
        }
    };

    private void changePlayPauseImage(String bgimage, String rjnamet, String program, String rjimaget) {

        Log.e(TAG, "changePlayPauseImage: " + bgimage);
        Log.e(TAG, "changePlayPauseImage: " + rjnamet);
        Log.e(TAG, "changePlayPauseImage: " + rjimaget);
        Log.e(TAG, "changePlayPauseImage: " + program);

//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                MediaItem m=Utils.SONGS_LIST.get(position);
        rjname.setText(rjnamet);
        showname.setText(program);
        Picasso.with(getActivity()).load(bgimage).into(rjbgimage);
        Picasso.with(getActivity()).load(rjimaget).placeholder(R.drawable.pr).into(rjimage);
        Picasso.with(getActivity()).load(bgimage).placeholder(R.mipmap.ic_launcher).into(bgimagetest);
        spinner.setVisibility(View.INVISIBLE);

//            }
//        });

    }

}
