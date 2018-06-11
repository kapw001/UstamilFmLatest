package com.prmobiapp.ustamilfm.activity;

/**
 * Created by Ravi on 29/07/15.
 */

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import com.prmobiapp.ustamilfm.R;
import com.prmobiapp.ustamilfm.adapter.NavDrawerListAdapter;
import com.prmobiapp.ustamilfm.model.NavDrawerItem;

public class FragmentDrawer extends Fragment {

    private static String TAG = FragmentDrawer.class.getSimpleName();

    private ListView mDrawerList;

    // nav drawer title
    private CharSequence mDrawerTitle;
    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavDrawerListAdapter adapter;
    private View containerView;
    private static String[] titles = null;
    private FragmentDrawerListener drawerListener;
    private static int lastposition=0;
    public FragmentDrawer() {

    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mTitle = mDrawerTitle = getTitle();
        // drawer labels
//        titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);

//        PlayerConstants.SONG_PAUSED = false;
//        Controls.playControl(getActivity());
    }

    @SuppressWarnings("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);



        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerList = (ListView)layout.findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Find People
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Photos
        navDrawerItems.add(new NavDrawerItem("RJ's Profile", navMenuIcons.getResourceId(2, -1)));
        // Communities, Will add a counter here
//        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
//        // Pages
//        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
//        // What's hot, We  will add a counter here
//        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(6, -1)));

//        navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(7, -1)));

//        navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], navMenuIcons.getResourceId(8, -1)));



        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getActivity(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);


        return layout;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar,int lastposition) {
        for (int i=0;i<navDrawerItems.size();i++){
            mDrawerList.setItemChecked(i, false);
//            mDrawerList.setSelection(i);
        }
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
//        displayView(0);
        mDrawerList.setItemChecked(lastposition, true);
        mDrawerList.setSelection(lastposition);
        setTitle(navMenuTitles[lastposition]);
    }



    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
//            displayView(position);

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            drawerListener.onDrawerItemSelected(view, position);
            mDrawerLayout.closeDrawer(containerView);

        }
    }


    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }

    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new FriendsFragment();
                break;
            case 2:
                fragment = new MessagesFragment();
                break;
            case 3:
                fragment = new MessagesFragment();
                break;
            case 4:
                fragment = new MessagesFragment();
                break;
            case 5:
                fragment = new MessagesFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
            android.support.v4.app.FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(containerView);

//            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    public void setTitle(String title) {
        mTitle = title;

//        titelinterface="sddsf";

//        getActivity().getSupportActionBar().setTitle(title);
//        MainActivity.setTitle(title);
//        getSupportActionBar().setTitle(mTitle);
    }
}
