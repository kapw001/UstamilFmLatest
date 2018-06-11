package com.prmobiapp.ustamilfm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Toast;

/**
 * Created by Karthik on 11/30/2015.
 */
public class Ring extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
            AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
//            Toast.makeText(context, "Test", Toast.LENGTH_LONG).show();
            if (am.getRingerMode()==AudioManager.RINGER_MODE_SILENT){
                Toast.makeText(context, "Phone Is RINGER_MODE_SILENT", Toast.LENGTH_LONG).show();
            }
            if (am.getRingerMode()==AudioManager.RINGER_MODE_VIBRATE){
                Toast.makeText(context, "Phone Is RINGER_MODE_VIBRATE", Toast.LENGTH_LONG).show();
            }
            if (am.getRingerMode()==AudioManager .RINGER_MODE_NORMAL){
                Toast.makeText(context, "Phone Is RINGER_MODE_NORMAL", Toast.LENGTH_LONG).show();
            }
        if (am.getRingerMode()==AudioManager.STREAM_VOICE_CALL){
            Toast.makeText(context, "Phone Is STREAM_VOICE_CALL", Toast.LENGTH_LONG).show();
        }
    }
}
