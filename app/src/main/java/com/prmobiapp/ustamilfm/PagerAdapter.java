package com.prmobiapp.ustamilfm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import com.prmobiapp.ustamilfm.activity.MainActivity;
import com.prmobiapp.ustamilfm.activity.MovieTeaser;

public class PagerAdapter extends FragmentStatePagerAdapter {
    public static String getUrl;
    int mNumOfTabs;
    private List<TabGet> feedItemList = new ArrayList<TabGet>();
    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }
    public PagerAdapter(FragmentManager fm, int NumOfTabs,List<TabGet> feedItemList) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.feedItemList=feedItemList;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
//            case 0:
//                RequestSongs tab1 = new RequestSongs();
//                return tab1;
//            case 0:
//                MovieTeaser tab1 = new MovieTeaser();
//                return tab1;
//            case 1:
//                BirthdayWishes tab2 = new BirthdayWishes();
//                return tab2;
//            case 2:
//                SubmitKavithi tab3 = new SubmitKavithi();
//                return tab3;
//            case 3:
//                RjHunt tab4 = new RjHunt();
//                return tab4;
//            case 4:
//                AdvertiseMent tab5 = new AdvertiseMent();
//                return tab5;
//            case 5:
//                MovieTrailler tab6 = new MovieTrailler();
//                return tab6;
//            case 6:
//                MovieTrailler tab7 = new MovieTrailler();
//                return tab7;
            default:
                MovieTeaser tab1 = new MovieTeaser();
//                Bundle bundle = new Bundle();
//                bundle.putString("url", MainActivity.feedItemList.get(position).getUrljson());
                getUrl=MainActivity.feedItemList.get(position).getUrljson();
//                tab1.setUrl();
                return tab1;
//                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}