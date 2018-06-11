package com.prmobiapp.ustamilfm;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {
 
    final String TAG = "JsonParser.java";
 
    static InputStream is = null;
    static JSONArray jObj = null;
    static String json = "";
    String id;
    public JsonParser(String string) {
    	this.id=string;
		// TODO Auto-generated constructor stub
	}

	public JSONArray getJSONFromUrl(String url) {
 
        // make HTTP request
        try {
 
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
            nameValuePair.add(new BasicNameValuePair("id",id));
         //   nameValuePair.add(new BasicNameValuePair("password", "123456789"));
          //Encoding POST data
            try {
                  httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
             
            } catch (UnsupportedEncodingException e)
            {
                 e.printStackTrace();
            }
 
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();           
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        try {
 
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
 
        } catch (Exception e) {
            Log.e(TAG, "Error converting result " + e.toString());
        }
 
        // try parse the string to a JSON object
        try {
            jObj = new JSONArray(json);
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing data " + e.toString());
        }
 
        // return JSON String
        return jObj;
    }
}