package com.rtsoftbd.siddiqui.hellocar.CustomAdapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rtsoftbd.siddiqui.hellocar.R;

/**
 * Created by RTsoftBD_Siddiqui on 2017-04-26.
 */

public class PageAdapter extends PagerAdapter {
    private Activity activity;

    public PageAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View page= activity.getLayoutInflater().inflate(R.layout.page, container, false);
        TextView tv=(TextView)page.findViewById(R.id.text);

        position=position * 2;

        populateTextView(tv, position);
        position++;
        tv=(TextView)page.findViewById(R.id.text2);

        if (position < getRealCount()) {
            populateTextView(tv, position);
            tv.setVisibility(View.VISIBLE);
        }
        else {
            tv.setVisibility(View.INVISIBLE);
        }

        container.addView(page);

        return(page);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    private int getRealCount() {
        return(4);
    }

    private void populateTextView(TextView tv, int position) {
        int blue=position * 25;

        tv.setText(String.format("Hello ", position + 1));
        tv.setBackgroundColor(Color.argb(255, 0, 0, blue));
    }

    @Override
    public int getCount() {
        return((getRealCount() / 2) + 1);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return(view == object);
    }
}
