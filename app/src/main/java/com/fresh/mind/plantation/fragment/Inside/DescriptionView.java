package com.fresh.mind.plantation.fragment.Inside;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.StyleSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.fresh.mind.plantation.Constant.AppData;
import com.fresh.mind.plantation.Constant.Config;
import com.fresh.mind.plantation.Constant.Utils;
import com.fresh.mind.plantation.R;
import com.fresh.mind.plantation.activity.GlossaryCompare;
import com.fresh.mind.plantation.activity.MainActivity;
import com.fresh.mind.plantation.customized.CustomTextView;
import com.fresh.mind.plantation.sqlite.LanguageChange;
import com.fresh.mind.plantation.sqlite.server.GlossaryTable;
import com.fresh.mind.plantation.sqlite.server.VerifyDetails;

import java.util.ArrayList;
import java.util.HashMap;


public class DescriptionView extends Fragment {
    private View rootView;
    private CustomTextView mTreeNameTxt, mSoilTxt, mAltitudeTxt, mRainfallTxt, mTerrainTxt, mPlantationTec;
    private ImageView mFirstImg, mSecondImg, mThirdImg;
    private CustomTextView GeneralInformation, Habitat, SoilpH, Temperature, TemperatureMin, TemperatureMax;
    private CustomTextView TreeCharacteristics, ConservationStatus, EdibilityRating, MedicinalRating, OtherUsesRating,
            Habit, GrowthRate, Height, CultivationStatus, OtherDetails, marketdetailstitle, Propagation, PlantationTechnique, CareDiseaseControl, Irrigation,
            Yield, RecommendedHarvest, MarketDetails, Intercrops, MajorUses,
            OtherUses, CarbonStockDetails, References;
    private ScrollView svAll;
    private CustomTextView tvNodata;
    private CustomTextView mAboutTitle, mFavorableTitle, mThreeChar, mPlantationGuideTitle,/* mHarvestTile, mAgroforest, */
            mUsesTitle, mAdditional;
    private VerifyDetails verifyDetails;
    private GlossaryTable glossaryTable;


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

        MainActivity.menuItem.setVisible(false);
        MainActivity.menuItem1.setVisible(false);
        String language = AppData.checkLanguage(getActivity());
        rootView = inflater.inflate(R.layout.plant_view_tree_details, null);
        init();
        verifyDetails = new VerifyDetails(getActivity());
        glossaryTable = new GlossaryTable(getActivity());
        mTreeNameTxt.setText("" + Config.TREE_NAME);
        ArrayList<HashMap<String, String>> description = verifyDetails.getDescription(Config.COMMON_KEY, language);
        ArrayList<HashMap<String, String>> glossaryDetails = glossaryTable.getGlossaryDetails(languages);


        checkCondition(description, glossaryDetails);


        onClick(description, glossaryDetails, GeneralInformation, "General_Info", "word");
        onClick(description, glossaryDetails, TreeCharacteristics, "Tree_Char", "word");
        onClick(description, glossaryDetails, Propagation, "Propagation", "word");
        onClick(description, glossaryDetails, PlantationTechnique, "Plantation_Technique", "word");
        onClick(description, glossaryDetails, CareDiseaseControl, "Care_Disease", "word");
        onClick(description, glossaryDetails, MedicinalRating, "Medicinal", "word");
        onClick(description, glossaryDetails, ConservationStatus, "Conservation", "word");
        onClick(description, glossaryDetails, EdibilityRating, "Edibility", "word");
        onClick(description, glossaryDetails, Irrigation, "Irrigation", "word");
        onClick(description, glossaryDetails, Habit, "Habit", "word");
/*
        String HeightStr = description.get(i).get("Height");
        String Market_Details = description.get(i).get("Market_Details");
*/
        onClick(description, glossaryDetails, GrowthRate, "Growth", "word");
        onClick(description, glossaryDetails, Height, "Habit", "word");
        onClick(description, glossaryDetails, CultivationStatus, "Cultivation", "word");
        onClick(description, glossaryDetails, OtherDetails, "Other_Details", "word");
        onClick(description, glossaryDetails, Propagation, "Propagation", "word");
        onClick(description, glossaryDetails, Yield, "Yield", "word");
        onClick(description, glossaryDetails, RecommendedHarvest, "Recommended_Harvest", "word");
        onClick(description, glossaryDetails, Intercrops, "Intercrops", "word");
        onClick(description, glossaryDetails, MajorUses, "Majar_Uses", "word");
        onClick(description, glossaryDetails, OtherUses, "Other_Uses", "word");
        onClick(description, glossaryDetails, OtherDetails, "Other_Rating", "word");
        onClick(description, glossaryDetails, CarbonStockDetails, "Carbon_Stock", "word");

        return rootView;
    }

    private void checkCondition(ArrayList<HashMap<String, String>> description, ArrayList<HashMap<String, String>> glossaryDetails) {
        Log.d("descriptionLegnthj", "" + description.size() + " \n " + description + " \n " + glossaryDetails.size() + "  " + glossaryDetails.toString());

        if (description.size() >= 1) {
            tvNodata.setVisibility(View.GONE);
            svAll.setVisibility(View.VISIBLE);
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
                if (temp.equals("null") || temp.equals("")) {
                    Temperature.setVisibility(View.GONE);
                } else {
                    Temperature.setVisibility(View.VISIBLE);
                }

                //setConditionText(Soil, (Min_Temperature + " : " + Max_Temperature), Altitude, Terrain, Rainfall, Soil_PH);
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

                if ((Soil.equals("null") || Soil.isEmpty()) && (Altitude.equals("null") || Altitude.isEmpty()) && (temp.equals("null") || temp.isEmpty()) && (Rainfall.equals("null") || Rainfall.isEmpty())
                        && (Terrain.equals("null") || Terrain.isEmpty()) && (Soil_PH.equals("null") || Soil_PH.isEmpty())) {
                    mFavorableTitle.setVisibility(View.GONE);
                    Temperature.setVisibility(View.GONE);
                }
                /*if ((YieldStr.equals("null") || YieldStr.isEmpty()) && (Recommended_Harvest.equals("null") || Recommended_Harvest.isEmpty()) && (Recommended_Harvest.equals("null") || Recommended_Harvest.isEmpty())) {
                    mHarvestTile.setVisibility(View.GONE);
                }*/
                setVisiable(mAboutTitle, General_Info, HabitatStr);

                /*Spanned Text = Html.fromHtml(Tree_Char);
                Log.d("Tree_Char125548",""+Tree_Char);
                mAboutTitle.setMovementMethod(LinkMovementMethod.getInstance());
                mAboutTitle.setText(Text);*/

                setVisiable(mAdditional, Reference, Carbon_Stock);
                setVisiable(mUsesTitle, Majar_Uses, Other_Uses);

                if (Market_Details.equals("null") || Market_Details.equals("")) {
                    marketdetailstitle.setVisibility(View.GONE);
                }

                /*if (IntercropsStr.equals("null") || IntercropsStr.equals("")) {
                    mAgroforest.setVisibility(View.GONE);
                }*/
                if ((Tree_Char.equals("null") || Tree_Char.equals("")) && (Conservation.equals("null") || Conservation.equals("")) && (Edibility.equals("null") || Edibility.equals("")) && (Medicinal.equals("null") || Medicinal.equals("")) &&
                        (Other_RatingStr.equals("null") || Other_RatingStr.equals("")) && (HabitStr.equals("null") || HabitStr.equals("")) && (Growth.equals("null") || Growth.equals("")) && (HeightStr.equals("null") || HeightStr.equals("")) &&
                        (Cultivation.equals("null") || Cultivation.equals("")) && (Other_Details.equals("null") || Other_Details.equals(""))) {
                    mThreeChar.setVisibility(View.GONE);
                }

                if ((PropagationStr.equals("null") || PropagationStr.equals("")) && (Plantation_TechniqueStr.equals("null") || Plantation_TechniqueStr.equals("")) && (Care_DiseaseStr.equals("null") || Care_DiseaseStr.equals(""))
                        && (IrrigationStr.equals("null") || IrrigationStr.equals(""))) {
                    mPlantationGuideTitle.setVisibility(View.GONE);
                }
                Min_Temperature = Min_Temperature.replace("<li>", "<p>\u2022 ");
                setText(Temperature, Min_Temperature, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.Climate) + " </font> : </b><br/>	" + Min_Temperature));

                TemperatureMin.setVisibility(View.GONE);
                TemperatureMax.setVisibility(View.GONE);
                //setText(TemperatureMin, Min_Temperature, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.Climate) + " </font> : </b><br/>	" + Min_Temperature));
                //setText(TemperatureMax, Max_Temperature, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.ClimateMax) + " </font> : </b><br/>	" + Max_Temperature));

                Soil = Soil.replace("<li>", "<p>\u2022 ");
                setText(mSoilTxt, Soil, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.Soil) + " </font> : </b><br/>	" + Soil));

                Altitude = Altitude.replace("<li>", "<p>\u2022 ");
                setText(mAltitudeTxt, Altitude, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.Altitude) + " </font> : </b><br/>	" + Altitude));

                Rainfall = Rainfall.replace("<li>", "<p>\u2022 ");
                setText(mRainfallTxt, Rainfall, Html.fromHtml("<b><font size='30' color='#0e7302'>" + getActivity().getString(R.string.Rainfall) + " </font> : </b><br/>\t  " + Rainfall));

                Terrain = Terrain.replace("<li>", "<p>\u2022 ");
                setText(mTerrainTxt, Terrain, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.Terrain) + " </font> : </b><br/>	" + Terrain));

                Soil_PH = Soil_PH.replace("<li>", "<p>\u2022 ");
                setText(SoilpH, Soil_PH, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.SoilpH) + " </font> : </b><br/>	" + Soil_PH));
                //General_Info = General_Info.replace("<li>", "<p>\u2022 ");

                setText(GeneralInformation, General_Info, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.descriptions) + " </font> : </b><br/>	" + General_Info));
                //GeneralInformation.setText( Html.fromHtml("www.google.com"));
                //Tree_Char = Tree_Char.replace("<li>", "<p>\u2022 ");

                setText(TreeCharacteristics, Tree_Char, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.TreeCharacteristics) + " </font> : </b><br/>	" + Tree_Char));
                ConservationStatus.setVisibility(View.GONE);
                //setText(ConservationStatus, Conservation, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.ConservationStatus) + " </font> : </b><br/>	" + Conservation));

                HabitatStr = HabitatStr.replace("<li>", "<p>\u2022 ");
                setText(Habitat, HabitatStr, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.Habitat) + " </font> : </b><br/>		 " + HabitatStr));

                EdibilityRating.setVisibility(View.GONE);
                MedicinalRating.setVisibility(View.GONE);
                //setText(EdibilityRating, Edibility, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.EdibilityRating) + " </font> : </b><br/>	" + Edibility));
                //setText(MedicinalRating, Medicinal, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.MedicinalRating) + " </font> : </b><br/>	" + Medicinal));

                Growth = Growth.replace("<li>", "<p>\u2022 ");
                setText(GrowthRate, Growth, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.GrowthRate) + " </font> : </b><br/>	" + Growth));

                HeightStr = HeightStr.replace("<li>", "<p>\u2022 ");
                setText(Height, HeightStr, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.Height) + " </font> : </b><br/>	" + HeightStr));

                HabitStr = HabitStr.replace("<li>", "<p>\u2022 ");
                setText(Habit, HabitStr, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.Habit) + " </font> : </b><br/>	" + HabitStr));

                CultivationStatus.setVisibility(View.GONE);
                OtherUsesRating.setVisibility(View.GONE);
                OtherDetails.setVisibility(View.GONE);
//                marketdetailstitle.setVisibility(View.GONE);
           /* setText(CultivationStatus, Cultivation, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.CultivationStatus) + " </font> : </b><br/>	" + Cultivation));
            setText(OtherUsesRating, Other_RatingStr, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.OtherUsesRating) + " </font> : </b><br/>	" + Other_RatingStr));
            setText(OtherDetails, Other_Details, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.OtherDetails) + " </font> : </b><br/>	" + Other_Details));*/

                PropagationStr = PropagationStr.replace("<li>", "<p>\u2022 ");
                setText(Propagation, PropagationStr, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.Propagation) + " </font> : </b><br/>	" + PropagationStr));

                Plantation_TechniqueStr = Plantation_TechniqueStr.replace("<li>", "<p>\u2022 ");
                //PlantationTechnique.setText("" + Plantation_TechniqueStr);
                setText(PlantationTechnique, Plantation_TechniqueStr, Html.fromHtml("<b><font color='#0e7302'> " + getActivity().getString(R.string.plantationTec) + " </font> : </b> <br/>" + Plantation_TechniqueStr));

                Care_DiseaseStr = Care_DiseaseStr.replace("<li>", "<p>\u2022 ");
                setText(CareDiseaseControl, Care_DiseaseStr, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.Diseasecontrol) + " </font> : </b><br/>\t" + Care_DiseaseStr));

                IrrigationStr = IrrigationStr.replace("<li>", "<p>\u2022 ");
                setText(Irrigation, IrrigationStr, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.Irrigation) + " </font> : </b><br/>	" + IrrigationStr));

                YieldStr = YieldStr.replace("<li>", "<p>\u2022 ");
                setText(Yield, YieldStr, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.Yield) + " </font> : </b><br/>	" + YieldStr));

                Recommended_Harvest = Recommended_Harvest.replace("<li>", "<p>\u2022 ");
                setText(RecommendedHarvest, Recommended_Harvest, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.RecommendedHarvest) + " </font> : </b><br/>	" + Recommended_Harvest));

                Market_Details = Market_Details.replace("<li>", "<p>\u2022 ");
                setText(MarketDetails, Market_Details, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.MarketDetails) + " </font> : </b><br/>	" + Market_Details));

                IntercropsStr = IntercropsStr.replace("<li>", "<p>\u2022 ");
                setText(Intercrops, IntercropsStr, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.Intercrops) + " </font> : </b><br/>	" + IntercropsStr));

                Majar_Uses = Majar_Uses.replace("<li>", "<p>\u2022 ");
                setText(MajorUses, Majar_Uses, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.MajorUses) + " </font> : </b><br/>	" + Majar_Uses));

                Other_Uses = Other_Uses.replace("<li>", "<p>\u2022 ");
                setText(OtherUses, Other_Uses, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.OtherUses) + " </font> : </b><br/>	" + Other_Uses));

                Carbon_Stock = Carbon_Stock.replace("<li>", "<p>\u2022 ");
                setText(CarbonStockDetails, Carbon_Stock, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.CarbonStockDetails) + " </font> : </b><br/>	" + Carbon_Stock));
                //  Log.d("Reference", "" + Reference);

                Reference = Reference.replace("<li>", "<p>\u2022 ");
                setText(References, Reference, Html.fromHtml("<b><font color='#0e7302'>" + getActivity().getString(R.string.References) + " </font> : </b><br/>	 " + Reference));

            /* Log.d("descriptionValues", " General_Info " + General_Info + " Habitat  " + Habitat + " Soil  " + Soil + " Soil_PH  " + Soil_PH + " Altitude  " + Altitude + "  Min_Temperature " + Min_Temperature +
                    "  Max_Temperature " + Max_Temperature + " Rainfall  " + Rainfall + " Terrain  " + Terrain + "  Medicinal " + Medicinal + " Tree_Char " + Tree_Char + "  Conservation " + Conservation +
                    " Edibility  " + Edibility + "  Irrigation " + Irrigation + "  Growth " + Growth + " Height  " + Height + "  Intercropping_Model " + Intercropping_Model + "  Other_Details " + Other_Details +
                    " Propagation " + Propagation + "  Plantation_Technique " + Plantation_Technique + "  Care_Disease " + Care_Disease + "  Yield " + Yield + " Recommended_Harvest  " + Recommended_Harvest
                    + " Market_Details  " + Market_Details + " Intercrops  " + Intercrops + "  Majar_Uses " + Majar_Uses + " Other_Uses " + Other_Uses + " Carbon_Stock  " + Carbon_Stock + " Reference " + Reference
                    + " lastUpdate  " + lastUpdate);*/

                createUnderLine(glossaryDetails, Tree_Char, TreeCharacteristics, getActivity().getString(R.string.TreeCharacteristics));
                createUnderLine(glossaryDetails, General_Info, GeneralInformation, getActivity().getString(R.string.descriptions));
                createUnderLine(glossaryDetails, Medicinal, MedicinalRating, getActivity().getString(R.string.MedicinalRating));
                createUnderLine(glossaryDetails, Conservation, ConservationStatus, getActivity().getString(R.string.ConservationStatus));
                createUnderLine(glossaryDetails, Edibility, EdibilityRating, getActivity().getString(R.string.EdibilityRating));
                createUnderLine(glossaryDetails, IrrigationStr, Irrigation, getActivity().getString(R.string.Irrigation));
                createUnderLine(glossaryDetails, HabitStr, Habit, getActivity().getString(R.string.Habit));
                createUnderLine(glossaryDetails, Growth, GrowthRate, getActivity().getString(R.string.GrowthRate));
                createUnderLine(glossaryDetails, HeightStr, Height, getActivity().getString(R.string.Height));
                createUnderLine(glossaryDetails, Cultivation, CultivationStatus, getActivity().getString(R.string.CultivationStatus));
                createUnderLine(glossaryDetails, Other_Details, OtherDetails, getActivity().getString(R.string.OtherDetails));
                createUnderLine(glossaryDetails, PropagationStr, Propagation, getActivity().getString(R.string.Propagation));
                createUnderLine(glossaryDetails, Plantation_TechniqueStr, PlantationTechnique, getActivity().getString(R.string.plantationTec));
                createUnderLine(glossaryDetails, Care_DiseaseStr, CareDiseaseControl, getActivity().getString(R.string.Diseasecontrol));
                createUnderLine(glossaryDetails, YieldStr, Yield, getActivity().getString(R.string.Yield));
                createUnderLine(glossaryDetails, Recommended_Harvest, RecommendedHarvest, getActivity().getString(R.string.RecommendedHarvest));
                createUnderLine(glossaryDetails, Market_Details, MarketDetails, getActivity().getString(R.string.MarketDetails));
                createUnderLine(glossaryDetails, IntercropsStr, Intercrops, getActivity().getString(R.string.Intercrops));
                createUnderLine(glossaryDetails, Majar_Uses, MajorUses, getActivity().getString(R.string.MajorUses));
                createUnderLine(glossaryDetails, Other_Uses, OtherUses, getActivity().getString(R.string.OtherUses));
                createUnderLine(glossaryDetails, Carbon_Stock, CarbonStockDetails, getActivity().getString(R.string.CarbonStockDetails));
                if (!Terrain.isEmpty() || !HabitatStr.isEmpty() || !Soil.isEmpty() || !Soil_PH.isEmpty() || !Altitude.isEmpty() || !Min_Temperature.isEmpty() || !Max_Temperature.isEmpty() &&
                        !Rainfall.isEmpty() || !Terrain.isEmpty() || !temp.isEmpty() || !Medicinal.isEmpty() || Medicinal.isEmpty() || !Tree_Char.isEmpty() || !Conservation.isEmpty() &&
                        !Edibility.isEmpty() || !IrrigationStr.isEmpty() || !HabitStr.isEmpty() || !Growth.isEmpty() || !HeightStr.isEmpty() || !Cultivation.isEmpty() || !Other_Details.isEmpty() &&
                        !PropagationStr.isEmpty() || !Plantation_TechniqueStr.isEmpty() || !Care_DiseaseStr.isEmpty() || !YieldStr.isEmpty() || !Recommended_Harvest.isEmpty() || !Market_Details.isEmpty() &&
                        !IntercropsStr.isEmpty() || !Majar_Uses.isEmpty() || !Other_Uses.isEmpty() || !Other_RatingStr.isEmpty() || !Carbon_Stock.isEmpty()) {
                    tvNodata.setVisibility(View.GONE);
                    svAll.setVisibility(View.VISIBLE);
                } else {
                    tvNodata.setVisibility(View.VISIBLE);
                    svAll.setVisibility(View.GONE);
                }
            }
        } else {
            tvNodata.setVisibility(View.VISIBLE);
            tvNodata.setText("");
            svAll.setVisibility(View.GONE);
        }
    }

    private void onClick(final ArrayList<HashMap<String, String>> description, final ArrayList<HashMap<String, String>> glossaryDetails, CustomTextView textView, final String disc, final String wordFind) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<HashMap<String, String>> noOfUnderLines = new ArrayList<HashMap<String, String>>();
                ArrayList<String> dialogListView = new ArrayList<String>();
                String itemName = "";
                String itemPoistion = "";
                if (description.size() >= 1) {
                    String discStr = description.get(0).get(disc);
                    for (int glos = 0; glos < glossaryDetails.size(); glos++) {
                        String word = glossaryDetails.get(glos).get(wordFind);
                        if (!word.equals("-") && word != null && !word.isEmpty())
                            if (discStr.contains(word)) {
                                itemPoistion = "" + glos;
                                itemName = word;
                                HashMap<String, String> stringStringHashMap = new HashMap<String, String>();
                                stringStringHashMap.put("itemPoistion", "" + itemPoistion);
                                stringStringHashMap.put("itemName", "" + itemName);
                                noOfUnderLines.add(stringStringHashMap);

                            }
                    }
                }

                if (noOfUnderLines.size() >= 2) {
                    final Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.plant_glossary);
                    dialog.setTitle(getActivity().getString(R.string.select_hyper_link));
                    ListView listView = (ListView) dialog.findViewById(R.id.listView);
                    listView.setAdapter(new ItemHyper(getActivity(), noOfUnderLines, dialog));
                    dialog.show();
                } else {

                    Intent bundle = new Intent(getActivity(), GlossaryCompare.class);
                    bundle.putExtra("itemPoistion", itemPoistion);
                    bundle.putExtra("itemName", itemName);
                    getActivity().overridePendingTransition(0, 0);
                    getActivity().startActivity(bundle);


                }


            }
        });

    }


    private Spannable changeTextView(String tv, String target) {
        String bString = tv;
        int startSpan = 0, endSpan = 0;
        Spannable spanRange = new SpannableString(bString);

        while (true) {
            startSpan = bString.indexOf(target, endSpan);
            if (startSpan < 0)
                break;
            endSpan = startSpan + target.length();
            spanRange.setSpan(
                    new StyleSpan(android.graphics.Typeface.BOLD),
                    startSpan,
                    endSpan,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spanRange;
    }


    private void createUnderLine(ArrayList<HashMap<String, String>> glossaryDetails, String disc, CustomTextView textView, String title) {
        Log.d("sdklskdlsdk", title + "  " + disc);
        if (disc != null && !disc.isEmpty()) {
            textView.setVisibility(View.VISIBLE);
            for (int glos = 0; glos < glossaryDetails.size(); glos++) {
                String word = glossaryDetails.get(glos).get("word");
                if (!word.equals("-") && !word.equals("null") && !word.isEmpty())
                    if (disc.contains(word)) {

                        //Spanned spanRange = changeTextView(Tree_Char, word);

                        String replacedWith = "<font color='#279AF2'> <a href='http://www.plantation.com'>" + word + "</a> </font>".trim();
                        disc = disc.replaceAll(word, replacedWith);
                        //TreeCharacteristics.setText(spanRange);
                        /*Linkify.addLinks(TreeCharacteristics, Linkify.WEB_URLS);
                        TreeCharacteristics.setMovementMethod(LinkMovementMethod.getInstance());
                        TreeCharacteristics.setLinkTextColor(Color.RED);*/

                        setTextChange(textView, disc, Html.fromHtml("<b><font color='#0e7302'>" + title + " </font> : </b><br/>"),
                                Html.fromHtml(disc));
                    }
            }
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    private void setTextChange(CustomTextView textView, String text, Spanned title, Spanned spanned) {
        if (text.equals("null") || text.equals("")) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(title);
            textView.append(spanned);
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

/*
    private void setText(CustomTextView textView, Spanned text, Spanned spanned) {

        if (text.equals("null") || text.equals("")) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(spanned);
        }
    }*/

    private void init() {
        svAll = (ScrollView) rootView.findViewById(R.id.svAll);
        tvNodata = (CustomTextView) rootView.findViewById(R.id.tvNodata);

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
        marketdetailstitle = (CustomTextView) rootView.findViewById(R.id.marketdetailstitle);
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
        /*mHarvestTile = (CustomTextView) rootView.findViewById(R.id.mHarvestTile);
        mAgroforest = (CustomTextView) rootView.findViewById(R.id.mAgroforest);*/
        mUsesTitle = (CustomTextView) rootView.findViewById(R.id.mUsesTitle);
        mAdditional = (CustomTextView) rootView.findViewById(R.id.customTextView4);

    }

    class ItemHyper extends BaseAdapter {
        private final ArrayList<HashMap<String, String>> noOfUnderLines;
        private final FragmentActivity mContext;
        private final Dialog dialog;

        public ItemHyper(FragmentActivity activity, ArrayList<HashMap<String, String>> noOfUnderLines, Dialog dialog) {

            this.mContext = activity;
            this.noOfUnderLines = noOfUnderLines;
            this.dialog = dialog;
        }

        @Override
        public int getCount() {
            return noOfUnderLines.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = new ViewHolder();
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_single_view, parent, false);
                holder.textView = (CustomTextView) convertView.findViewById(R.id.mSPinnerText);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.textView.setText(noOfUnderLines.get(position).get("itemName"));
            holder.textView.setTypeface(null, Typeface.NORMAL);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                    Intent bundle = new Intent(mContext, GlossaryCompare.class);
                    bundle.putExtra("itemPoistion", noOfUnderLines.get(position).get("itemPoistion"));
                    bundle.putExtra("itemName", noOfUnderLines.get(position).get("itemName"));
                    mContext.overridePendingTransition(0, 0);
                    mContext.startActivity(bundle);

                }
            });
            return convertView;
        }

    }

    class ViewHolder {
        public CustomTextView textView;
    }
}


