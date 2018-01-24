package com.fresh.mind.plantation.fragment.menu_page;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.customized.CustomEditeText;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.sqlite.LanguageChange;

import static com.fresh.mind.plantation.R.id.textView20;
import static com.fresh.mind.plantation.activity.MainActivity.menuItem;
import static java.lang.Double.parseDouble;

/**
 * Created by AND I5 on 13-01-2017.
 */
public class ToolsSettings extends Fragment {

    private View rootView;
    private CustomEditeText areaEdit, enterAreaEdt1, enterAreEdt2;
    private CustomTextView caluculate, reset, estimateTxt, enstimateArea;
    private Spinner areaTypeSpn;
    String[] mSpinner = {"Acre", "Sq feet", "Hectare"};
    String area = "", ent1 = "", ent2 = "", spnr = "";
    int posi = -1;

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
        ((MainActivity) getActivity()).setTools(getActivity().getResources().getString(R.string.tools));
        rootView = inflater.inflate(R.layout.plant_tools, null);
        MainActivity.menuItem.setVisible(false);
        MainActivity.menuItem1.setVisible(false);
        areaTypeSpn = (Spinner) rootView.findViewById(R.id.areaTypeSpn);
        caluculate = (CustomTextView) rootView.findViewById(textView20);
        reset = (CustomTextView) rootView.findViewById(R.id.textView21);
        areaEdit = (CustomEditeText) rootView.findViewById(R.id.areaEdit);
        enterAreaEdt1 = (CustomEditeText) rootView.findViewById(R.id.enterAreaEdt1);
        enterAreEdt2 = (CustomEditeText) rootView.findViewById(R.id.enterAreEdt2);
        areaTypeSpn.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.adapter_spinner, R.id.mSPinnerText, mSpinner));
        enstimateArea = (CustomTextView) rootView.findViewById(R.id.enstimateArea);
        estimateTxt = (CustomTextView) rootView.findViewById(R.id.estimateTxt);
        setVisiable(View.GONE);
        areaEdit.setText("");
        enterAreaEdt1.setText("");
        enterAreEdt2.setText("");

        Config.SELECTED_TAB_VIEW_POSITION = 0;
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            // Do something for lollipop and above versions

            caluculate.setBackground(getActivity().getDrawable(R.drawable.riiple_oval));
            reset.setBackground(getActivity().getDrawable(R.drawable.ripple_reject));
        } else {
            // do something for phones running an SDK before lollipop

        }
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                areaEdit.setText("");
                enterAreaEdt1.setText("");
                enterAreEdt2.setText("");
                setVisiable(View.GONE);
            }
        });
        caluculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate(0);
            }

        });
        areaEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                calculate(1);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        enterAreEdt2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                calculate(1);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        enterAreaEdt1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                calculate(1);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return rootView;
    }

    private void calculate(int btn) {
        posi = areaTypeSpn.getSelectedItemPosition();
        spnr = mSpinner[posi];
        area = areaEdit.getText().toString().trim();
        ent1 = enterAreaEdt1.getText().toString().trim();
        ent2 = enterAreEdt2.getText().toString().trim();
        if (btn == 0) {
            buttonCalculate();
        } else {
            Log.d("sdsd", "Edit");
            editTextCalculate();
        }
    }

    private void editTextCalculate() {
        if (area.length() != 0 && ent1.length() != 0 && ent2.length() != 0) {
            double multiValue = 0.0;
            if (posi == 0) {
                multiValue = 4046.86;
            } else if (posi == 1) {
                multiValue = 0.092903;
            } else if (posi == 2) {
                multiValue = 10000;
            }
            double multi = parseDouble(area) * multiValue;
            double diveder = Double.parseDouble(ent1) * Double.parseDouble(ent2);
            double finalValues = multi / diveder;
            //Log.d("finalValues", "" + finalValues / 1000);
            int i = (int) finalValues;
            enstimateArea.setText("" + i);
            setVisiable(View.VISIBLE);
        }
    }

    private void buttonCalculate() {
        if (area.length() != 0 && ent1.length() != 0 && ent2.length() != 0) {
            double multiValue = 0.0;
            if (posi == 0) {
                multiValue = 4046.86;
            } else if (posi == 1) {
                multiValue = 0.092903;
            } else if (posi == 2) {
                multiValue = 10000;
            }
            double multi = parseDouble(area) * multiValue;
            double diveder = Double.parseDouble(ent1) * Double.parseDouble(ent2);
            double finalValues = multi / diveder;
            //Log.d("finalValues", "" + finalValues / 1000);
            int i = (int) finalValues;
            enstimateArea.setText("" + i);
            setVisiable(View.VISIBLE);
        } else {
            //Log.d("Miassing", "Enter all");
            Toast.makeText(getActivity(), getActivity().getString(R.string.enterAllField), Toast.LENGTH_SHORT).show();

        }
    }

    private void setVisiable(int visible) {

        estimateTxt.setVisibility(visible);
        enstimateArea.setVisibility(visible);
    }
}

