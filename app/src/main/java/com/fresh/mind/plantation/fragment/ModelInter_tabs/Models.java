package com.fresh.mind.plantation.fragment.ModelInter_tabs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.widget.LinearLayout;

import com.fresh.mind.plantation.Constant.AppData;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.fragment.menu_page.AgroForestry;
import com.fresh.mind.plantation.sqlite.LanguageChange;
import com.fresh.mind.plantation.sqlite.server.Modelinfo;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.fresh.mind.plantation.R.id.modellists;
import static com.fresh.mind.plantation.R.id.tvNodata;

public class Models extends Fragment {

    private String languages;
    private String imge;
    private CustomTextView mNoData;
    private Modelinfo modelinfo;
    private ArrayList<HashMap<String, String>> modellistdata;
    private String sch1;
    private ArrayList<HashMap<String, String>> mModelinfos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.activity_models, null);

        RecyclerView modellistsrecycle = (RecyclerView) rootview.findViewById(modellists);
        mNoData = (CustomTextView) rootview.findViewById(R.id.model_no_data);
        modellistdata = new ArrayList<>();

        modelinfo = new Modelinfo(getActivity());


        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
        modellistsrecycle.setLayoutManager(linearLayoutManager1);
        LanguageChange languageChange = new LanguageChange(getActivity());
        languages = languageChange.getStatus();
//        Utils.setLocalLanguage("en", getActivity());

        mModelinfos = modelinfo.getDescription(AppData.checkLanguage(getActivity()));
        ArrayList<HashMap<String, String>> Description = modelinfo.getDescription(languages);
        checkCondition(Description);
        Log.d("modellistdata",""+modellistdata.size());
        if (modellistdata.size() >= 1) {
            Modellistadapter adapter1 = new Modellistadapter(getActivity(), modellistdata);
            modellistsrecycle.setAdapter(adapter1);
            modellistsrecycle.setVisibility(View.VISIBLE);
            mNoData.setVisibility(View.GONE);
        } else {
            modellistsrecycle.setVisibility(View.GONE);
            mNoData.setVisibility(View.VISIBLE);
        }
        return rootview;
    }

    private void checkCondition(ArrayList<HashMap<String, String>> Description) {
        //  Log.d("descriptionLegnthj", "" + Description.size());
        String language = AppData.checkLanguage(getActivity());

        if (language.equals("1")) {
            for (int i = 0; i < Description.size(); i++) {
                sch1 = Description.get(i).get("Description_Tamil");
                //Log.d("Descriptionssss", "" + sch1);
                imge = Description.get(i).get("storagePath");
                // Log.d("Imgaepathhh", "" + imge);

                HashMap<String, String> modelnfodb = new HashMap<String, String>();
                modelnfodb.put("des", sch1);
                modelnfodb.put("images", imge);
                modellistdata.add(modelnfodb);

                //Log.d("modeldata", "" + modellistdata);
            }
        } else {
            for (int i = 0; i < Description.size(); i++) {
                sch1 = Description.get(i).get("Description");
                // Log.d("Descriptionssss", "" + sch1);
                imge = Description.get(i).get("storagePath");
                // Log.d("Imgaepathhh", "" + imge);

                HashMap<String, String> modelnfodb = new HashMap<String, String>();
                modelnfodb.put("des", sch1);
                modelnfodb.put("images", imge);
                modellistdata.add(modelnfodb);

                //Log.d("modeldata", "" + modellistdata);
            }
        }
    }


    private class Modellistadapter extends RecyclerView.Adapter<Modellistadapter.MyHolder> {
        private final FragmentActivity activity1;
        private final ArrayList<HashMap<String, String>> modellistdata1;


        public Modellistadapter(FragmentActivity activity, ArrayList<HashMap<String, String>> modellistdata) {
            activity1 = activity;

            modellistdata1 = modellistdata;
        }

        @Override
        public Modellistadapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(activity1).inflate(R.layout.agroforestitems, parent, false);
            Modellistadapter.MyHolder holder = new Modellistadapter.MyHolder(view);
            return holder;
        }


        @Override
        public int getItemViewType(int position) {
            return position % 2;
        }

        @Override
        public void onBindViewHolder(Modellistadapter.MyHolder holder, int position) {
            holder.agrodes.setText(modellistdata1.get(position).get("des"));
            String imag = modellistdata1.get(position).get("images");
            //Log.d("imag", "" + imag);
            if (imag != null) {
                try {
                    Picasso.with(activity1).load((Uri.fromFile(new File(imag)))).error(R.drawable.logo_3).into(holder.agroimages);
                    Picasso.with(activity1).load((Uri.fromFile(new File(imag)))).error(R.drawable.logo_3).into(holder.agroimages2);
                    /*Picasso.with(activity1).load((Uri.fromFile(new File(imag)))).placeholder(R.drawable.logo_3).error(R.drawable.logo_3).into(holder.agroimages);
                    Picasso.with(activity1).load((Uri.fromFile(new File(imag)))).placeholder(R.drawable.logo_3).error(R.drawable.logo_3).into(holder.agroimages2);*/
                } catch (OutOfMemoryError outOfMemoryError) {
                    outOfMemoryError.printStackTrace();
                    activity1.getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new AgroForestry()).commit();
                }
            } else {
                holder.agroimages.setImageResource(R.drawable.logo_3);
                holder.agroimages2.setImageResource(R.drawable.logo_3);
            }

            if (getItemViewType(position) == 1) {
                holder.first.setVisibility(View.GONE);
                holder.second.setVisibility(View.VISIBLE);
            } else {
                holder.first.setVisibility(View.VISIBLE);
                holder.second.setVisibility(View.GONE);
            }

        /*    Bitmap bmb= BitmapFactory.decodeFile(imag);
            holder.agroimages.setImageBitmap(bmb);*/
        }

        @Override
        public int getItemCount() {
            return modellistdata.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {
            CustomTextView agrodes;
            CustomTextView description;
            CircleImageView agroimages, agroimages2;
            LinearLayout first, second;

            public MyHolder(View itemView) {
                super(itemView);
                first = (LinearLayout) itemView.findViewById(R.id.first);
                second = (LinearLayout) itemView.findViewById(R.id.second);
                agrodes = (CustomTextView) itemView.findViewById(R.id.agrodes);
                agroimages2 = (CircleImageView) itemView.findViewById(R.id.agroimages2);
                agroimages = (CircleImageView) itemView.findViewById(R.id.agroimages);

            }
        }
    }
}
