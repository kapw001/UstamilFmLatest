package com.prmobiapp.ustamilfm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;

import static com.prmobiapp.ustamilfm.R.id.imageView;

public class ProgramViewActivity extends Activity {
    ImageView bigimage;
    TextView tamildestxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//        actionBar.setDisplayShowHomeEnabled(false);
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setDisplayUseLogoEnabled(false);
//        getSupportActionBar().hide();
//        requestWindowFeature(Window.FEATURE_ACTION_BAR);
//        getActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_program_view);

        bigimage = (ImageView) findViewById(R.id.programbigimage);


        tamildestxt = (TextView) findViewById(R.id.tamildes);
        Intent pi = getIntent();
        String urlimage = pi.getStringExtra("imgae");
        String tamildes = pi.getStringExtra("tamildes");
        tamildestxt.setText(tamildes);
//        byte[] data = Base64.decode(tamildes, Base64.DEFAULT);
//        try {
//            String text = new String(data, "UTF-8");
//            tamildestxt.setText(text);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

//        Picasso.with(this).load(urlimage)
//                .into(bigimage);

        Glide.with(this).load(urlimage).thumbnail(.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(bigimage);


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}
