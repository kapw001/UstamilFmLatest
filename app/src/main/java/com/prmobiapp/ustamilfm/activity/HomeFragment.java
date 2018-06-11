package com.prmobiapp.ustamilfm.activity;

/**
 * Created by Ravi on 29/07/15.
 */

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.prmobiapp.ustamilfm.JobScheduleExample;
import com.prmobiapp.ustamilfm.MediaItem;
import com.prmobiapp.ustamilfm.MyJOBService;
import com.prmobiapp.ustamilfm.MyService;
import com.prmobiapp.ustamilfm.R;
import com.prmobiapp.ustamilfm.UtilFunctions;
import com.prmobiapp.ustamilfm.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import be.rijckaert.tim.animatedvector.FloatingMusicActionButton;


public class HomeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "HomeFragment";

    public HomeFragment() {

    }

    private List<MediaItem> feedItemList = new ArrayList<MediaItem>();
    public boolean isClickedFirstTime = true;
    private FloatingActionButton playPause;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setRetainInstance(true);

    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;//= inflater.inflate(R.layout.fragment_home, container, false);
//        if (isNetworkAvailable(getActivity())) {
        // code here
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        playPause = (FloatingActionButton) rootView.findViewById(R.id.playpause);
        playPause.setOnClickListener(this);
        progressBar = (ProgressBar) rootView.findViewById(R.id.playprogressBar);


        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onResume() {
        super.onResume();
        makeJsonArrayRequest();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                mMessageReceiver, Utils.registerIntentFilter());
        Intent in = new Intent(getActivity(), MyService.class);
        in.setAction(Utils.PLAYPAUSEIMAGECHANGE);
        getActivity().startService(in);

//        MyJOBService.scheduleJob(getActivity());
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
//    }


    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(
                mMessageReceiver);
        super.onPause();

    }


    public void makeJsonArrayRequest() {
        String url = "http://www.ustamilfm.com/apis/fmjson.php";
        Log.e(TAG, "makeJsonArrayRequest: " + Utils.SONGS_LIST.size());
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        JsonParse(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Utils.CLOSESPINNER);
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);

            }
        });
//        Cache cache = AppController.getInstance().getRequestQueue().getCache();
//        Cache.Entry entry = cache.get(url);
//        if (entry != null) {
//            try {
//                String data = new String(entry.data, "UTF-8");
//                JSONArray response = new JSONArray(data);
//                JsonParse(response);
//                Log.e("called", "Testing");
//                // handle data, like converting it to xml, json, bitmap etc.,
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
////            Log.e(TAG, "makeJsonArrayRequest: " + Utils.SONGS_LIST.size());
//        } else {
//            // Cached response doesn't exists. Make network call here
//            AppController.getInstance().addToRequestQueue(req);
//        }
//        // Adding request to request queue
        Aapplication.getInstance().addToRequestQueue(req);

//        try {
//            JsonParse(new JSONArray(UtilFunctions.loadJSONFromAsset(getContext())));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }


    private void JsonParse(JSONArray response) {
        try {
            Utils.SONGS_LIST.clear();
            Log.e(TAG, "JsonParse: " + response);
            for (int i = 0; i < response.length(); i++) {
                JSONObject c = (JSONObject) response
                        .get(i);
                String title = c.getString("programs");
                String artist = c.getString("rjnames");
                String starttime = c.getString("starttime");
                String endtime = c.getString("endtime");
//                String url = "http://www.ustamilfm.com/images/programs/" + c.getString("urlimage");
                String url = c.getString("programimage");
                String tamildes = c.getString("tamildes");
                String rjimage = c.getString("rjimage");
                String favorite = "false";
                String stime = c.getString("starttime");
                String etime = c.getString("endtime");
                String programStartTime = c.getString("starttime");
                Log.e(TAG, "JsonParse: " + programStartTime);

                MediaItem songData = new MediaItem();
                songData.setsTime(stime);
                songData.seteTime(etime);
                songData.setShowTime(starttime);
                songData.setShowEndTime(endtime);
                songData.setTitle(title);
                songData.setF(true);
                songData.setArtist(artist);
                songData.setUrlImage(url);
                songData.setTamilDes(tamildes);
                songData.setRjimage(rjimage);
                songData.setfavorite(favorite);
                songData.setProgramStartTime(programStartTime);
                Utils.SONGS_LIST.add(songData);
            }


        } catch (JSONException e) {
            e.printStackTrace();

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.playpause:
                Intent in = new Intent(getActivity(), MyService.class);
                if (Utils.mediaPlayingStatus == false) {
                    Utils.audioFocusChange = true;
                    in.setAction(Utils.PLAY);
//                    Utils.playBtnClick=true;
                    Utils.isBackGroundPlaying = true;
                } else if (Utils.mediaPlayingStatus == true) {
                    Utils.audioFocusChange = false;
                    in.setAction(Utils.PAUSE);
//                    Utils.playBtnClick=false;
                }
                getActivity().startService(in);
                break;
        }
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

    private void changePlayPauseImage(final String message) {
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
        if (message.contentEquals("Play")) {

//            playPause.playAnimation();
            playPause.setImageResource(R.drawable.ic_stop_black_24dp);

            progressBar.setVisibility(View.INVISIBLE);
            playPause.setVisibility(View.VISIBLE);
        } else if (message.contentEquals("Pause")) {
//            playPause.playAnimation();
            playPause.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            progressBar.setVisibility(View.INVISIBLE);
            playPause.setVisibility(View.VISIBLE);
        } else if (message.contentEquals("Progress")) {
            Log.e("HI", "run: " + message);
            progressBar.setVisibility(View.VISIBLE);
            playPause.setVisibility(View.INVISIBLE);
        }


        Log.e("Testing", "run: Testing      " + message);
//            }
//        });

    }
}
