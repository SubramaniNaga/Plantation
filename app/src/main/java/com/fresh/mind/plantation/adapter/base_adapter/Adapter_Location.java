package com.fresh.mind.plantation.adapter.base_adapter;

import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.fragment.Inside.LocationSelected;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.fresh.mind.plantation.R.id.cnsLayout1;
import static com.fresh.mind.plantation.R.id.llContactLayout;

/**
 * Created by AND I5 on 17-01-2017.
 */
public class Adapter_Location extends BaseAdapter {
    private final ArrayList<HashMap<String, String>> mLocation;
    private final FragmentActivity mContext;

    public Adapter_Location(FragmentActivity activity, ArrayList<HashMap<String, String>> mLocation) {
        this.mContext = activity;
        this.mLocation = mLocation;
    }

    @Override
    public int getCount() {
        return mLocation.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        // return the total number of view types. this value should never change at runtime
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // return a value between 0 and (getViewTypeCount - 1)
        return position % 2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.adapter_gird, null);
        CustomTextView mSPinnerText = (CustomTextView) convertView.findViewById(R.id.mSPinnerText);

        ImageView imTreeImage = (ImageView) convertView.findViewById(R.id.imTreeImage);
        LinearLayout llContactLayout = (LinearLayout) convertView.findViewById(R.id.llContactLayout);
        mSPinnerText.setText("" + mLocation.get(position).get("District"));


        if (Config.checkMaterial() == 1) {
            llContactLayout.setBackground(mContext.getDrawable(R.drawable.ripple_location));
        }
        mSPinnerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Config.SELECTED_DISTRICT_NAME = mLocation.get(position).get("District");
                        Config.LISTVIEW_SMOOTH_VIEW_POSITION = position;
                        mContext.getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new LocationSelected()).addToBackStack(null).commit();
                    }
                }, 300);
            }
        });

        return convertView;

    }
}
