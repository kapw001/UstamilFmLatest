package com.prmobiapp.ustamilfm;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.prmobiapp.ustamilfm.activity.Aapplication;
import com.prmobiapp.ustamilfm.activity.MainActivity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Karthik on 3/30/2016.
 */
public class MyServiceBackup extends Service implements AudioManager.OnAudioFocusChangeListener {
    private static final String TAG = "MyService";
    private static final int ONE_MINUTE_IN_MILLIS = 60000;
    private MediaListner mediaListner;
    private MediaPlayer mediaPlayer;
    private RemoteController mRemoteController;
    private Handler handler;
    private static SimpleDateFormat dateFormat;
    private static Date EndTime;
    private static Date miEndTime;
    private static Date EndTime1;
    private static Date miEndTime1;
    private static Date CurrentTime;
    private static Date miCurrentTime;
    private static String s;
    private static String e;
    private Timer timer;
    private AudioManager audioManager;
    private MyNotification myNotification;
    private NotificationBecomeNoisyBroadcast notificationBroadcast;
    private BroadcastReceiver receiver;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: ");

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.media.AUDIO_BECOMING_NOISY");
//        filter.addAction("SOME_OTHER_ACTION");

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //do something based on the intent's action
                if (Utils.mediaPlayingStatus == true) {
                    Intent in = new Intent(context, MyServiceBackup.class);
                    in.setAction(Utils.NOTIFYPAUSE);
                    context.startService(in);

                }
//                Toast.makeText(context, "Noicy Test", Toast.LENGTH_LONG).show();
            }
        };
        registerReceiver(receiver, filter);


//        notificationBroadcast = new NotificationBecomeNoisyBroadcast();
//
//        IntentFilter commandFilter = new IntentFilter();
//        commandFilter.addAction("android.media.AUDIO_BECOMING_NOISY");
////        commandFilter.addAction(Intent.ACTION_HEADSET_PLUG);
////        commandFilter.addAction(Intent.ACTION_HEADSET_PLUG);
////        commandFilter.addAction(Intent.ACTION_HEADSET_PLUG);
////        commandFilter.addAction(Intent.ACTION_HEADSET_PLUG);
////        commandFilter.addAction(Intent.ACTION_HEADSET_PLUG);
////        commandFilter.addAction(Intent.ACTION_HEADSET_PLUG);
//        registerReceiver(notificationBroadcast, commandFilter);

        mediaListner = new MediaListner();
        handler = new Handler();
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
//        audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        this.mRemoteController = new RemoteController();
        this.mRemoteController.register(getApplicationContext(), audioManager);
        myNotification = new MyNotification(this, this.mRemoteController);
        timer = new Timer();
    }

    private class MainTask extends TimerTask {
        @Override
        public void run() {
            try {
                doWork();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }


    private void doWork() throws ParseException {
        if (Utils.SONGS_LIST.size() > 0) {

//            Date dt = new Date();
//            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
////            sdf.setTimeZone(TimeZone.getTimeZone("EST"));
//
//            Log.e(TAG, "doWork: "+sdf.getTimeZone().getDisplayName() +"   "+sdf.getTimeZone().getRawOffset() +"   "+sdf.getTimeZone().getDSTSavings() +"   "+sdf.getTimeZone().getID());
//            String time1 = sdf.format(dt);
//
//            Log.e(TAG, "doWork: " + time1);

            try {


                ArrayList<MediaItem> SONGS_LIST = Utils.SONGS_LIST;
                int size = SONGS_LIST.size();

                for (int i = 0; i < size; i++) {
                    MediaItem f = SONGS_LIST.get(i);
                    s = f.getsTime();
                    e = f.geteTime();
                    dateFormat = new SimpleDateFormat("h:mm a");
//                dateFormat.setTimeZone(TimeZone.getTimeZone("CST6CDT"));
                    EndTime = dateFormat.parse(s);
                    miEndTime = new Date(EndTime.getTime());
                    EndTime1 = dateFormat.parse(e);
                    miEndTime1 = new Date(EndTime1.getTime() - (1 * ONE_MINUTE_IN_MILLIS));

//                    Log.e(TAG, "doWork: " + miEndTime1.getTime());

                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
                    String time1 = sdf.format(miEndTime1);

                    Log.e(TAG, "doWork: " + time1);


                    CurrentTime = dateFormat.parse(dateFormat.format(new Date()));
                    miCurrentTime = new Date(CurrentTime.getTime());
                    if (miCurrentTime.after(miEndTime) && miCurrentTime.before(miEndTime1)) {
                        if (f.isF()) {
                            final int finalI = i;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    h.sendEmptyMessage(finalI);
                                    Utils.currentPosition = finalI;
                                    if (myNotification.isNotificationShown()) {
                                        myNotification.hideNotification();
                                        myNotification.showLRNotification();
                                    }
                                }
                            });
                            f.setF(false);
                        }
//
                    }


                }

                int count = 0;

                for (int i = 0; i < size; i++) {
                    MediaItem f = SONGS_LIST.get(i);
                    if (!f.isF()) {
                        count++;
                    }
                }

                if (count == size) {
                    for (int i = 0; i < size; i++) {
                        Utils.SONGS_LIST.get(i).setF(true);
                    }
                }

//                Log.e(TAG, "doWork: " + count + "   " + Utils.SONGS_LIST.size());

            } catch (IndexOutOfBoundsException e) {

                Log.e(TAG, "doWork: " + e.getMessage());
            }


        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: ");

//        timer.scheduleAtFixedRate(new MainTask(), 0, 10);


        if (intent != null) {
            if (intent.getAction() != null) {
                if (intent.getAction().equals(Utils.CHANGEPROGRAMIMAGE)) {
                    timer.scheduleAtFixedRate(new MainTask(), 0, 100);
                } else if (intent.getAction().equals(Utils.PLAY)) {
                    PlayMusic();
                    Utils.notifyPlayPuusebtnstatus = true;
                } else if (intent.getAction().equals(Utils.PAUSE)) {
                    StopMusic();
                    Utils.notifyPlayPuusebtnstatus = false;
                    Utils.audioFocusChange = false;
                } else if (intent.getAction().equals(Utils.PLAYPAUSEIMAGECHANGE)) {
                    if (Utils.mediaPlayingStatus == false) {
                        sendMessage("Pause", Utils.PAUSE);
                    } else {
                        sendMessage("Play", Utils.PLAY);
                    }
                } else if (intent.getAction().equals(Utils.PROGRESS)) {
                    sendMessage("Progress", Utils.PROGRESS);
                } else if (intent.getAction().equals(Utils.SHOWNOTIFY)) {
                    Utils.isNotifyShown = true;
                    if (Utils.playBtnClick) {
                        if (Utils.isNotifyShown) {
                            myNotification.showLRNotification();
                        }
                    }
                } else if (intent.getAction().equals(Utils.HIDENOTIFY)) {
                    myNotification.hideNotification();
                    Utils.isNotifyShown = false;
                    if (Utils.playBtnClick) {
                        Utils.mediaPlayingStatus = true;
                    }
                } else if (intent.getAction().equals(Utils.NOTIFYPLAY)) {
                    PlayMusic();
                    Log.e(TAG, "onStartCommand: PLAY");
                    Utils.audioFocusChange = true;

                } else if (intent.getAction().equals(Utils.NOTIFYPAUSE)) {

                    StopMusic();
                    Utils.audioFocusChange = false;
                    Log.e(TAG, "onStartCommand: NOTIPAUSE");
                } else if (intent.getAction().equals(Utils.NOTIFYCLOSE)) {

                    Utils.playBtnClick = false;
                    Utils.mediaPlayingStatus = false;
                    stopSelfDestroy();
                    Utils.audioFocusChange = false;
                    Log.e(TAG, "onStartCommand: NOTIFYCLOSE");
                } else if (intent.getAction().equals(Utils.OPENMAINACTIVITY)) {
//                    Toast.makeText(this, "Activity", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(this, MainActivity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(in);
                }
            }
        }
        return Service.START_STICKY;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        Log.e(TAG, "onTrimMemory: " + level);
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                if (mediaPlayer == null && Utils.audioFocusChange == true) {
                    Intent in1 = new Intent(getApplicationContext(), MyServiceBackup.class);
                    in1.setAction(Utils.PLAY);
                    startService(in1);
                }

                Log.i(TAG, "AUDIOFOCUS_GAIN");
//                Toast.makeText(this, "Focus GAINED", Toast.LENGTH_LONG).show();
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                Log.i(TAG, "AUDIOFOCUS_LOSS");
                StopMusic();
//                Toast.makeText(this, "Focus LOST", Toast.LENGTH_LONG).show();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                StopMusic();
//                Intent in = new Intent(getApplicationContext(), MyService.class);
//                in.setAction(Utils.PAUSE);
//                Utils.mediaPlayingStatus = false;
//                startService(in);
                Log.i(TAG, "AUDIOFOCUS_LOSS_TRANSIENT");
//                Toast.makeText(this, "Focus LOST TRANSIENT", Toast.LENGTH_LONG).show();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                Log.i(TAG, "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
//                Toast.makeText(this, "Focus LOST TRANSIENT CAN DUCK", Toast.LENGTH_LONG).show();
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
        stopSelfDestroy();
    }

    public void stopSelfDestroy() {
        StopMusic();
        stopSelf();
        if (myNotification.isNotificationShown()) {
            myNotification.hideNotification();
        }
        this.mRemoteController.release();
        timer.cancel();
        handler.removeCallbacksAndMessages(null);
        h.removeCallbacksAndMessages(null);

        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }

        audioManager.abandonAudioFocus(this);
//        if (notificationBroadcast!=null){
//            unregisterReceiver(notificationBroadcast);
//        }

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        stopSelfDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void PlayMusic() {
        audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        Aapplication.getInstance().trackEvent("Play", "Playing", "Track event example");
        Utils.notiButtonandProgressChange = "Progress";
        if (myNotification.isNotificationShown()) {
            myNotification.updateNotification();
//            mRemoteController.lockScreenButtonChange();
        }
        Utils.playBtnClick = true;
        sendMessage("Progress", Utils.PROGRESS);
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer = null;
                    }
                    mediaPlayer = new MediaPlayer();
//                    http://streaming.radio.co/s0cfc93915/listen
//                    http://173.192.205.177:80
                    mediaPlayer.setDataSource("http://streaming.radio.co/s0cfc93915/listen");
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setOnPreparedListener(mediaListner);
                    mediaPlayer.setOnErrorListener(mediaListner);
                    mediaPlayer.setOnCompletionListener(mediaListner);
                    mediaPlayer.setOnBufferingUpdateListener(mediaListner);
                    mediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void StopMusic() {
        Aapplication.getInstance().trackEvent("Stop", "Stop...", "Track event example");
        Utils.playBtnClick = false;
        if (mediaPlayer != null) {
            mediaPlayer.setOnPreparedListener(null);
            mediaPlayer.stop();
            mediaPlayer.release();
//            mediaPlayer.reset();
            mediaPlayer = null;

        }
        Utils.mediaPlayingStatus = false;
        Utils.notiButtonandProgressChange = "Pause";
        sendMessage("Pause", Utils.PAUSE);
        if (myNotification.isNotificationShown()) {
            myNotification.updateNotification();
            mRemoteController.lockScreenButtonChange();
        }
    }

    private void sendMessage(final String name, final String action) {
        handler.post(new Runnable() {
            @Override
            public void run() {
//                Utils.notiButtonandProgressChange = name;
                Log.e(TAG, "run: " + Utils.notiButtonandProgressChange);

                Log.e("sender", "Broadcasting message");
                Intent intent = new Intent(action);
                intent.putExtra("message", name);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
            }
        });

    }

    private Handler h = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            final MediaItem m = Utils.SONGS_LIST.get(msg.what);
            Intent intent = new Intent(Utils.CHANGEPROGRAMIMAGE);
            intent.putExtra("bgimage", m.getUrlImage());
            intent.putExtra("rjname", m.getArtist());
            intent.putExtra("program", m.getTitle());
            intent.putExtra("rjimage", m.getRjimage());
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
            return false;
        }
    });

    public void T() {
        Utils.notiButtonandProgressChange = "Play";
        Utils.mediaPlayingStatus = true;
        sendMessage("Play", Utils.PLAY);
        Log.e(TAG, "onPrepared: ");
        if (myNotification.isNotificationShown()) {
            myNotification.updateNotification();
            mRemoteController.lockScreenButtonChange();
        }
    }

    private class MediaListner implements MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {
        private static final String TAG = "MediaListner";

        @Override
        public void onPrepared(MediaPlayer mp) {
            mp.start();
            T();
        }

        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {

        }

        @Override
        public void onCompletion(MediaPlayer mp) {
            Log.e(TAG, "onCompletion: ");
        }

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            StopMusic();
//            Toast.makeText(getApplicationContext(), "No Internet Connection.", Toast.LENGTH_SHORT).show();
            return false;
        }

    }


}
