package com.me.mseotsanyana.mande.UTIL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.mseotsanyana.mande.R;

/**
 * Created by mseotsanyana on 2016/11/02.
 */
public class cGridAdapter extends BaseAdapter{
    private Context mContext;
    private final String[] mText;
    private final int[] mImageid;

    public cGridAdapter(Context c, String[] text, int[] Imageid) {
        mContext = c;
        this.mImageid = Imageid;
        this.mText = text;
    }

    @Override
    public int getCount() {
        return mText.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.grid_cell, null);
        } else {
            grid = convertView;
        }

        ImageView imageView = null;//(ImageView)grid.findViewById(R.id.grid_image);
        TextView textView = (TextView) grid.findViewById(R.id.grid_text);
        textView.setText(mText[position]);
        imageView.setImageResource(mImageid[position]);

        return grid;
    }
}
