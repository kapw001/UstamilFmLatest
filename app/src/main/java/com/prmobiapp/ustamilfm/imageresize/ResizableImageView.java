package com.prmobiapp.ustamilfm.imageresize;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by PRMobile on 4/27/16.
 */
public class ResizableImageView extends ImageView {
    public ResizableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable d = getDrawable();

        if (d != null) {

            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);

            if (width >= height) {
                width = (int) Math.ceil((float) height * (float) d.getIntrinsicWidth() / (float) d.getIntrinsicHeight());
            } else {
                height = (int) Math.ceil((float) width * (float) d.getIntrinsicHeight() / (float) d.getIntrinsicWidth());
            }

            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}