package com.fresh.mind.plantation.fragment.ModelInter_tabs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.fresh.mind.plantation.Constant.AppData;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.adapter.base_adapter.SchemeExpandableListAdapter;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.sqlite.Intercrops;
import com.fresh.mind.plantation.sqlite.LanguageChange;
import com.fresh.mind.plantation.sqlite.server.SchecmesDb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.fresh.mind.plantation.Constant.Config.AGRO_MODE;
import static com.fresh.mind.plantation.R.id.intercroplist;

public class Intercropstab extends Fragment {

    private String languages;
    private String imge;
    private String sch1;

    //   private HashMap<String, List<String>> mIntercropsinfos;
    private HashMap<String, List<String>> mIntercropsinfoslist;
    private Intercrops Intercropsinfo;
    private CustomTextView mNoData;
    private ExpandableListView expandableListView;
    private SchemeExpandableListAdapter itemInterCrops;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.activity_intercropstab, null);
        expandableListView = (ExpandableListView) rootview.findViewById(R.id.expandableListView);
        RecyclerView intercroplist = (RecyclerView) rootview.findViewById(R.id.intercroplist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        intercroplist.setHasFixedSize(true);
        intercroplist.setLayoutManager(layoutManager);
        mNoData = (CustomTextView) rootview.findViewById(R.id.inter_no_data);
        mIntercropsinfoslist = new HashMap<>();
        Intercropsinfo = new Intercrops(getActivity());

        LanguageChange languageChange = new LanguageChange(getActivity());
        languages = languageChange.getStatus();

        HashMap<String, List<String>> mIntercropsinfos = Intercropsinfo.getExpandableModels(AppData.checkLanguage(getActivity()));

        final List<String> expandableListTitle = new ArrayList<>(mIntercropsinfos.keySet());

        Log.d("mIntercropsinfos", "" + mIntercropsinfos);
        Log.d("Modelsssinter", "" + mIntercropsinfos.size() + "   " + expandableListTitle.size());


       /* if (mIntercropsinfos.size() >= 1) {

            itemInterCrops = new SchemeExpandableListAdapter(getActivity(), expandableListTitle, mIntercropsinfos);
            expandableListView.setAdapter(itemInterCrops);
            expandableListView.setVisibility(View.VISIBLE);
            mNoData.setVisibility(View.GONE);

        } else {

            expandableListView.setVisibility(View.GONE);
            mNoData.setVisibility(View.VISIBLE);

        }


        final int[] prevExpandPosition = {-1};
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                                                        @Override
                                                        public void onGroupExpand(int groupPosition) {
                                                            //    Toast.makeText(getActivity(), expandableListTitle.get(groupPosition) + " List Expanded.", Toast.LENGTH_SHORT).show();
                                                            if (prevExpandPosition[0] >= 0 && prevExpandPosition[0] != groupPosition) {
                                                                expandableListView.collapseGroup(prevExpandPosition[0]);
                                                            }
                                                            prevExpandPosition[0] = groupPosition;
                                                        }
                                                    }

        );


        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Log.d("onGroupExpand", "this works?");
                for (int i = 0; i < itemInterCrops.getGroupCount(); i++) {
                    if (i != groupPosition)
                        expandableListView.collapseGroup(groupPosition);
                }
            }
        });

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.d("onGroupClick:", "worked");
                parent.expandGroup(groupPosition);
                return true;
            }
        });*/

        ArrayList<HashMap<String, String>> IntercropsDescription = Intercropsinfo.getIntercropDescription(languages);
        // checkConditionIntercrop(IntercropsDescription);*/
        Log.d("IntercropsDescription", "" + IntercropsDescription.size());
        if (IntercropsDescription.size() >= 1) {
            InterCroplistadapter adapter2 = new InterCroplistadapter(getActivity(), IntercropsDescription);
            intercroplist.setAdapter(adapter2);
            mNoData.setVisibility(View.GONE);
            intercroplist.setVisibility(View.VISIBLE);
        } else {
            mNoData.setVisibility(View.VISIBLE);
            intercroplist.setVisibility(View.GONE);
        }
        return rootview;
    }

    private void checkConditionIntercrop(ArrayList<HashMap<String, String>> InterDescription) {
        Log.d("descriptionLegnthj", "" + InterDescription.size());
        String language = AppData.checkLanguage(getActivity());

        if (language.equals("1")) {
            for (int i = 0; i < InterDescription.size(); i++) {
                sch1 = InterDescription.get(i).get("Description_Tamil");
                Log.d("Descriptionssss", "" + sch1);
                imge = InterDescription.get(i).get("storagePath");
                Log.d("Imgaepathhh", "" + imge);

                HashMap<String, String> modelnfodb = new HashMap<String, String>();
                modelnfodb.put("des", sch1);
                modelnfodb.put("images", imge);
                //  mIntercropsinfoslist.add(modelnfodb);

                Log.d("modeldataintercrop", "" + mIntercropsinfoslist.size());
            }
        } else {
            for (int i = 0; i < InterDescription.size(); i++) {
                String sch12 = InterDescription.get(i).get("Description");
                Log.d("Descriptionssss", "" + sch12);
                String imge2 = InterDescription.get(i).get("storagePath");
                Log.d("Imgaepathhh", "" + imge2);

                HashMap<String, String> modelnfodb = new HashMap<String, String>();
                modelnfodb.put("des", sch12);
                modelnfodb.put("images", imge2);
                // mIntercropsinfoslist.add(modelnfodb);

                Log.d("modeldata", "" + mIntercropsinfoslist.size());
            }
        }
    }

    private class InterCroplistadapter extends RecyclerView.Adapter<InterCroplistadapter.MyHolder> {
        private final FragmentActivity activity1;
        private final ArrayList<HashMap<String, String>> intercrop1;
        private final LayoutInflater inflator;

        public InterCroplistadapter(FragmentActivity activity, ArrayList<HashMap<String, String>> modellistdata) {
            activity1 = activity;
            inflator = LayoutInflater.from(activity);
            intercrop1 = modellistdata;
            Log.d("intercrop1", "" + intercrop1.size());
        }

        @Override
        public InterCroplistadapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(activity1).inflate(R.layout.agroforestitems2, parent, false);
            InterCroplistadapter.MyHolder holder = new InterCroplistadapter.MyHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(InterCroplistadapter.MyHolder holder, final int position) {
            try {
                holder.agrodes.setText(intercrop1.get(position).get("des"));
                String imag = intercrop1.get(position).get("images");
                Bitmap bmb = BitmapFactory.decodeFile(imag);
                holder.agroimages.setImageBitmap(bmb);
                holder.mLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AGRO_MODE = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("des", intercrop1.get(position).get("des"));
                        bundle.putString("class", "inter");
                        VIewAgreoForestryImage vIewAgreoForestryImage = new VIewAgreoForestryImage();
                        vIewAgreoForestryImage.setArguments(bundle);
                        activity1.getSupportFragmentManager().beginTransaction().replace(R.id.container_body, vIewAgreoForestryImage).addToBackStack(null).commit();
                    }
                });

            } catch (OutOfMemoryError outOfMemoryError) {

            }
        }

        @Override
        public int getItemCount() {
            return intercrop1.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {
            CustomTextView agrodes;
            CustomTextView description;
            CircleImageView agroimages;
            public ConstraintLayout mLayout;

            public MyHolder(View itemView) {
                super(itemView);
                agrodes = (CustomTextView) itemView.findViewById(R.id.agrodes);
                agroimages = (CircleImageView) itemView.findViewById(R.id.agroimages);
                mLayout = (ConstraintLayout) itemView.findViewById(R.id.mLayout);

            }
        }
    }

    class ItemInterCrops extends BaseExpandableListAdapter {
        private final FragmentActivity mContext;
        private final HashMap<String, List<String>> expandableListDetail;
        private final List<String> expandableListTitle;

        public ItemInterCrops(FragmentActivity activity, List<String> strings, HashMap<String, List<String>> expandableListDetail) {
            this.mContext = activity;
            this.expandableListTitle = strings;
            this.expandableListDetail = expandableListDetail;
            Log.d("expandableListDetail", "" + expandableListDetail.size() + "   " + expandableListTitle.size());
        }


        @Override
        public int getGroupCount() {
            return expandableListTitle.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return expandableListDetail.get(this.expandableListTitle.get(groupPosition)).size() - 1;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return expandableListTitle.get(groupPosition);
        }

        @Override
        public Object getChild(int listPosition, int expandedListPosition) {
            return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                    .get(expandedListPosition);

        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            Log.d("sdchildPosition", "" + childPosition);
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            final String expandedListText = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.adapter_spinner_1, null);
            }
            final CustomTextView expandedListTextView = (CustomTextView) convertView.findViewById(R.id.mSPinnerText);
            expandedListTextView.setText("\t\t\t\t " + expandedListText);

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            //Log.d("isExpandeddsdklskisLastChild", "" + isLastChild);
            final String expandedListText = (String) getChild(groupPosition, childPosition);
            Log.d("expandedListText", "" + expandedListText);
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.adapter_single_view, null);
            }
            final CustomTextView expandedListTextView = (CustomTextView) convertView.findViewById(R.id.mSPinnerText);
            expandedListTextView.setText(expandedListText);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10, 8, 8, 8);
            convertView.setLayoutParams(lp);

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }
}
