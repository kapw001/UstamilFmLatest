package com.prmobiapp.ustamilfm;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.RemoteControlClient;
import android.os.Build;

import com.prmobiapp.ustamilfm.Utils;

/**
 * Created by Karthik on 4/8/2016.
 */
public class RemoteController {
    private static final String TAG;
    private RemoteControlClient remoteControlClient;
    private ComponentName remoteComponentName;
    private Context context;

    static {
        TAG = "RemoteController";
    }

    public void register(Context context, AudioManager audioManager) {
        this.context = context;
        remoteComponentName = new ComponentName(context.getApplicationContext(), new NotificationBroadcast().ComponentName());
        try {
            if (remoteControlClient == null) {
                audioManager.registerMediaButtonEventReceiver(remoteComponentName);
                Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
                mediaButtonIntent.setComponent(remoteComponentName);
                PendingIntent mediaPendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, mediaButtonIntent, 0);
                remoteControlClient = new RemoteControlClient(mediaPendingIntent);
                audioManager.registerRemoteControlClient(remoteControlClient);
            }
            remoteControlClient.setTransportControlFlags(
                    RemoteControlClient.FLAG_KEY_MEDIA_PLAY_PAUSE);
        } catch (Exception ex) {
        }
    }

    public void UpdateMetadata(MediaItem data) {
        if (remoteControlClient == null)
            return;
        RemoteControlClient.MetadataEditor metadataEditor = remoteControlClient.editMetadata(true);
        metadataEditor.putString(MediaMetadataRetriever.METADATA_KEY_ALBUM, data.getArtist());
        metadataEditor.putString(MediaMetadataRetriever.METADATA_KEY_TITLE, data.getTitle());
        Bitmap bitmap = Utils.getBitmapFromURL(data.getUrlImage());
        metadataEditor.putBitmap(RemoteControlClient.MetadataEditor.BITMAP_KEY_ARTWORK, bitmap);
        metadataEditor.apply();
        lockScreenButtonChange();
    }

    public void lockScreenButtonChange() {
        if (remoteControlClient != null) {
            if (Utils.currentVersionSupportLockScreenControls()) {
                if (Utils.mediaPlayingStatus) {
                    remoteControlClient.setPlaybackState(RemoteControlClient.PLAYSTATE_PLAYING);
                } else {
                    remoteControlClient.setPlaybackState(RemoteControlClient.PLAYSTATE_PAUSED);
                }
            }
        }
    }

    public void release() {
        this.remoteControlClient = null;
    }
}
