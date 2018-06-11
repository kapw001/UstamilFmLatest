package com.prmobiapp.ustamilfm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Karthik on 11/17/2015.
 */
public class ThreadClass {
     static Bitmap myBitmap;

    public static Bitmap getBitmapFromURL(final String strURL) {

        new Thread(new Runnable() {

            public void run() {
                try {
                    URL url = new URL(strURL);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    myBitmap = BitmapFactory.decodeStream(input);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        }).start();

        return myBitmap;
    }
}
