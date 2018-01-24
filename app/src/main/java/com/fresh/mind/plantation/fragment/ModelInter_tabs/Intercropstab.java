package com.fresh.mind.plantation.fragment.ModelInter_tabs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fresh.mind.plantation.Constant.AppData;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.fragment.menu_page.AgroForestry;
import com.fresh.mind.plantation.sqlite.Intercrops;
import com.fresh.mind.plantation.sqlite.LanguageChange;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.id;
import static com.fresh.mind.plantation.R.id.modellists;

public class Intercropstab extends Fragment {

    private String languages;
    private String imge;
    private String sch1;

    private ArrayList<HashMap<String, String>> mIntercropsinfos;
    private ArrayList<HashMap<String, String>> mIntercropsinfoslist;
    private Intercrops Intercropsinfo;
    private CustomTextView mNoData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.activity_intercropstab, null);

        RecyclerView intercroplist = (RecyclerView) rootview.findViewById(R.id.intercroplist);
        mNoData = (CustomTextView) rootview.findViewById(R.id.inter_no_data);
        mIntercropsinfoslist = new ArrayList<>();
        Intercropsinfo = new Intercrops(getActivity());

        LanguageChange languageChange = new LanguageChange(getActivity());
        languages = languageChange.getStatus();

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
        intercroplist.setLayoutManager(linearLayoutManager2);


        mIntercropsinfos = Intercropsinfo.getIntercropDescription(AppData.checkLanguage(getActivity()));

        Log.d("Modelsssinter", "" + mIntercropsinfos.size());

        ArrayList<HashMap<String, String>> IntercropsDescription = Intercropsinfo.getIntercropDescription(languages);
        checkConditionIntercrop(IntercropsDescription);

        if (mIntercropsinfoslist.size() >= 1) {
            InterCroplistadapter adapter2 = new InterCroplistadapter(getActivity(), mIntercropsinfoslist);
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
                mIntercropsinfoslist.add(modelnfodb);

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
                mIntercropsinfoslist.add(modelnfodb);

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
        }

        @Override
        public InterCroplistadapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(activity1).inflate(R.layout.agroforestitems2, parent, false);
            InterCroplistadapter.MyHolder holder = new InterCroplistadapter.MyHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(InterCroplistadapter.MyHolder holder, int position) {
            holder.agrodes.setText(intercrop1.get(position).get("des"));
            String imag = intercrop1.get(position).get("images");
            Bitmap bmb = BitmapFactory.decodeFile(imag);
            holder.agroimages.setImageBitmap(bmb);
        }

        @Override
        public int getItemCount() {
            return intercrop1.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {
            CustomTextView agrodes;
            CustomTextView description;
            CircleImageView agroimages;

            public MyHolder(View itemView) {
                super(itemView);
                agrodes = (CustomTextView) itemView.findViewById(R.id.agrodes);
                agroimages = (CircleImageView) itemView.findViewById(R.id.agroimages);

            }
        }
    }
}
