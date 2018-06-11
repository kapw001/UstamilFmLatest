package com.prmobiapp.ustamilfm.imageresize;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by PRMobile on 4/27/16.
 */
public class ProportionalImageView extends ImageView {

    public ProportionalImageView(Context context) {
        super(context);
    }

    public ProportionalImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProportionalImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable d = getDrawable();
        if (d != null) {
            int w = MeasureSpec.getSize(widthMeasureSpec);
            int h = MeasureSpec.getSize(heightMeasureSpec);
            setMeasuredDimension(w, h);
        }
        else super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}