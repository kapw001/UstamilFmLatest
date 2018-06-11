package com.prmobiapp.ustamilfm.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import com.prmobiapp.ustamilfm.MediaItem;
import com.prmobiapp.ustamilfm.ProgramViewActivity;
import com.prmobiapp.ustamilfm.R;

/**
 * Created by Karthik on 11/13/2015.
 */
public class FeedListRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    protected ImageView thumbnail,faviorprogram;
    protected TextView programshowname,programrjname,programshowtime;
    private List<MediaItem> feedItemList;
    private Context mContext;
    CardView itemview;
    RelativeLayout colorchange;
    public FeedListRowHolder(View view,List<MediaItem> feedItemList,Context context) {
        super(view);
        view.setOnClickListener(this);
        this.feedItemList = feedItemList;
        this.mContext = context;
        this.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        this.faviorprogram = (ImageView) view.findViewById(R.id.faviorprogram);
        this.programshowname = (TextView) view.findViewById(R.id.programshowname);
        this.programrjname=(TextView) view.findViewById(R.id.programrjname);
        this.programshowtime=(TextView) view.findViewById(R.id.textView4);
        this.itemview=(CardView)view.findViewById(R.id.programitemsview);
        this.colorchange=(RelativeLayout) view.findViewById(R.id.colorchange);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.faviorprogram){
//            MediaItem feedItem = feedItemList.get(getPosition());
//            sharedpreferences s=new sharedpreferences(mContext);
//            if(feedItem.getfavorite().equalsIgnoreCase("false")){
//                this.faviorprogram.setImageResource(R.drawable.fav_red);
//                feedItem.setfavorite("true");
//            }else if(feedItem.getfavorite().equalsIgnoreCase("true")){
//                this.faviorprogram.setImageResource(R.drawable.favorites);
//                feedItem.setfavorite("false");
//            }
//            Toast.makeText(v.getContext(), "position = " + feedItem.getfavorite(), Toast.LENGTH_SHORT).show();
        }else{
            MediaItem feedItem = feedItemList.get(getPosition());
            Intent i=new Intent(mContext,ProgramViewActivity.class);
            i.putExtra("imgae",feedItem.getUrlImage());
            i.putExtra("tamildes",feedItem.getTamilDes());
            mContext.startActivity(i);
        }

//        Toast.makeText(v.getContext(), "position = " + feedItem.getArtist(), Toast.LENGTH_SHORT).show();
    }
}