package com.prmobiapp.ustamilfm.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.prmobiapp.ustamilfm.MovieTraillerItem;
import com.prmobiapp.ustamilfm.R;
import com.prmobiapp.ustamilfm.YoutubePlayer;

/**
 * Created by Karthik on 11/13/2015.
 */
public class MovieFeedListRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    protected ImageView movieteaserimage;
    protected TextView moviename,moviesubname;
    private List<MovieTraillerItem> feedItemList;
    private Context mContext;
    public MovieFeedListRowHolder(View view, List<MovieTraillerItem> feedItemList, Context context) {
        super(view);
        view.setOnClickListener(this);
        this.feedItemList = feedItemList;
        this.mContext = context;
        this.movieteaserimage = (ImageView) view.findViewById(R.id.movieteaserimage);
        this.moviename = (TextView) view.findViewById(R.id.moviename);
        this.moviesubname=(TextView) view.findViewById(R.id.moviesubname);
    }

    @Override
    public void onClick(View v) {
        MovieTraillerItem feedItem = feedItemList.get(getPosition());
        Intent i=new Intent(mContext,YoutubePlayer.class);
        i.putExtra("video",feedItem.getVideo());
        mContext.startActivity(i);
//       Toast.makeText(v.getContext(), "position = " + feedItem.getVideo(), Toast.LENGTH_SHORT).show();
    }
}