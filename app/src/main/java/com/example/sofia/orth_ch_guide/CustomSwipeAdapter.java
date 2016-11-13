package com.example.sofia.orth_ch_guide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Sofia on 28.10.2016.
 */
public class CustomSwipeAdapter extends PagerAdapter{

    Church ch;
    private Context context;
    ImageView imageView;

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
        String [] images = ch.photos.split(",");
        /*String [] images = {"http://imageshack.com/a/img921/4706/EZ3F2x.jpg",
        "http://imageshack.com/a/img923/4738/1DlTc3.jpg",
        "http://imageshack.com/a/img923/4030/7FcbCi.jpg"};*/

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = inflater.inflate(R.layout.swipe_layout, container, false);
        imageView = (ImageView)item_view.findViewById(R.id.image_view);
        URL url = null;
        Bitmap bmp = null;
        new DownloadImageTask().execute(images[position]);
        container.addView(item_view);
        return  item_view;
    }

    Bitmap loadImageFromNetwork(String url){
        Bitmap ibmp = null;
        try {
            URL iurl = new URL(url);
            ibmp = BitmapFactory.decodeStream(iurl.openConnection().getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ibmp;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }

    private class DownloadImageTask extends AsyncTask <String, Void, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            return loadImageFromNetwork(urls[0]);
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }




}
