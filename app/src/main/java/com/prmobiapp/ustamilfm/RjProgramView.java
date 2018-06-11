package com.prmobiapp.ustamilfm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

public class RjProgramView extends Activity {

    TextView rname,showname,rprograms;
    ImageView im;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rj_program_view);
        im=(ImageView)findViewById(R.id.rimageshow);
        rname=(TextView)findViewById(R.id.rname);
        showname=(TextView)findViewById(R.id.showname);
        rprograms=(TextView)findViewById(R.id.rprograms);
        Intent gg=getIntent();
        rname.setText(Html.fromHtml(gg.getStringExtra("name")));
        showname.setText(Html.fromHtml(gg.getStringExtra("showname")));
        rprograms.setText(Html.fromHtml(gg.getStringExtra("programs")));
//        Picasso.with(this).load(gg.getStringExtra("image"))
//                .into(im);

        Glide.with(this).load(gg.getStringExtra("image")).thumbnail(.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(im);
    }
}
