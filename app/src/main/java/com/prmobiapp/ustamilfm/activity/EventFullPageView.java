package com.prmobiapp.ustamilfm.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import com.prmobiapp.ustamilfm.R;

public class EventFullPageView extends Activity {
   ImageView eventfullimageshow;
    Button b;
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_event_full_page_view);
        eventfullimageshow=(ImageView)findViewById(R.id.eventfullimageshow);
        b=(Button)findViewById(R.id.eventb);

        t=(TextView)findViewById(R.id.eventtextview);
        final Intent gg=getIntent();

        if (gg.getStringExtra("uporcom").equalsIgnoreCase("Upcoming")){
            b.setText("FOR MORE INFORMATION");
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(gg.getStringExtra("moreinfo")));
                    startActivity(i);
                }
            });

        }else if(gg.getStringExtra("uporcom").equalsIgnoreCase("Completed")){
            b.setText("DONE");
            b.setVisibility(View.GONE);

        }


        Picasso.with(this).load(gg.getStringExtra("image"))
//                .error(R.drawable.placeholder)
//                .placeholder(R.drawable.placeholder)
                .into(eventfullimageshow);
        t.setText(Html.fromHtml(gg.getStringExtra("des")));

    }
    @Override
    protected void onResume() {
        super.onResume();
//        if (SongService.mp!=null){
//            if(SongService.mp.isPlaying()){
//                Controls.nresumeControl(this);
//
//            }}
    }
}
