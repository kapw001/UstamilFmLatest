package com.prmobiapp.ustamilfm.activity;

import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.prmobiapp.ustamilfm.Help;
import com.prmobiapp.ustamilfm.MediaItem;
import com.prmobiapp.ustamilfm.MyJOBService;
import com.prmobiapp.ustamilfm.MyService;
import com.prmobiapp.ustamilfm.PlayerConstants;
import com.prmobiapp.ustamilfm.PrivacyPolicy;
import com.prmobiapp.ustamilfm.R;
import com.prmobiapp.ustamilfm.TabGet;
import com.prmobiapp.ustamilfm.UtilFunctions;
import com.prmobiapp.ustamilfm.Utils;
import com.prmobiapp.ustamilfm.adapter.NavDrawerListAdapter;
import com.prmobiapp.ustamilfm.sharedpreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {
    private static boolean adShow = true;
    public static List<TabGet> feedItemList = new ArrayList<TabGet>();
    private AudioManager mAudioManager;
    private static String TAG = MainActivity.class.getSimpleName();
    public static Intent intent;
    public static Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    // slide menu items
    private String[] navMenuTitles;
    // nav drawer title

    private CharSequence mDrawerTitle;
    // used to store app title
    private CharSequence mTitle;
    private static int lastposition = 0;

    //    private NotificationBroadcast headsetAndBluetooth;
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
//        makeJsonArrayRequest();


        callJsonFun();
        callJsonProgramImageChangeFun();


        getIntent().setAction("Already created");
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setIcon(R.drawable.icon);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mTitle = mDrawerTitle = getTitle();
        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar, lastposition);
        drawerFragment.setDrawerListener(this);
        displayView(lastposition);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.shareapp) {
            if (Utils.currentPosition > 0) {
                MediaItem data = Utils.SONGS_LIST.get(Utils.currentPosition);
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Us Tamil FM");
                String sAux = "\nUs Tamil FM: " + data.getTitle() + " CST: " + data.getsTime() + " TO " + data.geteTime() + "\n";
                sAux = sAux + "http://www.ustamilfm.com \n\n";
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "choose one"));
            }
            return true;
        }
        if (id == R.id.rateus) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.prmobiapp.ustamilfm")));
            } catch (ActivityNotFoundException e) {

                Toast.makeText(this, "There is no activity to handle ", Toast.LENGTH_SHORT).show();
            }

        }

        if (id == R.id.help) {
            Intent i = new Intent(this, Help.class);
            startActivity(i);
        }
        if (id == R.id.privacypolicy) {
            Intent i = new Intent(this, PrivacyPolicy.class);
            startActivity(i);
        }
//        if (id == R.id.settings) {
//            Intent in = new Intent(getApplicationContext(), Setting.class);
//            startActivity(in);
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
        lastposition = position;
        NavDrawerListAdapter.setSelectedIndex(lastposition);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastposition = savedInstanceState.getInt("pos");
//        displayView(lastposition);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("pos", lastposition);

    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new FriendsFragmentProgramlist();
                break;
            case 2:
                fragment = new RjProfiles();
                break;
//            case 3:
////                fragment = new BroadcastFragment();
//                fragment = new NewBroadcastFragment();
////                PlayerConstants.list=feedItemList;
//
//
//                break;
//            case 5:
//                fragment = new RequestBoxFragment();
//                break;
//            case 4:
//                fragment = new EventFragment();
//                break;
            case 3:
                fragment = new AboutFragment();
                break;
//            case 7:
//                fragment = new MessagesFragment();
//                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
            setTitle(navMenuTitles[position]);
            // set the toolbar title
//            getSupportActionBar().setTitle(title);
        }
    }

    public void setTitle(String title) {
        mTitle = title;
        getSupportActionBar().setTitle("  " + mTitle);
    }

    public void hide() {
        getSupportActionBar().hide();
    }


    @Override
    protected void onResume() {
        super.onResume();
//        makeJsonArrayRequest();
        Aapplication.getInstance().trackScreenView("Home Screen");


//        Intent in = new Intent(this, MyService.class);
//        in.setAction(Utils.HIDENOTIFY);
//        startService(in);
    }


    private boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    @Override
    protected void onPause() {
        super.onPause();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (isAppIsInBackground(getApplicationContext())) {
//
//                    Intent in = new Intent(getApplicationContext(), MyService.class);
//                    in.setAction(Utils.SHOWNOTIFY);
//                    startService(in);
//                }
//            }
//        }, 500);

    }


    public void makeJsonArrayRequest() {
        String url = "http://www.ustamilfm.com/apis/fmjson.php";
        Log.e(TAG, "makeJsonArrayRequest: " + Utils.SONGS_LIST.size());
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e(TAG, response.toString());
                        JsonParse(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

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
        // Adding request to request queue
//        Aapplication.getInstance().addToRequestQueue(req);

        try {
            JsonParse(new JSONArray(UtilFunctions.loadJSONFromAsset(this)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                String stime = c.getString("stime");
                String etime = c.getString("etime");
                String programStartTime = c.getString("programStartTime");
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


//    @Override
//    protected void onResume() {
//        super.onResume();
//        Controls.nresumeControl(this);
//
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//
//    }
//
//    @Override
//    public void onBackPressed() {
//
//        super.onBackPressed();  // optional depending on your needs
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (isApplicationSentToBackground(this)) {
//            if (SongService.mp != null) {
//                if (SongService.mp.isPlaying()) {
//                    Controls.npauseControl(this);
//                }
//            }
//        }
//    }
//
//    public static boolean isApplicationSentToBackground(final Context context) {
//        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
//        if (!tasks.isEmpty()) {
//            ComponentName topActivity = tasks.get(0).topActivity;
//            if (!topActivity.getPackageName().equals(context.getPackageName())) {
//                return true;
//            }
//        }
//
//        return false;
//    }


    private class GetTabJson extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            feedItemList = UtilFunctions.tabGet(getApplicationContext());

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
//            MainActivity.intent = new Intent(getApplicationContext(), SongService.class);
//            startService(MainActivity.intent);
        }
    }

    private void callJsonFun() {
        String url = "http://www.ustamilfm.com/tab.php";
        String tag_string_req = "string_req";
        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                try {
                    JSONArray js = new JSONArray(response);
                    for (int i = 0; i < js.length(); i++) {
                        TabGet songData = new TabGet();
                        try {
                            JSONObject c = js.getJSONObject(i);
                            String tabgetjson = c.getString("tabgetjson");
                            String tabnames = c.getString("tabnames");
                            songData.setUrljson(tabgetjson);
                            songData.setTabname(tabnames);
                            feedItemList.add(songData);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                MainActivity.intent = new Intent(getApplicationContext(), SongService.class);
//                startService(MainActivity.intent);

            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        });

// Adding request to request queue
//        Aapplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void callJsonProgramImageChangeFun() {
        sharedpreferences s = new sharedpreferences(this);
        String url1 = "http://www.ustamilfm.com/apis/fmjson.php";
        String tag_string_req1 = "string_req";
        StringRequest strReq1 = new StringRequest(Request.Method.GET,
                url1, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());

                parse(response);
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        });

// Adding request to request queue
        Aapplication.getInstance().addToRequestQueue(strReq1, tag_string_req1);

//        parse(UtilFunctions.loadJSONFromAsset(this));
    }


    private void parse(String response) {
        try {
            JSONArray js = new JSONArray(response);
            for (int i = 0; i < js.length(); i++) {
                MediaItem songData = new MediaItem();
                try {
                    JSONObject c = js.getJSONObject(i);
                    String title = c.getString("programs");
                    String artist = c.getString("rjnames");
                    String starttime = c.getString("starttime");
                    String endtime = c.getString("endtime");
                    String url = c.getString("programimage");
//                    String url = "http://www.ustamilfm.com/images/programs/" + c.getString("urlimage");
                    String tamildes = c.getString("tamildes");
                    String rjimage = c.getString("rjimage");

                    String getvalues = sharedpreferences.getValues(c.getString("programs"));
                    if (getvalues == null) {
//						Log.e("Ok GOt","KKKK");
                        sharedpreferences.savepre(c.getString("programs"), "false");
                    }
                    String favorite = sharedpreferences.getValues(c.getString("programs"));
                    Log.e("Ok GOt", sharedpreferences.getValues(c.getString("programs")));


                    String stime = c.getString("starttime").toString();
                    String etime = c.getString("endtime").toString();

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
                    PlayerConstants.SONGS_LIST.add(songData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
//            sartService();
//                    MainActivity.intent = new Intent(getApplicationContext(), SongService.class);
//                    startService(MainActivity.intent);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void sartService() {
//        MainActivity.intent = new Intent(this, SongService.class);
//        startService(MainActivity.intent);
    }


}