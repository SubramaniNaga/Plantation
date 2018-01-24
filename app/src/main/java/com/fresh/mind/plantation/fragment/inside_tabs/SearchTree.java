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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.fresh.mind.plantation.Constant.AppData;
import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.fragment.Inside.SearchedByTree;

import com.fresh.mind.plantation.sqlite.LanguageChange;
import com.fresh.mind.plantation.sqlite.server.DistrictNameList;
import com.fresh.mind.plantation.sqlite.server.RainfallType;
import com.fresh.mind.plantation.sqlite.server.SoilType;
import com.fresh.mind.plantation.sqlite.server.TerrainType;
import com.fresh.mind.plantation.sqlite.server.TreeTypeInfo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by AND I5 on 13-01-2017.
 */
public class SearchTree extends Fragment {

    private View rootView;
    private CustomTextView mSearchBtn, mResetBtn;
    private Spinner mDistrictSpinner, mSollTypeSpinner, mRainFallSpinner, mTerraintypeSpinner, mTreetypeSpinner;


    private ArrayList<HashMap<String, String>> mDistrictList;

    String districtName, soillType, rainFallType, terrainType, treeType;
    private DistrictNameList districtNameList;
    private SoilType soilType;
    private TerrainType terrainTypeDb;
    private RainfallType rainfallType;
    private TreeTypeInfo treeTypeInfo;
    ArrayList<HashMap<String, String>> mSoiltypeList, mRainFalType, mTerrainType, mTreeType;
    String[] noRainfal;
    String languages;


    @Override
    public void onDestroy() {
        super.onDestroy();

        districtNameList.close();
        soilType.close();
        rainfallType.close();
        terrainTypeDb.close();

        Log.d("CloseSearchTree", "Objects");
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        LanguageChange languageChange = new LanguageChange(getActivity());
        languages = languageChange.getStatus();

        if (languages.equals("1")) {
            Utils.setLocalLanguage("ta", getActivity());
        } else {
            Utils.setLocalLanguage("en", getActivity());
        }
        ((MainActivity) getActivity()).setSearch(getActivity().getResources().getString(R.string.searchTree));
        rootView = inflater.inflate(R.layout.plant_search, null);


        Log.d("lskdlsdks222", "" + Config.SELECTED_TAB_VIEW_POSITION);

        mDistrictSpinner = (Spinner) rootView.findViewById(R.id.mDistrictSpinner);
        mSollTypeSpinner = (Spinner) rootView.findViewById(R.id.mSollTypeSpinner);
        mRainFallSpinner = (Spinner) rootView.findViewById(R.id.mRainFallSpinner);
        mTerraintypeSpinner = (Spinner) rootView.findViewById(R.id.mTerraintypeSpinner);
        mTreetypeSpinner = (Spinner) rootView.findViewById(R.id.mTreetypeSpinner);
        mSearchBtn = (CustomTextView) rootView.findViewById(R.id.mSearchBtn);
        mResetBtn = (CustomTextView) rootView.findViewById(R.id.mResetBtn);


        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            // Do something for lollipop and above versions
            mSearchBtn.setBackground(getActivity().getDrawable(R.drawable.riiple_oval));
            mResetBtn.setBackground(getActivity().getDrawable(R.drawable.ripple_reject));
        }


        //mDistrictList = districtList.getDistrictNames();
        soilType = new SoilType(getActivity());

        districtNameList = new DistrictNameList(getActivity());
        rainfallType = new RainfallType(getActivity());
        terrainTypeDb = new TerrainType(getActivity());
        treeTypeInfo = new TreeTypeInfo(getActivity());

        mDistrictList = districtNameList.getDistrictNames(languages);
        mDistrictSpinner.setAdapter(new DistrictSpinner(getActivity(), mDistrictList));
        // Log.d("a454512sas", "asas" + Config.SELECTED_DIS + "  " + Config.SELECTED_SOIL + " " + Config.SELECTED_RAIN_FALL + "  " + Config.SELECTED_TERRAIN + " " + Config.SELECTE_TREE_TYPE);
        mDistrictSpinner.setSelected(false);
        mSollTypeSpinner.setSelected(false);
        mRainFallSpinner.setSelected(false);
        mTerraintypeSpinner.setSelected(false);
        mTreetypeSpinner.setSelected(false);

        if (Config.SELECTED_DIS >= 0) {
            mDistrictSpinner.setSelection(Config.SELECTED_DIS);
        }
       /* if (Config.SELECTED_SOIL >= 0) {
            mSollTypeSpinner.setSelection(Config.SELECTED_SOIL);
        }
        if (Config.SELECTED_RAIN_FALL >= 0) {
            mRainFallSpinner.setSelection(Config.SELECTED_RAIN_FALL);
        }
        if (Config.SELECTED_TERRAIN >= 0) {
            mTerraintypeSpinner.setSelection(Config.SELECTED_TERRAIN);
        }
        if (Config.SELECTE_TREE_TYPE >= 0) {
            mTreetypeSpinner.setSelection(Config.SELECTE_TREE_TYPE);
        }*/
        mDistrictSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                districtName = mDistrictList.get(position).get("District");
                mSoiltypeList = soilType.getSoilType(districtName, "Soil", AppData.checkLanguage(getActivity()));
                Log.d("mSoiltypeList", "" + mSoiltypeList.size());
                Config.SELECTED_DIS = mDistrictSpinner.getSelectedItemPosition();
                if (mSoiltypeList.size() >= 1) {
                    mSollTypeSpinner.setAdapter(new SoilTypeSpinner(getActivity(), mSoiltypeList));
                } else {
                    noRainfal = new String[]{getResources().getString(R.string.noSoilType)};
                    mSollTypeSpinner.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.adapter_spinner, R.id.mSPinnerText, noRainfal));
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSollTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mSoiltypeList.size() >= 1) {
                    soillType = mSoiltypeList.get(position).get("soilType");
                } else {
                    soillType = noRainfal[position];
                }
                mRainFalType = rainfallType.getRainnFallType(districtName, AppData.checkLanguage(getActivity()));
                Log.d("mTerrainType mRainFalType", "" + mRainFalType.size());
                if (mRainFalType.size() >= 1) {
                    mRainFallSpinner.setAdapter(new RainSpinner(getActivity(), mRainFalType));
                } else {
                    noRainfal = new String[]{getResources().getString(R.string.noRainfall)};
                    mRainFallSpinner.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.adapter_spinner, R.id.mSPinnerText, noRainfal));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mRainFallSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (mRainFalType.size() >= 1) {
                    rainFallType = mRainFalType.get(position).get("Rainfall");
                } else {
                    rainFallType = noRainfal[position];
                }
                mTerrainType = terrainTypeDb.getTerrainType(districtName, AppData.checkLanguage(getActivity()));

                if (mTerrainType.size() >= 1) {
                    mTerraintypeSpinner.setAdapter(new TerrainTypeSpinner(getActivity(), mTerrainType));
                } else {
                    noRainfal = new String[]{getResources().getString(R.string.noTerrainType)};
                    mTerraintypeSpinner.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.adapter_spinner, R.id.mSPinnerText, noRainfal));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mTerraintypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mTerrainType.size() >= 1) {
                    terrainType = mTerrainType.get(position).get("Terrain");
                } else {
                    terrainType = noRainfal[position];
                }
                mTreeType = treeTypeInfo.getTreeType(districtName, soillType, AppData.checkLanguage(getActivity()));
                Log.d("mTerrainType mTreeType", "" + mTreeType.size());
                if (mTreeType.size() >= 1) {
                    mTreetypeSpinner.setAdapter(new TreeTypeSpinner(getActivity(), mTreeType));
                } else {
                    noRainfal = new String[]{getResources().getString(R.string.noTreeType)};
                    mTreetypeSpinner.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.adapter_spinner, R.id.mSPinnerText, noRainfal));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mTreetypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mTreeType.size() >= 1) {
                    treeType = mTreeType.get(position).get("Treetype");
                } else {
                    treeType = noRainfal[position];
                }
                Config.SELECTE_TREE_TYPE = mTreetypeSpinner.getSelectedItemPosition();
                //mTreetypeSpinner.setSelection(Config.SELECTE_TREE_TYPE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              Log.d("treeTypetreeType", "  " + treeType + " districtName " + districtName + "  rainFallType " + rainFallType + " terrainType  " + terrainType + "  soillType " + soillType);
                                              if (soillType != getResources().getString(R.string.noSoilType) &&
                                                      rainFallType != getResources().getString(R.string.noRainfall) &&
                                                      terrainType != getResources().getString(R.string.noTerrainType) &&
                                                      treeType != getResources().getString(R.string.noTreeType)) {
                                              } else {
                                                  Toast.makeText(getActivity(), getActivity().getString(R.string.enterAllField), Toast.LENGTH_LONG).show();
                                              }
                                              if (districtName != null) {
                                                  if (soillType != getResources().getString(R.string.noSoilType)) {
                                                      if (rainFallType != getResources().getString(R.string.noRainfall)) {
                                                          if (terrainType != getResources().getString(R.string.noTerrainType)) {
                                                              if (treeType != getResources().getString(R.string.noTreeType) && treeType != "") {
                                                                  Config.TREE_TYPE = treeType;
                                                                  Config.DISTRICT_NAME = districtName;
                                                                  Config.RAIN_FALL = rainFallType;
                                                                  Config.TERRAIN_TYPE = terrainType;
                                                                  Config.SOIL_TYPE = soillType;
                                                                  getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new SearchedByTree()).addToBackStack(null).commit();
                                                              } else {
                                                                  Toast.makeText(getActivity(), getResources().getString(R.string.selectTreeName), Toast.LENGTH_SHORT).show();
                                                              }
                                                          } else {
                                                              Toast.makeText(getActivity(), getResources().getString(R.string.selectTerrainName), Toast.LENGTH_SHORT).show();
                                                          }
                                                      } else {
                                                          Toast.makeText(getActivity(), getResources().getString(R.string.selectRainFallName), Toast.LENGTH_SHORT).show();
                                                      }
                                                  } else {
                                                      Toast.makeText(getActivity(), getResources().getString(R.string.selectSoilName), Toast.LENGTH_SHORT).show();
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
                                             Config.SELECTED_DIS = 0;
                                             districtName = "";
                                             soillType = getResources().getString(R.string.noSoilType);
                                             rainFallType = getResources().getString(R.string.noRainfall);
                                             terrainType = getResources().getString(R.string.noTerrainType);
                                             treeType = getResources().getString(R.string.noTreeType);

                                             //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_body, new SearchTree()).commit();

                                             mDistrictList = districtNameList.getDistrictNames(languages);
                                             mDistrictSpinner.setAdapter(new DistrictSpinner(getActivity(), mDistrictList));
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
            convertView = layoutInflater.inflate(R.layout.adapter_spinner, null);
            CustomTextView mSPinnerText = (CustomTextView) convertView.findViewById(R.id.mSPinnerText);
            mSPinnerText.setText("" + mDistricValues.get(position).get("District"));

            return convertView;
        }
    }


    class SoilTypeSpinner extends BaseAdapter {

        private final FragmentActivity mContext;
        private final ArrayList<HashMap<String, String>> mDistricValues;

        public SoilTypeSpinner(FragmentActivity activity, ArrayList<HashMap<String, String>> spinnner) {
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
            convertView = layoutInflater.inflate(R.layout.adapter_spinner, null);
            CustomTextView mSPinnerText = (CustomTextView) convertView.findViewById(R.id.mSPinnerText);
            mSPinnerText.setText("" + mDistricValues.get(position).get("soilType"));

            return convertView;

        }

    }

    class RainSpinner extends BaseAdapter {

        private final FragmentActivity mContext;
        private final ArrayList<HashMap<String, String>> mDistricValues;

        public RainSpinner(FragmentActivity activity, ArrayList<HashMap<String, String>> spinnner) {
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
            convertView = layoutInflater.inflate(R.layout.adapter_spinner, null);
            CustomTextView mSPinnerText = (CustomTextView) convertView.findViewById(R.id.mSPinnerText);
            mSPinnerText.setText("" + mDistricValues.get(position).get("Rainfall"));

            return convertView;

        }
    }

    class TerrainTypeSpinner extends BaseAdapter {

        private final FragmentActivity mContext;
        private final ArrayList<HashMap<String, String>> mDistricValues;

        public TerrainTypeSpinner(FragmentActivity activity, ArrayList<HashMap<String, String>> spinnner) {
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
            convertView = layoutInflater.inflate(R.layout.adapter_spinner, null);
            CustomTextView mSPinnerText = (CustomTextView) convertView.findViewById(R.id.mSPinnerText);
            mSPinnerText.setText("" + mDistricValues.get(position).get("Terrain"));
            return convertView;
        }
    }

    class TreeTypeSpinner extends BaseAdapter {

        private final FragmentActivity mContext;
        private final ArrayList<HashMap<String, String>> mDistricValues;

        public TreeTypeSpinner(FragmentActivity activity, ArrayList<HashMap<String, String>> spinnner) {
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
            convertView = layoutInflater.inflate(R.layout.adapter_spinner, null);
            CustomTextView mSPinnerText = (CustomTextView) convertView.findViewById(R.id.mSPinnerText);
            mSPinnerText.setText("" + mDistricValues.get(position).get("Treetype"));
            return convertView;
        }
    }
}

