package com.fresh.mind.plantation.fragment.menu_page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.customized.RippleView;
import com.fresh.mind.plantation.sqlite.LanguageChange;

/**
 * Created by AND I5 on 25-01-2018.
 */

public class Calculation extends Fragment {
    private RippleView mEstimateAverage, mEstimate;
    private TextInputEditText mRadious, mHeight, mFarmFactor, mAverage, mLength;
    private CustomTextView calcAv, calc;
    private String languages, values;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        LanguageChange languageChange = new LanguageChange(getActivity());
        languages = languageChange.getStatus();
        Bundle bundle = getArguments();
        values = bundle.getString("values");

        if (languages.equals("1")) {
            Utils.setLocalLanguage("ta", getActivity());
        } else {
            Utils.setLocalLanguage("en", getActivity());
        }

        ((MainActivity) getActivity()).viewTreeDetails("" + values, "Calculation");
        return inflater.inflate(R.layout.calculation_form, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mEstimate = (RippleView) view.findViewById(R.id.estimateRadious);
        mRadious = (TextInputEditText) view.findViewById(R.id.radious);
        mHeight = (TextInputEditText) view.findViewById(R.id.height);
        mFarmFactor = (TextInputEditText) view.findViewById(R.id.farmFactor);
        mEstimateAverage = (RippleView) view.findViewById(R.id.estimateAverage);
        mAverage = (TextInputEditText) view.findViewById(R.id.average);
        mLength = (TextInputEditText) view.findViewById(R.id.length);

        calcAv = (CustomTextView) view.findViewById(R.id.calcAv);
        calc = (CustomTextView) view.findViewById(R.id.calc);


        mEstimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String height = "", radious = "", farmFactory = "";
                radious = mHeight.getText().toString();
                height = mHeight.getText().toString();
                farmFactory = mHeight.getText().toString();
                if (!radious.isEmpty()) {
                    if (!height.isEmpty()) {
                        if (!farmFactory.isEmpty()) {
                            double radis = Double.parseDouble(radious);
                            double heiht = Double.parseDouble(height);
                            double farmfactor = Double.parseDouble(farmFactory);
                            Log.d("sdsdlskd", "  " + radis + " dsd " + heiht + " far " + farmFactory);

                            calc.setText("Estimation Of : " + (int) Math.PI * Math.pow(radis, 2) * heiht * farmfactor);

                        } else {
                            Toast.makeText(getActivity(), getActivity().getString(R.string.farm_factor), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), getActivity().getString(R.string.height_toast), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getActivity().getString(R.string.radious_toast), Toast.LENGTH_SHORT).show();
                }
            }
        });

        mEstimateAverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String average = "", length = "";
                average = mAverage.getText().toString();
                length = mLength.getText().toString();

                if (!average.isEmpty()) {
                    if (!length.isEmpty()) {
                        double ave = Double.parseDouble(average);
                        double lenth = Double.parseDouble(length);
                        Log.d("sdsdlsddsdkd", "  " + ave + " dsd " + lenth);
                        calcAv.setText("Estimation Of : " + (int) Math.pow(ave / 4, 2) * lenth);

                        //Integer.valueOf(D.intValue()
                    } else {
                        Toast.makeText(getActivity(), getActivity().getString(R.string.length_toast), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), getActivity().getString(R.string.average_toast), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
