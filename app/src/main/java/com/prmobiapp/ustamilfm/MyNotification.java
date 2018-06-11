package com.prmobiapp.ustamilfm;

import android.app.Notification;
import android.app.Notification.MediaStyle;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.prmobiapp.ustamilfm.Utils;
import com.prmobiapp.ustamilfm.activity.MainActivity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Karthik on 4/7/2016.
 */

public class MyNotification {
    private static final int NOTIFICATION_ID = 999;
    private NotificationCompat.Builder newBuilder;
    private Builder builder;
    private Context context;
    private RemoteViews views, bigViews;
    private PendingIntent pendingIntent;
    private boolean notificationShown;
    private RemoteController mRemoteController;


    private class loadImageTask extends AsyncTask<Void, Void, Bitmap> {
        Context context;
        String imageUrl;

        public loadImageTask(Context context, String imageUrl) {
            this.context = context;
            this.imageUrl = imageUrl;
        }

        protected Bitmap doInBackground(Void... params) {
            if (MyNotification.this.notificationShown) {
                try {
                    Bitmap bmp = BitmapFactory.decodeStream(new URL(this.imageUrl).openStream());
//                    MyNotification.this.mRemoteController.updateImage(bmp);
                    return bmp;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            return null;
        }

        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            if (MyNotification.this.notificationShown) {
                if (result != null) {
                    try {
                        MyNotification.this.views.setImageViewBitmap(R.id.imageViewAlbumArt, result);
                        MyNotification.this.bigViews.setImageViewBitmap(R.id.imageViewAlbumArt, result);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
                MyNotification.this.showNotification();
            }
        }
    }

    public MyNotification(Context context, RemoteController mRemoteController) {
        this.context = context;
        this.mRemoteController = mRemoteController;
        this.notificationShown = false;
    }

    public boolean isNotificationShown() {
        return this.notificationShown;
    }

    public void hideNotification() {
        this.notificationShown = false;
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(NOTIFICATION_ID);
    }

    public void showLRNotification() {
        if (Utils.SONGS_LIST.size() > 0) {
            this.notificationShown = true;
            MediaItem m = Utils.SONGS_LIST.get(Utils.currentPosition);
            textNbuttons(m.getArtist(), m.getTitle());
            showNotification();
            this.mRemoteController.UpdateMetadata(m);
            new loadImageTask(context, m.getUrlImage()).execute(new Void[0]);
        }
    }

    public void updateNotification() {
        updateButtons();
        showNotification();
    }

    private void textNbuttons(String progName, String rjName) {
        views = new RemoteViews(context.getPackageName(),
                R.layout.custom_notification);
        bigViews = new RemoteViews(context.getPackageName(),
                R.layout.big_notification);
        int requestID = (int) System.currentTimeMillis();
        Intent notificationIntent = new Intent(context, MainActivity.class);
//        notificationIntent.setAction(Utils.OPENMAINACTIVITY);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        pendingIntent = PendingIntent.getActivity(context, 0,
//                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent previousIntent = new Intent(context, MyService.class);
        previousIntent.setAction(Utils.NOTIFYPLAY);
        PendingIntent play = PendingIntent.getService(context, requestID,
                previousIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent playIntent = new Intent(context, MyService.class);
        playIntent.setAction(Utils.NOTIFYPAUSE);
        PendingIntent pause = PendingIntent.getService(context, requestID,
                playIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent closeIntent = new Intent(context, MyService.class);
        closeIntent.setAction(Utils.NOTIFYCLOSE);
        PendingIntent close = PendingIntent.getService(context, requestID,
                closeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

//        views.setOnClickPendingIntent(R.id.noti, pendingIntent);
//        bigViews.setOnClickPendingIntent(R.id.noti, pendingIntent);

        views.setOnClickPendingIntent(R.id.btnPlay, play);
        bigViews.setOnClickPendingIntent(R.id.btnPlay, play);

        views.setOnClickPendingIntent(R.id.btnPause, pause);
        bigViews.setOnClickPendingIntent(R.id.btnPause, pause);

        views.setOnClickPendingIntent(R.id.btnDelete, close);
        bigViews.setOnClickPendingIntent(R.id.btnDelete, close);

        views.setTextViewText(R.id.textSongName, progName);
        bigViews.setTextViewText(R.id.textSongName, progName);

        views.setTextViewText(R.id.textAlbumName, rjName);
        bigViews.setTextViewText(R.id.textAlbumName, rjName);

        if (VERSION.SDK_INT < 21) {
            this.builder = new Builder(context);
            this.builder.setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent).setOngoing(true).setAutoCancel(true).setVisibility(View.VISIBLE);
        } else {


            if (VERSION.SDK_INT > 25) {
                int notifyID = 1;
                String CHANNEL_ID = "my_channel_01";// The id of the channel.
                CharSequence name = "UsTamilFM";// The user-visible name of the channel.
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);

                this.newBuilder = new NotificationCompat.Builder(context).setChannelId(CHANNEL_ID).setSmallIcon(R.mipmap.black).setContentIntent(pendingIntent).setOngoing(true).setAutoCancel(true).setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()).setVisibility(View.VISIBLE);

            } else {
                this.newBuilder = new NotificationCompat.Builder(context).setSmallIcon(R.mipmap.black).setContentIntent(pendingIntent).setOngoing(true).setAutoCancel(true).setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()).setVisibility(View.VISIBLE);
            }


        }
        this.notificationShown = true;
        updateButtons();
    }

    public void updateButtons() {
        String message = Utils.notiButtonandProgressChange;
        Log.e("Test", "updateButtons: Test " + message);

        if (message.contentEquals("Play")) {
            views.setViewVisibility(R.id.btnPlay, View.INVISIBLE);
            bigViews.setViewVisibility(R.id.btnPlay, View.INVISIBLE);

            views.setViewVisibility(R.id.btnProgress, View.INVISIBLE);
            bigViews.setViewVisibility(R.id.btnProgress, View.INVISIBLE);

            views.setViewVisibility(R.id.btnPause, View.VISIBLE);
            bigViews.setViewVisibility(R.id.btnPause, View.VISIBLE);
//            views.setImageViewResource(R.id.btnPause, R.drawable.pause);
//            bigViews.setImageViewResource(R.id.btnPause, R.drawable.pause);
        } else if (message.contentEquals("Pause")) {
            views.setViewVisibility(R.id.btnPlay, View.VISIBLE);
            bigViews.setViewVisibility(R.id.btnPlay, View.VISIBLE);

            views.setViewVisibility(R.id.btnPause, View.INVISIBLE);
            bigViews.setViewVisibility(R.id.btnPause, View.INVISIBLE);

            views.setViewVisibility(R.id.btnProgress, View.INVISIBLE);
            bigViews.setViewVisibility(R.id.btnProgress, View.INVISIBLE);
//            views.setImageViewResource(R.id.btnPause, R.drawable.play);
//            bigViews.setImageViewResource(R.id.btnPause, R.drawable.play);
        } else if (message.contentEquals("Progress")) {
            Log.e("HI", "run: " + message);
            views.setViewVisibility(R.id.btnPlay, View.INVISIBLE);
            bigViews.setViewVisibility(R.id.btnPlay, View.INVISIBLE);

            views.setViewVisibility(R.id.btnPause, View.INVISIBLE);
            bigViews.setViewVisibility(R.id.btnPause, View.INVISIBLE);

            views.setViewVisibility(R.id.btnProgress, View.VISIBLE);
            bigViews.setViewVisibility(R.id.btnProgress, View.VISIBLE);
        }


//        if (Utils.mediaPlayingStatus) {
//            views.setViewVisibility(R.id.btnPlay, View.INVISIBLE);
//            bigViews.setViewVisibility(R.id.btnPlay, View.INVISIBLE);
//            views.setViewVisibility(R.id.btnPause, View.VISIBLE);
//            bigViews.setViewVisibility(R.id.btnPause, View.VISIBLE);
//        } else {
//            views.setViewVisibility(R.id.btnPlay, View.VISIBLE);
//            bigViews.setViewVisibility(R.id.btnPlay, View.VISIBLE);
//            views.setViewVisibility(R.id.btnPause, View.INVISIBLE);
//            bigViews.setViewVisibility(R.id.btnPause, View.INVISIBLE);
//        }
    }


    private void showNotification() {
        Notification n;
        if (VERSION.SDK_INT < 21) {
            n = this.builder.build();
            if (VERSION.SDK_INT >= 16) {
                n.contentView = this.views;
                n.bigContentView = this.bigViews;
            } else {
                n.contentView = this.views;
            }
        } else {
            n = newBuilder.build();
            n.contentView = this.views;
            n.bigContentView = this.bigViews;
        }

        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "UsTamilFM";// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;


        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mNotificationManager.createNotificationChannel(mChannel);
        }

        mNotificationManager.notify(NOTIFICATION_ID, n);

    }
}
