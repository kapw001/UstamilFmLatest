package com.prmobiapp.ustamilfm.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.prmobiapp.ustamilfm.MovieTraillerItem;
import com.prmobiapp.ustamilfm.R;
import com.prmobiapp.ustamilfm.RjProgramView;

/**
 * Created by Karthik on 11/13/2015.
 */
public class RjProfileFeedListRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    protected ImageView movieteaserimage;
    protected Button rjclick;
    protected TextView moviename,moviesubname;
    private List<MovieTraillerItem> feedItemList;
    private Context mContext;
    public RjProfileFeedListRowHolder(View view, List<MovieTraillerItem> feedItemList, Context context) {
        super(view);
        view.setOnClickListener(this);
        this.feedItemList = feedItemList;
        this.mContext = context;
        this.movieteaserimage = (ImageView) view.findViewById(R.id.movieteaserimage);
        this.moviename = (TextView) view.findViewById(R.id.moviename);
        this.moviesubname=(TextView) view.findViewById(R.id.moviesubname);
        this.rjclick=(Button) view.findViewById(R.id.rjbutton);
    }

    @Override
    public void onClick(View v) {
        MovieTraillerItem feedItem = feedItemList.get(getPosition());
        if (!feedItem.getSubname().equalsIgnoreCase("Click Here To Apply")){
        Intent i=new Intent(mContext,RjProgramView.class);
        i.putExtra("name",feedItem.getName());
        i.putExtra("image",feedItem.getThumimage());
        i.putExtra("showname",feedItem.getSubname());
        i.putExtra("programs", feedItem.getPrograms());
        mContext.startActivity(i);
        }else{
//            Intent i=new Intent(mContext,RJhuntActivity.class);
//            mContext.startActivity(i);
        }
//       Toast.makeText(v.getContext(), "position = " + feedItem.getVideo(), Toast.LENGTH_SHORT).show();
    }
}