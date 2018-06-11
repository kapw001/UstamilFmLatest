package com.prmobiapp.ustamilfm.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.prmobiapp.ustamilfm.PagerAdapter;
import com.prmobiapp.ustamilfm.R;
import com.prmobiapp.ustamilfm.TabGet;

/**
 * Created by Ravi on 29/07/15.
 */
public class NewBroadcastFragment extends Fragment {
    private static final String TAG = "RecyclerViewExample";
//    private List<TabGet> feedItemList = new ArrayList<TabGet>();


    public NewBroadcastFragment() {
        // Required empty public constructor
    }

//    public NewBroadcastFragment(List<TabGet> feedItemList) {
//        // Required empty public constructor
//        this.feedItemList = feedItemList;
//    }
    public void setList(List<TabGet> feedItemList){
//        this.feedItemList = feedItemList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);

        if (MainActivity.feedItemList.size() > 0) {
            for (int i = 0; i < MainActivity.feedItemList.size(); i++) {
                TabGet t = MainActivity.feedItemList.get(i);
                tabLayout.addTab(tabLayout.newTab().setText(t.getTabname()));
            }
        }
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 6xzcvxczvxzc"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getFragmentManager(), tabLayout.getTabCount(), MainActivity.feedItemList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
