package com.prmobiapp.ustamilfm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.view.KeyEvent;
import android.widget.Toast;


public class NotificationBecomeNoisyBroadcast extends BroadcastReceiver {
	private static String TAG="Headset And Bluetooth";
	@Override
	public void onReceive(Context context, Intent intent) {
//		final String action = intent.getAction();
		if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {

			if (Utils.mediaPlayingStatus == true) {
				Intent in = new Intent(context, MyService.class);
				in.setAction(Utils.NOTIFYPAUSE);
				context.startService(in);

			}
			Toast.makeText(context, "Noicy", Toast.LENGTH_LONG).show();
		}
	}
}
