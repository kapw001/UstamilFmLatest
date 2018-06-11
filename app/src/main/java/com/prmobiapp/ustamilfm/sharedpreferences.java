package com.prmobiapp.ustamilfm;

        import android.content.Context;
        import android.content.SharedPreferences;

/**
 * Created by Karthik on 12/7/2015.
 */
public class sharedpreferences {
    private static final String MY_PREFS_NAME = "MyPrefsFile";
    private static SharedPreferences.Editor editor;
    public static Context c;
    public sharedpreferences(Context c){
        this.c=c;
    }

    public static void savepre(String s,String ss){
        editor = c.getSharedPreferences(MY_PREFS_NAME, c.MODE_PRIVATE).edit();
        editor.putString(s, ss);
        editor.commit();
    }

    public static String getValues(String s){
        SharedPreferences prefs = c.getSharedPreferences(MY_PREFS_NAME, c.MODE_PRIVATE);
        String restoredText = prefs.getString(s, null);
        return restoredText;

    }

}
