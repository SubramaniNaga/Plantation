package com.fresh.mind.plantation.fragment.Inside;

import android.media.Rating;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fresh.mind.plantation.Constant.AppData;
import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.sqlite.LanguageChange;
import com.fresh.mind.plantation.sqlite.server.VerifyDetails;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static com.fresh.mind.plantation.R.id.customTextView3;

import static com.fresh.mind.plantation.R.id.mSoilPh;
import static com.fresh.mind.plantation.R.id.textView;
import static com.fresh.mind.plantation.R.string.Additional;
import static com.fresh.mind.plantation.R.string.Habit;
import static com.fresh.mind.plantation.R.string.Rainfall;
import static com.fresh.mind.plantation.R.string.Reference;
import static com.fresh.mind.plantation.R.string.Soil;

/**
 * Created by AND I5 on 03-02-2017.
 */

public class DescriptionView extends Fragment {
    View rootView;

    private CustomTextView mTreeNameTxt, mSoilTxt, mAltitudeTxt, mRainfallTxt, mTerrainTxt, mPlantationTec;
    private ImageView mFirstImg, mSecondImg, mThirdImg;
    private CustomTextView GeneralInformation, Habitat, SoilpH, Temperature, TemperatureMin, TemperatureMax;
    private CustomTextView TreeCharacteristics, ConservationStatus, EdibilityRating, MedicinalRating, OtherUsesRating, Habit, GrowthRate, Height, CultivationStatus, OtherDetails,
            Propagation, PlantationTechnique, CareDiseaseControl, Irrigation,
            Yield, RecommendedHarvest, MarketDetails,
            Intercrops, MajorUses, OtherUses,
            CarbonStockDetails, References;

    private CustomTextView mAboutTitle, mFavorableTitle, mThreeChar, mPlantationGuideTitle, mHarvestTile, mAgroforest, mUsesTitle, mAdditional;
    private VerifyDetails verifyDetails;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        LanguageChange languageChange = new LanguageChange(getActivity());
        String languages = languageChange.getStatus();
        if (languages.equals("1")) {
            Utils.setLocalLanguage("ta", getActivity());
        } else {
            Utils.setLocalLanguage("en", getActivity());
        }
        String language = AppData.checkLanguage(getActivity());
        rootView = inflater.inflate(R.layout.plant_view_tree_details, null);
        init();
        verifyDetails = new VerifyDetails(getActivity());
        mTreeNameTxt.setText("" + Config.TREE_NAME);
        ArrayList<HashMap<String, String>> description = verifyDetails.getDescription(Config.TREE_NAME, language);
        checkCondition(description);
        return rootView;
    }


    private void checkCondition(ArrayList<HashMap<String, String>> description) {

        Log.d("description", "" + description.size());
        for (int i = 0; i < description.size(); i++) {
            String General_Info = description.get(i).get("General_Info");
            String HabitatStr = description.get(i).get("Habitat");

            String Soil = description.get(i).get("Soil");
            String Soil_PH = description.get(i).get("Soil_PH");
            String Altitude = description.get(i).get("Altitude");
            String Min_Temperature = description.get(i).get("Min_Temperature");
            String Max_Temperature = description.get(i).get("Max_Temperature");
            String Rainfall = description.get(i).get("Rainfall");
            String Terrain = description.get(i).get("Terrain");

            String temp = (Min_Temperature + Max_Temperature);
            Log.d("descriptionss32415", "" + temp + " " + temp.length());
            setText(TemperatureMin, Min_Temperature, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.Climate) + " </font> - </b>  " + Min_Temperature));
            setText(TemperatureMax, Max_Temperature, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.ClimateMax) + " </font> - </b>  " + Max_Temperature));
            setText(mSoilTxt, Soil, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.Soil) + " </font> - </b>  " + Soil));
            setText(mAltitudeTxt, Altitude, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.Altitude) + " </font> - </b>  " + Altitude));
            //setText(Temperature, temp, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.Climate) + " </font> - </b>  " + temp));
            if (temp.equals("null") || temp.equals("")) {
                Temperature.setVisibility(View.GONE);
            } else {
                Temperature.setVisibility(View.VISIBLE);
            }
            setText(mRainfallTxt, Rainfall, Html.fromHtml("<b><font size='30' color='#0e7302'>" + getActivity().getString(R.string.Rainfall) + " </font> - </b>  " + Rainfall));
            setText(mTerrainTxt, Terrain, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.Terrain) + " </font> - </b>  " + Terrain));
            setText(SoilpH, Soil_PH, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.SoilpH) + " </font> - </b>  " + Soil_PH));

            //setConditionText(Soil, (Min_Temperature + " - " + Max_Temperature), Altitude, Terrain, Rainfall, Soil_PH);
            String Medicinal = description.get(i).get("Medicinal");
            String Tree_Char = description.get(i).get("Tree_Char");
            String Conservation = description.get(i).get("Conservation");
            String Edibility = description.get(i).get("Edibility");
            String IrrigationStr = description.get(i).get("Irrigation");
            String HabitStr = description.get(i).get("Habit");
            String Growth = description.get(i).get("Growth");
            String HeightStr = description.get(i).get("Height");
            String Cultivation = description.get(i).get("Cultivation");
            String Other_Details = description.get(i).get("Other_Details");
            String PropagationStr = description.get(i).get("Propagation");
            String Plantation_TechniqueStr = description.get(i).get("Plantation_Technique");
            String Care_DiseaseStr = description.get(i).get("Care_Disease");
            String YieldStr = description.get(i).get("Yield");
            String Recommended_Harvest = description.get(i).get("Recommended_Harvest");
            String Market_Details = description.get(i).get("Market_Details");
            String IntercropsStr = description.get(i).get("Intercrops");
            String Majar_Uses = description.get(i).get("Majar_Uses");
            String Other_Uses = description.get(i).get("Other_Uses");
            String Other_RatingStr = description.get(i).get("Other_Rating");

            String Carbon_Stock = description.get(i).get("Carbon_Stock");
            String Reference = description.get(i).get("Reference");
            /*String lastUpdate = description.get(i).get("lastUpdate");
Log.d("lastUpdate",""+lastUpdate);
*/
            if ((Soil.equals("null") || Soil.isEmpty()) && (Altitude.equals("null") || Altitude.isEmpty()) && (temp.equals("null") || temp.isEmpty()) && (Rainfall.equals("null") || Rainfall.isEmpty())
                    && (Terrain.equals("null") || Terrain.isEmpty()) && (Soil_PH.equals("null") || Soil_PH.isEmpty())) {
                mFavorableTitle.setVisibility(View.GONE);
                Temperature.setVisibility(View.GONE);
            }
            if ((YieldStr.equals("null") || YieldStr.isEmpty()) && (Recommended_Harvest.equals("null") || Recommended_Harvest.isEmpty()) && (Recommended_Harvest.equals("null") || Recommended_Harvest.isEmpty())) {
                mHarvestTile.setVisibility(View.GONE);
            }
            setVisiable(mAboutTitle, General_Info, HabitatStr);
            setVisiable(mAdditional, Reference, Carbon_Stock);
            setVisiable(mUsesTitle, Majar_Uses, Other_Uses);
            if (IntercropsStr.equals("null") || IntercropsStr.equals("")) {
                mAgroforest.setVisibility(View.GONE);
            }
            if ((Tree_Char.equals("null") || Tree_Char.equals("")) && (Conservation.equals("null") || Conservation.equals("")) && (Edibility.equals("null") || Edibility.equals("")) && (Medicinal.equals("null") || Medicinal.equals("")) &&
                    (Other_RatingStr.equals("null") || Other_RatingStr.equals("")) && (HabitStr.equals("null") || HabitStr.equals("")) && (Growth.equals("null") || Growth.equals("")) && (HeightStr.equals("null") || HeightStr.equals("")) &&
                    (Cultivation.equals("null") || Cultivation.equals("")) && (Other_Details.equals("null") || Other_Details.equals(""))) {
                mThreeChar.setVisibility(View.GONE);
            }

            if ((PropagationStr.equals("null") || PropagationStr.equals("")) && (Plantation_TechniqueStr.equals("null") || Plantation_TechniqueStr.equals("")) && (Care_DiseaseStr.equals("null") || Care_DiseaseStr.equals(""))
                    && (IrrigationStr.equals("null") || IrrigationStr.equals(""))) {
                mPlantationGuideTitle.setVisibility(View.GONE);
            }


            setText(GeneralInformation, General_Info, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.GeneralInformation) + " </font> - </b>  " + General_Info));
            setText(TreeCharacteristics, Tree_Char, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.TreeCharacteristics) + " </font> - </b>  " + Tree_Char));
            setText(ConservationStatus, Conservation, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.ConservationStatus) + " </font> - </b>  " + Conservation));
            setText(Habitat, HabitatStr, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.Habitat) + " </font> - </b>  " + HabitatStr));
            setText(EdibilityRating, Edibility, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.EdibilityRating) + " </font> - </b>  " + Edibility));
            setText(MedicinalRating, Medicinal, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.MedicinalRating) + " </font> - </b>  " + Medicinal));
            setText(GrowthRate, Growth, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.GrowthRate) + " </font> - </b>  " + Growth));
            setText(Height, HeightStr, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.Height) + " </font> - </b>  " + HeightStr));
            setText(Habit, HabitStr, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.Habit) + " </font> - </b>  " + HabitStr));

            setText(CultivationStatus, Cultivation, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.CultivationStatus) + " </font> - </b>  " + Cultivation));
            setText(OtherUsesRating, Other_RatingStr, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.OtherUsesRating) + " </font> - </b>  " + Other_RatingStr));
            setText(OtherDetails, Other_Details, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.OtherDetails) + " </font> - </b>  " + Other_Details));
            setText(Propagation, PropagationStr, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.Propagation) + " </font> - </b>  " + PropagationStr));
            setText(PlantationTechnique, Plantation_TechniqueStr, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.plantationTec) + " </font> - </b>  " + Plantation_TechniqueStr));

            setText(CareDiseaseControl, Care_DiseaseStr, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.Diseasecontrol) + " </font> - </b>  " + Care_DiseaseStr));
            setText(Irrigation, IrrigationStr, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.Irrigation) + " </font> - </b>  " + IrrigationStr));
            setText(Yield, YieldStr, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.Yield) + " </font> - </b>  " + YieldStr));
            setText(RecommendedHarvest, Recommended_Harvest, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.RecommendedHarvest) + " </font> - </b>  " + Recommended_Harvest));
            setText(MarketDetails, Market_Details, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.MarketDetails) + " </font> - </b>  " + Market_Details));

            setText(Intercrops, IntercropsStr, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.Intercrops) + " </font> - </b>  " + IntercropsStr));
            setText(MajorUses, Majar_Uses, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.MajorUses) + " </font> - </b>  " + Majar_Uses));
            setText(OtherUses, Other_Uses, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.OtherUses) + " </font> - </b>  " + Other_Uses));
            setText(CarbonStockDetails, Carbon_Stock, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.CarbonStockDetails) + " </font> - </b>  " + Carbon_Stock));
            setText(References, Reference, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.References) + " </font> - </b>  " + Reference));

            /* Log.d("descriptionValues", " General_Info " + General_Info + " Habitat  " + Habitat + " Soil  " + Soil + " Soil_PH  " + Soil_PH + " Altitude  " + Altitude + "  Min_Temperature " + Min_Temperature +
                    "  Max_Temperature " + Max_Temperature + " Rainfall  " + Rainfall + " Terrain  " + Terrain + "  Medicinal " + Medicinal + " Tree_Char " + Tree_Char + "  Conservation " + Conservation +
                    " Edibility  " + Edibility + "  Irrigation " + Irrigation + "  Growth " + Growth + " Height  " + Height + "  Intercropping_Model " + Intercropping_Model + "  Other_Details " + Other_Details +
                    " Propagation " + Propagation + "  Plantation_Technique " + Plantation_Technique + "  Care_Disease " + Care_Disease + "  Yield " + Yield + " Recommended_Harvest  " + Recommended_Harvest
                    + " Market_Details  " + Market_Details + " Intercrops  " + Intercrops + "  Majar_Uses " + Majar_Uses + " Other_Uses " + Other_Uses + " Carbon_Stock  " + Carbon_Stock + " Reference " + Reference
                    + " lastUpdate  " + lastUpdate);*/

        }

    }

    private void setVisiable(CustomTextView textView, String text, String text2) {
        if ((text.equals("null") || text.equals("")) && (text2.equals("null") || text2.equals(""))) {
            textView.setVisibility(View.GONE);
        }
    }

    private void setText(CustomTextView textView, String text, Spanned spanned) {

        if (text.equals("null") || text.equals("")) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(spanned);
        }
    }


    private void logd(String what, String additional_information) {
        Log.d("Sample     " + what, "" + additional_information);
    }

    private void setConditionText(String soil, String climate, String altitude, String terrain, String rainfall, String Soil_PH) {
        mSoilTxt.setText(Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(Soil) + " </font> - </b>  " + soil));
        mAltitudeTxt.setText(Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.Altitude) + " </font> - </b>  " + altitude));
        Temperature.setText(Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.Climate) + " </font> - </b>  " + climate));
        mRainfallTxt.setText(Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(Rainfall) + " </font> - </b>  " + rainfall));
        mTerrainTxt.setText(Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.Terrain) + " </font> - </b>  " + terrain));


        if (Soil_PH.equals("null")) {
            Log.d("asdfsdf", "111" + Soil_PH);
            SoilpH.setVisibility(View.GONE);
        } else {
            Log.d("asdfsdf", "222" + Soil_PH);

            SoilpH.setVisibility(View.VISIBLE);
            SoilpH.setText(Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.SoilpH) + " </font> - </b>  " + Soil_PH));
        }
    }

    private void init() {
        mFirstImg = (ImageView) rootView.findViewById(R.id.mFirstImg);
        mSecondImg = (ImageView) rootView.findViewById(R.id.mSecondImg);
        mThirdImg = (ImageView) rootView.findViewById(R.id.mThirdImg);

        mSoilTxt = (CustomTextView) rootView.findViewById(R.id.mSoilTxt);
        mAltitudeTxt = (CustomTextView) rootView.findViewById(R.id.mAltitudeTxt);
        mRainfallTxt = (CustomTextView) rootView.findViewById(R.id.mRainfallTxt);
        mTerrainTxt = (CustomTextView) rootView.findViewById(R.id.mTerrainTxt);
        mTreeNameTxt = (CustomTextView) rootView.findViewById(R.id.mTreeNameTxt);
        SoilpH = (CustomTextView) rootView.findViewById(R.id.mSoilPh);
        Temperature = (CustomTextView) rootView.findViewById(R.id.Temperature);

        TreeCharacteristics = (CustomTextView) rootView.findViewById(R.id.mTreeChar);
        ConservationStatus = (CustomTextView) rootView.findViewById(R.id.ConservationStatus);
        EdibilityRating = (CustomTextView) rootView.findViewById(R.id.mEdibilityStatus);
        MedicinalRating = (CustomTextView) rootView.findViewById(R.id.mMedicalRating);
        OtherUsesRating = (CustomTextView) rootView.findViewById(R.id.mOtherUsesRating);
        Habit = (CustomTextView) rootView.findViewById(R.id.mHabit);
        GrowthRate = (CustomTextView) rootView.findViewById(R.id.GrowthRate);
        CultivationStatus = (CustomTextView) rootView.findViewById(R.id.mCultivetionsStatus);
        OtherDetails = (CustomTextView) rootView.findViewById(R.id.OtherDetails);
        Propagation = (CustomTextView) rootView.findViewById(R.id.mPropagation);
        PlantationTechnique = (CustomTextView) rootView.findViewById(R.id.PlantationTechnique);
        CareDiseaseControl = (CustomTextView) rootView.findViewById(R.id.mCareDsease);
        Irrigation = (CustomTextView) rootView.findViewById(R.id.Irrigation);
        Yield = (CustomTextView) rootView.findViewById(R.id.mYeild);
        RecommendedHarvest = (CustomTextView) rootView.findViewById(R.id.mReommandedHarvest);
        MarketDetails = (CustomTextView) rootView.findViewById(R.id.mMarketDetails);
        Intercrops = (CustomTextView) rootView.findViewById(R.id.Intercrops);
        MajorUses = (CustomTextView) rootView.findViewById(R.id.MajorUses);
        OtherUses = (CustomTextView) rootView.findViewById(R.id.OtherUses);
        CarbonStockDetails = (CustomTextView) rootView.findViewById(R.id.CarbonStockDetails);
        References = (CustomTextView) rootView.findViewById(R.id.References);
        GeneralInformation = (CustomTextView) rootView.findViewById(R.id.GeneralInformation);
        Habitat = (CustomTextView) rootView.findViewById(R.id.Habitat);
        Height = (CustomTextView) rootView.findViewById(R.id.Height);
        TemperatureMin = (CustomTextView) rootView.findViewById(R.id.TemperatureMin);
        TemperatureMax = (CustomTextView) rootView.findViewById(R.id.TemperatureMax);

        mAboutTitle = (CustomTextView) rootView.findViewById(R.id.mAboutTitle);
        mFavorableTitle = (CustomTextView) rootView.findViewById(R.id.customTextView3);
        mThreeChar = (CustomTextView) rootView.findViewById(R.id.mThreeChar);
        mPlantationGuideTitle = (CustomTextView) rootView.findViewById(R.id.mPlantationGuideTitle);
        mHarvestTile = (CustomTextView) rootView.findViewById(R.id.mHarvestTile);
        mAgroforest = (CustomTextView) rootView.findViewById(R.id.mAgroforest);
        mUsesTitle = (CustomTextView) rootView.findViewById(R.id.mUsesTitle);
        mAdditional = (CustomTextView) rootView.findViewById(R.id.customTextView4);

    }
}
