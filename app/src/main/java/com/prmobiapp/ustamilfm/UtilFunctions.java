package com.prmobiapp.ustamilfm;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.text.Html;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.prmobiapp.ustamilfm.activity.Aapplication;
import com.prmobiapp.ustamilfm.activity.MainActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class UtilFunctions {
    static String LOG_CLASS = "UtilFunctions";
    //	static String[] TITLE={"test1","test2","test3","test4"};
//	static String[] ALBUMNAME={"Karthik1","Karthik2","Karthik3","Karthik4"};
//	static int[] ALBUMID={R.drawable.img_sm1,R.drawable.img_sm2,R.drawable.img_sm3,R.drawable.img_sm4};
//	static String[] showTime={"3:00 pm","12:36 pm","12:37 pm","12:38 pm"};
//	static String[] showEndTime={"3:59 pm","12:37 pm","12:38 pm","12:59 pm"};
    public static final String TAG = Aapplication.class
            .getSimpleName();
//    private static JSONArray jsa = null;

    /**
     * Check if service is running or not
     *
     * @param serviceName
     * @param context
     * @return
     */
    public static boolean isServiceRunning(String serviceName, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private static Object makeGetRequest(String url1) {
//		String  tag_string_req = "string_req";
//
//		StringRequest strReq = new StringRequest(Request.Method.GET,
//				url, new Response.Listener<String>() {
//
//			@Override
//			public void onResponse(String response) {
//				Log.d(TAG, response.toString());
//				try {
//					jsa=new JSONArray(response);
//
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//
//			}
//
//		}, new Response.ErrorListener() {
//
//			@Override
//			public void onErrorResponse(VolleyError error) {
//				VolleyLog.d(TAG, "Error: " + error.getMessage());
//
//			}
//		});
//
//// Adding request to request queue
//		Aapplication.getInstance().addToRequestQueue(strReq, tag_string_req);

//        JSONArray jsa = null;
//        HttpClient client = new DefaultHttpClient();
//        HttpGet request = new HttpGet(url);
////		HttpGet request = new HttpGet("http://www.ustamilfm.com/fmjson.php");
//        // replace with your url
//        String res = null;
//        HttpResponse response;
//        try {
//            response = client.execute(request);
//            res = EntityUtils.toString(response.getEntity());
//            jsa = new JSONArray(res);
////			Log.d("Response of GET request", response.toString());
//        } catch (ClientProtocolException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        Object jsa = null;
        URL url;
        InputStream in;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(url1);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream());
            jsa = readStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }


        return jsa;
    }

    private static String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is), 1000);
        for (String line = r.readLine(); line != null; line = r.readLine()) {
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }

    /**
     * Read the songs present in external storage
     *
     * @param context
     * @return
     */
    public static ArrayList<TabGet> tabGet(Context context) {
        ArrayList<TabGet> listOfSongs = new ArrayList<TabGet>();

        JSONArray js = null;
        try {
            js = new JSONArray(makeGetRequest("http://www.ustamilfm.com/tab.php").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (js != null) {

            for (int i = 0; i < js.length(); i++) {
                TabGet songData = new TabGet();
                try {
                    JSONObject c = js.getJSONObject(i);
                    String tabgetjson = c.getString("tabgetjson");
                    String tabnames = c.getString("tabnames");
                    songData.setUrljson(tabgetjson);
                    songData.setTabname(tabnames);
                    listOfSongs.add(songData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Log.e("Response of GET request", js.toString());
        }
        Log.d("SIZE", "SIZE: " + listOfSongs.size());
        return listOfSongs;
    }

    public static ArrayList<MediaItem> listOfSongs(Context context) {
//         String res=makeGetRequest();
        sharedpreferences s = new sharedpreferences(context);
        ArrayList<MediaItem> listOfSongs = new ArrayList<MediaItem>();
//		new Handler().post(new Runnable() {
//			@Override
//			public void run() {
        // Code here will run in UI thread
        JSONArray js = null;
        try {
            js = new JSONArray(makeGetRequest("http://www.ustamilfm.com/apis/fmjson.php").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        try {
//            js = new JSONArray(loadJSONFromAsset(context));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        if (js != null) {

            for (int i = 0; i < js.length(); i++) {
                MediaItem songData = new MediaItem();
                try {
                    JSONObject c = js.getJSONObject(i);
                    String title = c.getString("programs");
                    String artist = c.getString("rjnames");
                    String starttime = c.getString("starttime");
                    String endtime = c.getString("endtime");
                    String url = c.getString("programimage");
//					String url = "http://www.ustamilfm.com/images/programs/" + c.getString("urlimage");
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
                    listOfSongs.add(songData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            Log.e("Response of GET request", js.toString());
        }
        Log.d("SIZE", "SIZE: " + listOfSongs.size());
        return listOfSongs;
    }


    public static Map<Object, ArrayList<MediaItem>> listOfProgramsList(Context context) {
//         String res=makeGetRequest();
        sharedpreferences s = new sharedpreferences(context);

//		new Handler().post(new Runnable() {
//			@Override
//			public void run() {
        // Code here will run in UI thread

        String s1 = makeGetRequest("http://www.ustamilfm.com/apis/programs.php").toString();

        JSONObject js = null;
        try {
            js = new JSONObject(s1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e(TAG, "listOfProgramsList: " + js.toString());


        Map<Object, ArrayList<MediaItem>> programsList = new HashMap<>();

        try {
            assert js != null;
            programsList.put("Weekdays",makeJson(js.getJSONArray("Weekdays")));
            programsList.put("Saturday",makeJson(js.getJSONArray("Saturday")));
            programsList.put("Sunday",makeJson(js.getJSONArray("Sunday")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
//

        Log.d("SIZE", "SIZE: " + programsList.size());
        return programsList;
    }


    private static ArrayList<MediaItem> makeJson(JSONArray js) {

        ArrayList<MediaItem> listOfSongs = new ArrayList<MediaItem>();
        if (js != null) {

            for (int i = 0; i < js.length(); i++) {
                MediaItem songData = new MediaItem();
                try {
                    JSONObject c = js.getJSONObject(i);
                    String title = c.getString("programs");
                    String artist = c.getString("rjnames");
                    String starttime = c.getString("starttime");
                    String endtime = c.getString("endtime");
                    String url = c.getString("programimage");
//					String url = "http://www.ustamilfm.com/images/programs/" + c.getString("urlimage");
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
                    listOfSongs.add(songData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            Log.e("Response of GET request", js.toString());
        }


        return listOfSongs;

    }


    public static ArrayList<MovieTraillerItem> listOfItems(Context context, String url) {
//         String res=makeGetRequest();
        ArrayList<MovieTraillerItem> listOfItems = new ArrayList<MovieTraillerItem>();
//		new Handler().post(new Runnable() {
//			@Override
//			public void run() {
        // Code here will run in UI thread
        JSONArray js = null;
        try {
            js = new JSONArray(makeGetRequest(url).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (js != null) {
            for (int i = 0; i < js.length(); i++) {
                MovieTraillerItem songData = new MovieTraillerItem();
                try {
                    JSONObject c = js.getJSONObject(i);
                    String thumbimage = c.getString("thumbimage");
                    String name = c.getString("name");
                    String subname = c.getString("subname");
                    String video = c.getString("video");
                    Log.e("URL", thumbimage + " " + Html.fromHtml(thumbimage));
//				String url ="http://www.ustamilfm.com/images/programs/"+c.getString("urlimage");
//				String tamildes =c.getString("tamildes");


                    songData.setThumimage(thumbimage);
                    songData.setName(name);
                    songData.setSubname(subname);
                    songData.setVideo(video);

                    listOfItems.add(songData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            Log.e("Response of GET request", js.toString());
//
        }
        Log.d("SIZE", "SIZE: " + listOfItems.size());
        return listOfItems;
    }

    public static String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("fmjson.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static ArrayList<MovieTraillerItem> listOfItemstra(Context context) {
//         String res=makeGetRequest();
        ArrayList<MovieTraillerItem> listOfItems = new ArrayList<MovieTraillerItem>();
//		new Handler().post(new Runnable() {
//			@Override
//			public void run() {
        // Code here will run in UI thread
        JSONArray js = null;
        try {
            js = new JSONArray(makeGetRequest("http://www.ustamilfm.com/bupcoming.php").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (js != null) {
            for (int i = 0; i < js.length(); i++) {
                MovieTraillerItem songData = new MovieTraillerItem();
                try {
                    JSONObject c = js.getJSONObject(i);
                    String thumbimage = c.getString("thumbimage");
                    String name = c.getString("name");
                    String subname = c.getString("subname");
                    String video = c.getString("video");
                    Log.e("URL", thumbimage + " " + Html.fromHtml(thumbimage));
//				String url ="http://www.ustamilfm.com/images/programs/"+c.getString("urlimage");
//				String tamildes =c.getString("tamildes");
                    songData.setThumimage(thumbimage);
                    songData.setName(name);
                    songData.setSubname(subname);
                    songData.setVideo(video);

                    listOfItems.add(songData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

//            Log.e("Response of GET request", js.toString());
////
        }
//        Log.d("SIZE", "SIZE: " + listOfItems.size());
        return listOfItems;
    }

    public static ArrayList<MovieTraillerItem> listOfRjprofiles(Context context) {
        ArrayList<MovieTraillerItem> listOfItems = new ArrayList<MovieTraillerItem>();
        JSONArray js = null;
        try {
            js = new JSONArray(makeGetRequest("http://www.ustamilfm.com/apis/rjlist.php").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        JSONArray js = null;
//        try {
//            js = new JSONArray(loadJSONFromAsset(context));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        if (js != null) {
            for (int i = 0; i < js.length(); i++) {
                MovieTraillerItem songData = new MovieTraillerItem();
                try {
                    JSONObject c = js.getJSONObject(i);
                    String thumbimage = c.getString("rjimage");
                    String name = c.getString("rjnames");
                    String showname = c.getString("programs");
                    String programs = c.getString("programs");
                    songData.setThumimage(thumbimage);
                    songData.setName(name);
                    songData.setSubname(showname);
                    songData.setPrograms(programs);
                    listOfItems.add(songData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            Log.e("Response of GET request", js.toString());
//
        }
        Log.d("SIZE", "SIZE: " + listOfItems.size());

//        HashSet<MovieTraillerItem> items = new HashSet<>();
//        items.addAll(listOfItems);
//
//        listOfItems.clear();
//        listOfItems.addAll(items);

        return listOfItems;
    }

    public static ArrayList<MovieTraillerItem> listOfItemsEup(String url) {
//         String res=makeGetRequest();
        ArrayList<MovieTraillerItem> listOfItems = new ArrayList<MovieTraillerItem>();
//		new Handler().post(new Runnable() {
//			@Override
//			public void run() {
        // Code here will run in UI thread
        JSONArray js = null;
        try {
            js = new JSONArray(makeGetRequest(url).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (js != null) {
            for (int i = 0; i < js.length(); i++) {
                MovieTraillerItem songData = new MovieTraillerItem();
                try {
                    JSONObject c = js.getJSONObject(i);
//				if (c.getString("thumbimage")!=null){
                    String thumbimage = c.getString("thumbimage");
                    songData.setThumimage(thumbimage);
//					Log.e("Tag",thumbimage);
//				}
//				if (c.getString("thumbimage2")!=null){
                    String thumbimage2 = c.getString("thumbimage2");
                    songData.setThumbimage2(thumbimage2);
//				}
//				if (c.getString("des")!=null){
                    String des = c.getString("des");
                    songData.setDes(des);
//				}
//				if (c.getString("des1")!=null){
                    String des1 = c.getString("des1");
                    songData.setDes1(des1);
//				}
//				if (c.getString("moreinfo")!=null){
                    String moreinfo = c.getString("moreinfo");
                    songData.setMoreinfo(moreinfo);
//				}
//				if (c.getString("moreinfo2")!=null){
                    String moreinfo2 = c.getString("moreinfo2");
                    songData.setMoreinfo2(moreinfo2);
//				}
//				if (c.getString("address")!=null){
                    String address = c.getString("address");
                    songData.setAddress(address);
//				}
//				if (c.getString("address1")!=null){
                    String address1 = c.getString("address1");
                    songData.setAddress1(address1);
//				}
//				if (c.getString("address2")!=null){
                    String address2 = c.getString("address2");
                    songData.setAddress2(address2);
//				}
//				if (c.getString("address3")!=null){
                    String address3 = c.getString("address3");
                    songData.setAddress3(address3);
//				}
//				if (c.getString("address4")!=null){
                    String address4 = c.getString("address4");
                    songData.setAddress4(address4);
//				}
//				if (c.getString("address5")!=null){
                    String address5 = c.getString("address5");
                    songData.setAddress5(address5);
//				}
//				if (c.getString("address6")!=null){
                    String address6 = c.getString("address6");
                    songData.setAddress(address6);
//				}
//				if (c.getString("name")!=null){
                    String name = c.getString("name");
                    songData.setName(name);
//				}
//				if (c.getString("subname")!=null){
                    String subname = c.getString("subname");
                    songData.setSubname(subname);
//				}
//				if (c.getString("video")!=null){
                    String video = c.getString("video");
                    songData.setVideo(video);
//				}


                    listOfItems.add(songData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            Log.e("Response of GET request", js.toString());
//
        }
        Log.d("SIZE", "SIZE: " + listOfItems.size());
        return listOfItems;
    }


    /**
     * Get the album image from albumId
     *
     * @param context
     * @param album_id //	 * @param resize
     * @return
     */
    public static Bitmap getAlbumart(Context context, Long album_id) {
        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
            Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);
            ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
            if (pfd != null) {
                FileDescriptor fd = pfd.getFileDescriptor();
                bm = BitmapFactory.decodeFileDescriptor(fd, null, options);
                pfd = null;
                fd = null;
            }
        } catch (Error ee) {
        } catch (Exception e) {
        }
        return bm;
    }

    /**
     * @param context
     * @return
     */
    public static Bitmap getDefaultAlbumArt(Context context) {
        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            bm = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher, options);
        } catch (Error ee) {
        } catch (Exception e) {
        }
        return bm;
    }

    /**
     * Convert milliseconds into time hh:mm:ss
     *
     * @param milliseconds
     * @return time in String
     */
    public static String getDuration(long milliseconds) {
        long sec = (milliseconds / 1000) % 60;
        long min = (milliseconds / (60 * 1000)) % 60;
        long hour = milliseconds / (60 * 60 * 1000);

        String s = (sec < 10) ? "0" + sec : "" + sec;
        String m = (min < 10) ? "0" + min : "" + min;
        String h = "" + hour;

        String time = "";
        if (hour > 0) {
            time = h + ":" + m + ":" + s;
        } else {
            time = m + ":" + s;
        }
        return time;
    }

    public static boolean currentVersionSupportBigNotification() {
        int sdkVersion = android.os.Build.VERSION.SDK_INT;
        if (sdkVersion >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            return true;
        }
        return false;
    }

    public static boolean currentVersionSupportLockScreenControls() {
        int sdkVersion = android.os.Build.VERSION.SDK_INT;
        if (sdkVersion >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return true;
        }
        return false;
    }
}
