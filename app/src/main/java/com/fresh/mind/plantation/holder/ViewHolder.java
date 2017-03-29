package com.fresh.mind.plantation.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fresh.mind.plantation.R;

/**
 * Created by AND I5 on 02-02-2017.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    public CardView cardView1, cardView2;
    public TextView treeName, mSubName, mPart2Title, mPart2SubName;
    public ImageView imageView, mPart2Img;

    public ViewHolder(View convertView) {
        super(convertView);
        cardView1 = (CardView) convertView.findViewById(R.id.cardView2);
        cardView2 = (CardView) convertView.findViewById(R.id.cardView3);

        treeName = (TextView) convertView.findViewById(R.id.mTreeName);
        mSubName = (TextView) convertView.findViewById(R.id.mSubName);
        imageView = (ImageView) convertView.findViewById(R.id.imageView2);
        mPart2Title = (TextView) convertView.findViewById(R.id.textView2);
        mPart2SubName = (TextView) convertView.findViewById(R.id.textView4);
        mPart2Img = (ImageView) convertView.findViewById(R.id.imageView3);
    }
}
