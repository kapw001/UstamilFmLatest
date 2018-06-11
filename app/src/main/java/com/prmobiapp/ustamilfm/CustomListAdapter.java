package com.prmobiapp.ustamilfm;

/**
 * Created by karthick on 17-Dec-15.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
//    private final String[] newtext;
    private final Integer[] imgid;

    public CustomListAdapter(Activity context, String[] itemname, Integer[] imgid) {
        super(context, R.layout.mylist, itemname);
        // TODO Auto-generated constructor stub
        this.context=context;
        this.itemname=itemname;
//        this.newtext=newtext;
        this.imgid=imgid;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, parent, false);
//        view = inflater.inflate(R.layout.lview_row, parent, false);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView);

//        ImageView imagearrow = (ImageView) rowView.findViewById(R.id.arrow);


        imageView.setImageResource(imgid[position]);
        extratxt.setText( itemname[position]);

        return rowView;

    };
}