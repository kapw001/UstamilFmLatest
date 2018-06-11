package com.prmobiapp.ustamilfm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapterRequestBoxFragment extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapterRequestBoxFragment(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

//        switch (position) {
//            case 0:
////                RequestSongs tab1 = new RequestSongs();
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
////            case 5:
////                MovieTrailler tab6 = new MovieTrailler();
////                return tab6;
////            case 6:
////                MovieTrailler tab7 = new MovieTrailler();
////                return tab7;
//            default:
//                return null;
//        }

        return null;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}