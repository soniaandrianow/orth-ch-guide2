package com.example.sofia.orth_ch_guide;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Sofia on 28.10.2016.
 */
public class CustomSwipeAdapter extends PagerAdapter{

    Church ch;
    private Context context;

    public CustomSwipeAdapter(Context context, Church ch){
        this.ch = ch;
        this.context=context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int [] images = ch.image;

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = inflater.inflate(R.layout.swipe_layout, container, false);
        ImageView imageView = (ImageView)item_view.findViewById(R.id.image_view);
        imageView.setImageResource(images[position]);
        container.addView(item_view);

        return  item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
