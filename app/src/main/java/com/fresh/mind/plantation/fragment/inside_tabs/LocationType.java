package com.fresh.mind.plantation.fragment.inside_tabs;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.fragment.Inside.LocationSelected;
import com.fresh.mind.plantation.sqlite.LanguageChange;
import com.fresh.mind.plantation.sqlite.server.DistrictNameList;
import com.fresh.mind.plantation.sqlite.sorting.SortOrder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.fresh.mind.plantation.Constant.Config.LISTVIEW_SMOOTH_VIEW_POSITION;

/**
 * Created by AND I5 on 13-01-2017.
 */
public class LocationType extends Fragment {

    private View rootView;
    public static ArrayList<HashMap<String, String>> mLocation;
    private CustomTextView mNodata;
    private DistrictNameList districtNameList;
    private SortOrder sortOrder;
    //private ImageMap imageMaping;
    //private StaggeredGridLayoutManager stgaggeredGridLayoutManager;
    private int[] mDistrictImages = {R.drawable.d_ariyalur, R.drawable.d_chennai, R.drawable.d_coimbatore, R.drawable.d_cuddalore, R.drawable.d_dharmapuri, R.drawable.d_dindigul, R.drawable.d_erode,
            R.drawable.d_kanchipuram, R.drawable.d_kanyakumari, R.drawable.d_karur, R.drawable.d_krishnagiri, R.drawable.d_madurai, R.drawable.d_nagapattinam, R.drawable.d_namakkal,
            R.drawable.d_nilgiris, R.drawable.d_perambalur, R.drawable.d_pudukottai, R.drawable.d_ramanathapuram, R.drawable.d_salem, R.drawable.d_sivagangai, R.drawable.d_thanjavur,
            R.drawable.d_theni, R.drawable.d_thiruvallur, R.drawable.d_thiruvannamalai, R.drawable.d_thiruvarur, R.drawable.d_thoothukudi, R.drawable.d_tiruchirapalli, R.drawable.d_tirunelvelli,
            R.drawable.d_tiruppur, R.drawable.d_vellore, R.drawable.d_vilupuram, R.drawable.d_virudhunagar};

    /*  [{District=Ariyalur}, {District=Chennai}, {District=Coimbatore}, {District=Cudalore}, {District=Dharmapuri}, {District=Dindigul}, {District=Erode}, {District=Kanchipuram},
      {District=Kanyakumari}, {District=Karur}, {District=Krishnagiri}, {District=Madurai}, {District=Nagapattinam}, {District=Namakkal}, {District=Nilgiris}, {District=Perambalur},
      {District=Pudukottai}, {District=Ramanathapuram}, {District=Salem}, {District=Sivagangai}, {District=Thanjavur}, {District=Theni}, {District=Thiruvallur}, {District=Thiruvannamalai},
      {District=Thiruvarur}, {District=Thoothukudi}, {District=Tiruchirapalli}, {District=Tirunelveli}, {District=Tiruppur}, {District=Tirupur-test}, {District=Vellore}, {District=Vilupuram}, {District=Virudhunagar}]
  */
    private int[] mDistrictImagesTamil = {R.drawable.d_ariyalur, R.drawable.d_erode, R.drawable.d_cuddalore, R.drawable.d_kanyakumari, R.drawable.d_karur, R.drawable.d_kanchipuram, R.drawable.d_krishnagiri,
            R.drawable.d_coimbatore, R.drawable.d_sivagangai, R.drawable.d_chennai, R.drawable.d_salem, R.drawable.d_thanjavur, R.drawable.d_dharmapuri, R.drawable.d_dindigul,
            R.drawable.d_tiruchirapalli, R.drawable.d_tirunelvelli, R.drawable.d_tiruppur, R.drawable.d_thiruvannamalai, R.drawable.d_thiruvallur, R.drawable.d_thiruvarur, R.drawable.d_thoothukudi,
            R.drawable.d_theni, R.drawable.d_nagapattinam, R.drawable.d_namakkal, R.drawable.d_nilgiris, R.drawable.d_pudukottai, R.drawable.d_perambalur, R.drawable.d_madurai, R.drawable.d_ramanathapuram,
            R.drawable.d_virudhunagar, R.drawable.d_vilupuram, R.drawable.d_vellore};

    @Override
    public void onDestroy() {
        super.onDestroy();
        districtNameList.close();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        LanguageChange languageChange = new LanguageChange(getActivity());
        String languages = languageChange.getStatus();
        if (languages.equals("1")) {
            Utils.setLocalLanguage("ta", getActivity());
        } else {
            Utils.setLocalLanguage("en", getActivity());
        }
        //((MainActivity) getActivity()).setLocation(getActivity().getString(R.string.location), "Choose By Location");
        rootView = inflater.inflate(R.layout.plant_location_type, null);


        RecyclerView rvDistrictName = (RecyclerView) rootView.findViewById(R.id.rvDistrictName);
        rvDistrictName.setHasFixedSize(true);

        //stgaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, 1);
        GridLayoutManager gaggeredGridLayoutManager = new GridLayoutManager(getActivity(), 3);
        rvDistrictName.setLayoutManager(gaggeredGridLayoutManager);
      /*  imageMaping = (ImageMap) rootView.findViewById(R.id.imageMaping);

        imageMaping.setImageResource(R.drawable.tn_map);*/

        mNodata = (CustomTextView) rootView.findViewById(R.id.mNodata);
        districtNameList = new DistrictNameList(getActivity());
        sortOrder = new SortOrder(getActivity());
        String bySortType = sortOrder.getSort();
        mLocation = districtNameList.getDistrictNames(languages);
        //Log.d("locationssss", mDistrictImages.length + "  " + districtNameList.getDistrictNames(languages));

        Config.SELECTED_ID = 0;
        if (mLocation.size() >= 1) {

            Collections.sort(mLocation, new Comparator<HashMap<String, String>>() {
                @Override
                public int compare(HashMap<String, String> s1, HashMap<String, String> s2) {
                    return s1.get("District").compareTo(s2.get("District"));
                }
            });
            /*if (bySortType != null) {
                if (bySortType.equals("Z-A")) {
                    Collections.sort(mLocation, new Comparator<HashMap<String, String>>() {
                        @Override
                        public int compare(HashMap<String, String> s1, HashMap<String, String> s2) {
                            return s2.get("District").compareTo(s1.get("District"));
                        }
                    });
                } else {
                    Collections.sort(mLocation, new Comparator<HashMap<String, String>>() {
                        @Override
                        public int compare(HashMap<String, String> s1, HashMap<String, String> s2) {
                            return s1.get("District").compareTo(s2.get("District"));
                        }
                    });
                }
            }*/

            mNodata.setVisibility(View.GONE);

            //locationListView.setAdapter(new Adapter_Location(getActivity(), mLocation));
            rvDistrictName.setVisibility(View.VISIBLE);
            Log.d("sdlksldskdl", "" + mDistrictImagesTamil.length + "  Name " + mLocation.size() + mLocation + "\n  dsds " + mDistrictImages.length);
            if (languages.equals("1")) {
                rvDistrictName.setAdapter(new AdapterDistrictName(getActivity(), mLocation, mDistrictImagesTamil));
            } else {

                rvDistrictName.setAdapter(new AdapterDistrictName(getActivity(), mLocation, mDistrictImages));
            }
        } else {
            mNodata.setVisibility(View.VISIBLE);

            rvDistrictName.setVisibility(View.GONE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            LISTVIEW_SMOOTH_VIEW_POSITION = 0;
        }
/*
        imageMaping.addOnImageMapClickedHandler(new ImageMap.OnImageMapClickedHandler() {
            @Override
            public void onImageMapClicked(int id, ImageMap imageMap) {
                Log.d("akllaskas", "" + id);
                //imageMaping.showBubble(id);
                //imageMaping.pass(id);

            }

            @Override
            public void onBubbleClicked(int id) {
                Log.d("onBubbleClicked", "" + id);
            }
        });*/
        return rootView;
    }

    class AdapterDistrictName extends RecyclerView.Adapter<SolventViewHolders> {
        private final FragmentActivity mContext;
        private final ArrayList<HashMap<String, String>> listViewValues;
        private final int[] mDistrictImages;

        public AdapterDistrictName(FragmentActivity activity, ArrayList<HashMap<String, String>> mLocation, int[] mDistrictImages) {
            this.mContext = activity;
            this.listViewValues = mLocation;
            this.mDistrictImages = mDistrictImages;
        }

        @Override
        public SolventViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext.getApplicationContext()).inflate(R.layout.adapter_gird, null);
            return new SolventViewHolders(view);
        }


        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onBindViewHolder(final SolventViewHolders holder, final int position) {

            //if (position % 2 == 0) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(8, 8, 8, 8);
            holder.llContactLayout.setLayoutParams(lp);
            /*} else {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(8, 4, 8, 4);
                holder.llContactLayout.setLayoutParams(lp);
            }*/

            if (Config.checkMaterial() == 1) {
                holder.llContactLayout.setBackground(mContext.getDrawable(R.drawable.ripple_location));
            }

            holder.mSPinnerText.setText("" + listViewValues.get(position).get("District"));
            //holder.imTreeImage.setImageResource(R.drawable.logo_3);

            Picasso.with(mContext).load(mDistrictImages[position]).into(holder.imTreeImage);
                        /*Glide.with(mContext).load((""+mDistrictImages[position])).asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.imTreeImage) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    holder.imTreeImage.setImageDrawable(circularBitmapDrawable);
                }
            });*/

            holder.llContactLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Config.SELECTED_DISTRICT_NAME = listViewValues.get(position).get("District");
                    Config.LISTVIEW_SMOOTH_VIEW_POSITION = position;
                    mContext.getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new LocationSelected()).addToBackStack(null).commit();

                }
            });
        }

        @Override
        public int getItemCount() {
            return listViewValues.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position % 2;
        }

    }

    class SolventViewHolders extends RecyclerView.ViewHolder {
        public CustomTextView mSPinnerText;
        public CircleImageView imTreeImage;
        public LinearLayout llContactLayout;

        public SolventViewHolders(View convertView) {
            super(convertView);
            mSPinnerText = (CustomTextView) convertView.findViewById(R.id.mSPinnerText);
            imTreeImage = (CircleImageView) convertView.findViewById(R.id.imTreeImage);
            llContactLayout = (LinearLayout) convertView.findViewById(R.id.llContactLayout);
        }
    }
}

