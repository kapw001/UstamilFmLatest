package com.prmobiapp.ustamilfm;

import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Karthik on 3/30/2016.
 */
public class Utils {

    public static ArrayList<MediaItem> SONGS_LIST = new ArrayList<MediaItem>();
    public static int currentPosition = 0;
    public static final String PLAY = "com.example.karthik.fm.PLAY";
    public static final String PAUSE = "com.example.karthik.fm.PAUSE";
    public static final String NOTIFYPLAY = "com.example.karthik.fm.NOTIFYPLAY";
    public static final String NOTIFYPAUSE = "com.example.karthik.fm.NOTIFYPAUSE";
    public static final String PROGRESS = "com.example.karthik.fm.PROGRESS";
    public static final String PLAYPAUSEIMAGECHANGE = "com.example.karthik.fm.PLAYPAUSEIMAGECHANGE";
    public static final String NOTIFYCLOSE = "com.example.karthik.fm.NOTIFYCLOSE";
    public static final String SHOWNOTIFY = "com.example.karthik.fm.SHOWNOTIFY";
    public static final String HIDENOTIFY = "com.example.karthik.fm.HIDENOTIFY";
    public static final String CHANGEPROGRAMIMAGE = "com.example.karthik.fm.CHANGEPROGRAMIMAGE";

    public static final String OPENMAINACTIVITY = "com.example.karthik.fm.OPENMAINACTIVITY";

    public static final String CLOSESPINNER = "com.example.karthik.fm.CLOSESPINNER";

    public static boolean mediaPlayingStatus = false;
    public static boolean notifyPlayPuusebtnstatus=false;
    public static boolean audioFocusChange=false;
    public static boolean isBackGroundPlaying=false;
    public static boolean isNotifyShown=false;
    public static boolean pUpdate=false;
    public static boolean playBtnClick = false;
    public static boolean progressTrue = false;

    public static String notiButtonandProgressChange="Play";



    public static IntentFilter registerIntentFilter() {
        IntentFilter in = new IntentFilter();
        in.addAction(Utils.PLAY);
        in.addAction(Utils.PAUSE);
        in.addAction(Utils.PROGRESS);
        in.addAction(Utils.PLAYPAUSEIMAGECHANGE);
        in.addAction(Utils.NOTIFYCLOSE);
        in.addAction(Utils.NOTIFYPLAY);
        in.addAction(Utils.NOTIFYPAUSE);
        return in;
    }

    public static IntentFilter registerIntentFilterIMAGECHANGE() {
        IntentFilter in = new IntentFilter();
        in.addAction(Utils.CHANGEPROGRAMIMAGE);
        in.addAction(Utils.CLOSESPINNER);
        return in;
    }
    public static Bitmap getBitmapFromURL(String strURL) {
        try {
            Bitmap bmp = BitmapFactory.decodeStream(new URL(strURL).openStream());
            return bmp;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static boolean currentVersionSupportLockScreenControls() {
        int sdkVersion = android.os.Build.VERSION.SDK_INT;
        if (sdkVersion >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return true;
        }
        return false;
    }
}
