package com.fresh.mind.plantation.fragment.menu_page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.sqlite.LanguageChange;

import static com.fresh.mind.plantation.R.id.tvNodata;

/**
 * Created by AND I5 on 08-07-2017.
 */
public class PlantationCalculation extends Fragment {
    private View rootView;
    private CustomTextView tvNodata;
    private String languages;
    private String[] models;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ((MainActivity) getActivity()).setPlantationCalc(getActivity().getResources().getString(R.string.plantationCalculation));
        rootView = inflater.inflate(R.layout.plant_calculation, null);
        MainActivity.menuItem.setVisible(false);
        MainActivity.menuItem1.setVisible(false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rvCommon);
        tvNodata = (CustomTextView) rootView.findViewById(R.id.tvNodata);
        tvNodata.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        LanguageChange languageChange = new LanguageChange(getActivity());
        languages = languageChange.getStatus();
        if (languages.equals("1")) {
            Utils.setLocalLanguage("ta", getActivity());
            models = new String[]{"விதைப்பு எண்", "தொகுதி மதிப்பீடு"};
//            , "கார்பன் பங்கு கணக்கிடுதல்"
        } else {
            Utils.setLocalLanguage("en", getActivity());
            models = new String[]{"Number Of Seeding", "Volume Estimation"};
//            , "Carbon Stock Calculation"
        }
        AdapterPlantationCalculation
                adapter = new AdapterPlantationCalculation(getActivity(), models);
        recyclerView.setAdapter(adapter);
        return rootView;

    }

    class AdapterPlantationCalculation extends RecyclerView.Adapter<HolderAgro> {
        private final String[] values;
        private final FragmentActivity activity;

        public AdapterPlantationCalculation(FragmentActivity activity, String[] i) {
            this.activity = activity;
            this.values = i;
        }

        @Override
        public HolderAgro onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(activity).inflate(R.layout.adapter_calcultaion, parent, false);
            return new HolderAgro(view);
        }

        @Override
        public void onBindViewHolder(HolderAgro holder, int position) {
            holder.mSPinnerText.setText(values[position]);
            if (languages.equals("1")) {
                holder.tvSubText.setText("தோட்டக்கலை கணக்கிடுதலக்கு சில உப உள்ளடக்கங்களை வழங்குக...");
            } else {
                holder.tvSubText.setText("Sub Content for Plantation Calculation...");

            }
            final String element = values[position];
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(activity, element, Toast.LENGTH_SHORT).show();

                }
            });
        }

        @Override
        public int getItemCount() {
            return values.length;
        }
    }

    class HolderAgro extends RecyclerView.ViewHolder {
        CustomTextView mSPinnerText;
        CustomTextView tvSubText;

        public HolderAgro(View itemView) {
            super(itemView);
            mSPinnerText = (CustomTextView) itemView.findViewById(R.id.mSPinnerText);
            tvSubText = (CustomTextView) itemView.findViewById(R.id.tvSubText);
        }
    }
}
