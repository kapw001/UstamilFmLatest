package com.prmobiapp.ustamilfm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.prmobiapp.ustamilfm.Utils;


public class NotificationBroadcast extends BroadcastReceiver {
	private static String TAG="Headset And Bluetooth";
	@Override
	public void onReceive(Context context, Intent intent) {
//		final String action = intent.getAction();
//		if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {
//
//			if (Utils.mediaPlayingStatus == true) {
//				Intent in = new Intent(context, MyService.class);
//				in.setAction(Utils.NOTIFYPAUSE);
//				context.startService(in);
//
//			}
//			Toast.makeText(context, "Noicy", Toast.LENGTH_LONG).show();
//		}
//		if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
//			int state = intent.getIntExtra("state", -1);
//			switch (state) {
//				case 0:
//					Log.d(TAG, "Headset is unplugged");
//					if (Utils.mediaPlayingStatus == true) {
//						Intent in = new Intent(context, MyService.class);
//						in.setAction(Utils.NOTIFYPAUSE);
//						context.startService(in);
//					}
//					Toast.makeText(context, "Pluged", Toast.LENGTH_LONG).show();
//
//					break;
//				case 1:
//					Log.d(TAG, "Headset is plugged");
//					Toast.makeText(context, "UnPlugged", Toast.LENGTH_LONG).show();
//					break;
//				default:
//					Log.d(TAG, "I have no idea what the headset state is");
//			}
//		}

		if (intent.getAction().equals(Intent.ACTION_MEDIA_BUTTON)) {
            KeyEvent keyEvent = (KeyEvent) intent.getExtras().get(Intent.EXTRA_KEY_EVENT);
            if (keyEvent.getAction() != KeyEvent.ACTION_DOWN)
                return;

            switch (keyEvent.getKeyCode()) {
                case KeyEvent.KEYCODE_HEADSETHOOK:
                case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
					Intent in = new Intent(context, MyService.class);
					if (Utils.mediaPlayingStatus == false) {
						in.setAction(Utils.PLAY);
//                    Utils.playBtnClick=true;
					} else if (Utils.mediaPlayingStatus == true) {
						in.setAction(Utils.PAUSE);
//                    Utils.playBtnClick=false;
					}
					context.startService(in);
                	break;
                case KeyEvent.KEYCODE_MEDIA_PLAY:
                	break;
                case KeyEvent.KEYCODE_MEDIA_PAUSE:
                	break;
                case KeyEvent.KEYCODE_MEDIA_STOP:
                	break;
                case KeyEvent.KEYCODE_MEDIA_NEXT:

                	break;
                case KeyEvent.KEYCODE_MEDIA_PREVIOUS:

                	break;
            }
		}

	}
	
	public String ComponentName() {
		return this.getClass().getName(); 
	}
}
