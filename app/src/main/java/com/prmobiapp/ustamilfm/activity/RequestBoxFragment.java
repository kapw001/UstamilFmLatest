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

import com.prmobiapp.ustamilfm.PagerAdapterRequestBoxFragment;
import com.prmobiapp.ustamilfm.R;

/**
 * Created by Ravi on 29/07/15.
 */
public class RequestBoxFragment extends Fragment {
    private static final String TAG = "RecyclerViewExample";



    public RequestBoxFragment() {
        // Required empty public constructor
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
//        Toolbar toolbar = (Toolbar)rootView.findViewById(R.id.toolbar);
//        getActivity().setActionBar(toolbar);//setSupportActionBar(toolbar);
        TabLayout tabLayout = (TabLayout)rootView. findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Request Songs"));
        tabLayout.addTab(tabLayout.newTab().setText("Birthday Wishes"));
        tabLayout.addTab(tabLayout.newTab().setText("Submit Kavithi"));
        tabLayout.addTab(tabLayout.newTab().setText("Rj Hunt"));
        tabLayout.addTab(tabLayout.newTab().setText("Advertisement"));

//        tabLayout.addTab(tabLayout.newTab().setText("Tab 6xzcvxczvxzc"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager)rootView.findViewById(R.id.pager);
        final PagerAdapterRequestBoxFragment adapter = new PagerAdapterRequestBoxFragment
                (getFragmentManager(), tabLayout.getTabCount());
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
