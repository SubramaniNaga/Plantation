package com.fresh.mind.plantation.fragment.inside_tabs;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SingleSpinnerSearch;
import com.androidbuts.multispinnerfilter.SpinnerListener;
import com.fresh.mind.plantation.Constant.AppData;
import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.customized.MultiSpinner;
import com.fresh.mind.plantation.fragment.Inside.SearchedByTree;
import com.fresh.mind.plantation.fragment.inside_tabs.TreeType;
import com.fresh.mind.plantation.setter.SetterDistrict;
import com.fresh.mind.plantation.sqlite.LanguageChange;
import com.fresh.mind.plantation.sqlite.server.AllRainFall;
import com.fresh.mind.plantation.sqlite.server.AllSoilType;
import com.fresh.mind.plantation.sqlite.server.AllTerrainType;
import com.fresh.mind.plantation.sqlite.server.DistrictNameList;
import com.fresh.mind.plantation.sqlite.server.RainfallType;
import com.fresh.mind.plantation.sqlite.server.SchecmesDb;
import com.fresh.mind.plantation.sqlite.server.SoilType;
import com.fresh.mind.plantation.sqlite.server.TerrainType;
import com.fresh.mind.plantation.sqlite.server.TreeTypeInfo;
import com.fresh.mind.plantation.sqlite.server.TreeTypeNameList;
import com.fresh.mind.plantation.tab_pager.HomeTabView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.fresh.mind.plantation.Constant.Config.SELECTED_TAB_VIEW_POSITION;
import static com.fresh.mind.plantation.Constant.Config.districtName;
import static com.fresh.mind.plantation.Constant.Config.rainFallType;
import static com.fresh.mind.plantation.Constant.Config.soillType;
import static com.fresh.mind.plantation.Constant.Config.terrainType;
import static com.fresh.mind.plantation.Constant.Config.treeType;
import static com.fresh.mind.plantation.R.id.mDistrictSpinner;
import static com.fresh.mind.plantation.R.id.mRainFallSpinner;
import static com.fresh.mind.plantation.R.id.mSollTypeSpinner;
import static com.fresh.mind.plantation.R.id.mTerraintypeSpinner;
import static com.fresh.mind.plantation.R.id.mTreetypeSpinner;
import static com.fresh.mind.plantation.R.id.view;

/**
 * Created by AND I5 on 13-04-2017.
 */

public class MultipleSearch extends Fragment {

    private View rootView;
    private CustomTextView mSearchBtn, mResetBtn;
    private Spinner mDistrictSpinner;
    private MultiSpinnerSearch mRainFallSpinner, mTerraintypeSpinner, mTreetypeSpinner;
    MultiSpinner mSollTypeSpinner;
    private TreeTypeNameList treeTypeNameList;
    private DistrictNameList districtNameList;
    private AllTerrainType allTerrainType;
    private AllSoilType allSoilType;
    private AllRainFall allRainFall;
    private String languages;
    private ArrayList<HashMap<String, String>> mDistrictList;
    private ArrayList<KeyPairBoolData> soilTypeList;
    private ArrayList<KeyPairBoolData> mRainFallSpinnerList;
    private ArrayList<KeyPairBoolData> mTerraintypeSpinnerList;
    private ArrayList<KeyPairBoolData> mTreetypeSpinnerList;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.plant_multiple_search, null);

        LanguageChange languageChange = new LanguageChange(getActivity());
        languages = languageChange.getStatus();
        if (languages.equals("1")) {
            Utils.setLocalLanguage("ta", getActivity());
        } else {
            Utils.setLocalLanguage("en", getActivity());
        }
        //((MainActivity) getActivity()).setSearch(getActivity().getResources().getString(R.string.searchTree));
        rootView = inflater.inflate(R.layout.plant_multiple_search, null);
        MainActivity.menuItem.setVisible(false);
        MainActivity.menuItem1.setVisible(false);

        mDistrictSpinner = (Spinner) rootView.findViewById(R.id.mDistrictSpinner);
        mSollTypeSpinner = (MultiSpinner) rootView.findViewById(R.id.mSollTypeSpinner);
        mRainFallSpinner = (MultiSpinnerSearch) rootView.findViewById(R.id.mRainFallSpinner);
        mTerraintypeSpinner = (MultiSpinnerSearch) rootView.findViewById(R.id.mTerraintypeSpinner);
        mTreetypeSpinner = (MultiSpinnerSearch) rootView.findViewById(R.id.mTreetypeSpinner);
        mSearchBtn = (CustomTextView) rootView.findViewById(R.id.mSearchBtn);
        mResetBtn = (CustomTextView) rootView.findViewById(R.id.mResetBtn);

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            // Do something for lollipop and above versions
            mSearchBtn.setBackground(getActivity().getDrawable(R.drawable.riiple_oval));
            mResetBtn.setBackground(getActivity().getDrawable(R.drawable.ripple_reject));
        }

        allRainFall = new AllRainFall(getActivity());
        allSoilType = new AllSoilType(getActivity());
        allTerrainType = new AllTerrainType(getActivity());
        districtNameList = new DistrictNameList(getActivity());

        treeTypeNameList = new TreeTypeNameList(getActivity());

        mDistrictList = districtNameList.getDistrictNames(languages);
        if (mDistrictList.size() >= 1) {
            mDistrictSpinner.setAdapter(new DistrictSpinner(getActivity(), mDistrictList));
        }

        mDistrictSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                districtName = mDistrictList.get(position).get("District");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        soilTypeList = allSoilType.getSetter(languages);
        //Log.d("allSoilType", "" + soilTypeList.size());
        mSollTypeSpinner.setItems(soilTypeList, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        // Log.i("districtSetter", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });


        mRainFallSpinnerList = allRainFall.getSetter(languages);
        //   Log.d("allSoilTypemRainFallSpinnerList", "" + mRainFallSpinnerList.size());
        mRainFallSpinner.setItems(mRainFallSpinnerList, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        // Log.i("districtSetter", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });


        mTerraintypeSpinnerList = allTerrainType.getSetter(languages);
        //  Log.d("allSoilTypemTerraintypeSpinnerList", "" + mTerraintypeSpinnerList.size());
        mTerraintypeSpinner.setItems(mTerraintypeSpinnerList, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        //Log.i("districtSetter", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });
        mTerraintypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTreetypeSpinnerList = districtNameList.getDistrictBaseTreeTypeKeyPair(languages, districtName);
                mTreetypeSpinner.setItems(mTreetypeSpinnerList, -1, new SpinnerListener() {

                    @Override
                    public void onItemsSelected(List<KeyPairBoolData> items) {

                        for (int i = 0; i < items.size(); i++) {
                            if (items.get(i).isSelected()) {
                                //Log.i("districtSetter", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                            }
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       /* mTreetypeSpinnerList = treeTypeNameList.getTreTypeeNamesKayPair(AppData.checkLanguage(getActivity()));
        mTreetypeSpinner.setItems(mTreetypeSpinnerList, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        //Log.i("districtSetter", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });*/


        mSearchBtn.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              // Log.d("dsfadfgg", "" + mDistrictSpinner.getSelectedItem().toString());
                                              soillType = mSollTypeSpinner.getSelectedItem().toString();
                                              rainFallType = mRainFallSpinner.getSelectedItem().toString();
                                              terrainType = mTerraintypeSpinner.getSelectedItem().toString();
                                              treeType = mTreetypeSpinner.getSelectedItem().toString();
                                              //Log.d("treeTypetreeType", "  " + treeType + " districtName " + districtName + "  rainFallType " + rainFallType + " terrainType  " + terrainType + "  soillType " + soillType);
                                              if (districtName != getResources().getString(R.string.selectDistrictName) ||
                                                      soillType != getResources().getString(R.string.selectSoilName) ||
                                                      rainFallType != getResources().getString(R.string.selectRainFallName) ||
                                                      terrainType != getResources().getString(R.string.selectTerrainName) ||
                                                      treeType != getResources().getString(R.string.selectTreeName)) {
                                              } else {
                                                  Toast.makeText(getActivity(), getActivity().getString(R.string.enterAllField), Toast.LENGTH_LONG).show();
                                              }
                                              if (districtName != null) {
                                                  if (soillType != getResources().getString(R.string.selectSoilName)) {
                                                      if (rainFallType != getResources().getString(R.string.selectRainFallName)) {
                                                          if (terrainType != getResources().getString(R.string.selectTerrainName)) {
                                                              if (treeType != getResources().getString(R.string.selectTreeName) && treeType != "") {
                                                                  Config.TREE_TYPE = treeType;
                                                                  Config.DISTRICT_NAME = districtName;
                                                                  Config.RAIN_FALL = rainFallType;
                                                                  Config.TERRAIN_TYPE = terrainType;
                                                                  Config.SOIL_TYPE = soillType;
                                                                  getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new SearchedByTree()).commit();
                                                              } else {
                                                                  Toast.makeText(getActivity(), R.string.selectTreeName, Toast.LENGTH_SHORT).show();
                                                              }
                                                          } else {
                                                              Toast.makeText(getActivity(), R.string.selectTerrainName, Toast.LENGTH_SHORT).show();
                                                          }
                                                      } else {
                                                          Toast.makeText(getActivity(), R.string.selectRainFallName, Toast.LENGTH_SHORT).show();
                                                      }
                                                  } else {
                                                      Toast.makeText(getActivity(), R.string.selectSoilName, Toast.LENGTH_SHORT).show();
                                                  }
                                              } else {
                                                  Toast.makeText(getActivity(), getActivity().getString(R.string.enterAllField), Toast.LENGTH_LONG).show();
                                              }

                                          }
                                      }

        );

        mResetBtn.setOnClickListener(new View.OnClickListener()

                                     {
                                         @Override
                                         public void onClick(View v) {

                                             Log.d("sldksldsk", "dsds");
                                             Config.SELECTED_DIS = 0;
                                             districtName = "";
                                             soillType = getResources().getString(R.string.noSoilType);
                                             rainFallType = getResources().getString(R.string.noRainfall);
                                             terrainType = getResources().getString(R.string.noTerrainType);
                                             treeType = getResources().getString(R.string.noTreeType);
                                             SELECTED_TAB_VIEW_POSITION = 3;
                                             getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new HomeTabView()).commit();

                                             /*mDistrictList = districtNameList.getDistrictNames(languages);
                                             mDistrictSpinner.setAdapter(new DistrictSpinner(getActivity(), mDistrictList));*/


                                            /* mSollTypeSpinner.setItems(soilTypeList, -1, new SpinnerListener() {

                                                 @Override
                                                 public void onItemsSelected(List<KeyPairBoolData> items) {

                                                     for (int i = 0; i < items.size(); i++) {
                                                         if (items.get(i).isSelected()) {
                                                             // Log.i("districtSetter", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                                                         }
                                                     }
                                                 }
                                             });

                                             mSollTypeSpinner.setItems(soilTypeList, -1, new SpinnerListener() {

                                                 @Override
                                                 public void onItemsSelected(List<KeyPairBoolData> items) {

                                                     for (int i = 0; i < items.size(); i++) {
                                                         if (items.get(i).isSelected()) {
                                                             // Log.i("districtSetter", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                                                         }
                                                     }
                                                 }
                                             });
                                             mRainFallSpinner.setItems(mRainFallSpinnerList, -1, new SpinnerListener() {

                                                 @Override
                                                 public void onItemsSelected(List<KeyPairBoolData> items) {

                                                     for (int i = 0; i < items.size(); i++) {
                                                         if (items.get(i).isSelected()) {
                                                             // Log.i("districtSetter", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                                                         }
                                                     }
                                                 }
                                             });
                                             mTerraintypeSpinner.setItems(mTerraintypeSpinnerList, -1, new SpinnerListener() {

                                                 @Override
                                                 public void onItemsSelected(List<KeyPairBoolData> items) {

                                                     for (int i = 0; i < items.size(); i++) {
                                                         if (items.get(i).isSelected()) {
                                                             //Log.i("districtSetter", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                                                         }
                                                     }
                                                 }
                                             });*/

                                         }
                                     }

        );
        return rootView;
    }


    class DistrictSpinner extends BaseAdapter {

        private final FragmentActivity mContext;
        private final ArrayList<HashMap<String, String>> mDistricValues;

        public DistrictSpinner(FragmentActivity activity, ArrayList<HashMap<String, String>> spinnner) {
            this.mContext = activity;
            this.mDistricValues = spinnner;
        }

        @Override
        public int getCount() {
            return mDistricValues.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.adapter_spinner_1, null);
            CustomTextView mSPinnerText = (CustomTextView) convertView.findViewById(R.id.mSPinnerText);
            mSPinnerText.setText("" + mDistricValues.get(position).get("District"));
            return convertView;
        }
    }

}
