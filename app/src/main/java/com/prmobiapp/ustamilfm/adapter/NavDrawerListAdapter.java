package com.prmobiapp.ustamilfm.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.prmobiapp.ustamilfm.R;
import com.prmobiapp.ustamilfm.model.NavDrawerItem;

public class NavDrawerListAdapter extends BaseAdapter {
	private static int selectedIndex;
	private Context context;
	public static String txtTitle;
	String drawcount="0";
	private ArrayList<NavDrawerItem> navDrawerItems;
	
	public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems){
		this.context = context;
		this.navDrawerItems = navDrawerItems;
	}

	@Override
	public int getCount() {
		return navDrawerItems.size();

	}

	@Override
	public Object getItem(int position) {
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	public static void setSelectedIndex(int ind) {
		selectedIndex = ind;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }
         
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        TextView txtCount = (TextView) convertView.findViewById(R.id.counter);
//		setSelectedIndex(position);

        imgIcon.setImageResource(navDrawerItems.get(position).getIcon());        
        txtTitle.setText(navDrawerItems.get(position).getTitle());
		txtTitle.setTextColor(Color.BLACK);
//		if (position == selectedIndex) {
//			txtTitle.setTextColor(Color.WHITE);
//		}
//		else {
//			txtTitle.setTextColor(Color.BLACK);
//		}
//		if (drawcount.equals(position)){
//			Log.e("asasasasasas", "count");
//		}
        // displaying count
        // check whether it set visible or not
        if(navDrawerItems.get(position).getCounterVisibility()){
        	txtCount.setText(navDrawerItems.get(position).getCount());
			Toast.makeText(context,"1",Toast.LENGTH_SHORT).show();
		}else{
        	// hide the counter view
        	txtCount.setVisibility(View.INVISIBLE);
//			Toast.makeText(context,"2",Toast.LENGTH_SHORT).show();

//			String e="visiiiii";
//			Log.e("vvvvvvvvvvvvvvvv", "Source File Does not exist");
		}
        
        return convertView;
	}

}
