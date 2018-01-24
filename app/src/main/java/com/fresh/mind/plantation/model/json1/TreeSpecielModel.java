package com.fresh.mind.plantation.model.json1;

import com.google.gson.annotations.SerializedName;

/**
 * Created by AND I5 on 18-12-2017.
 */

public class TreeSpecielModel {

    @SerializedName("TreeName")
    public String TreeName;

    @SerializedName("ScientificName")
    public String ScientificName;

    @SerializedName("TreeType")
    public String TreeType;

    @SerializedName("TreeNameTamil")
    public String TreeNameTamil;

    @SerializedName("ScientificTamil")
    public String ScientificTamil;

    @SerializedName("TreeTypeTamil")
    public String TreeTypeTamil;


    @SerializedName("District")
    public String District;

    @SerializedName("DistrictTamil")
    public String DistrictTamil;

    @SerializedName("Images")
    public String Images;


    @SerializedName("common_key")
    public String common_key;

    @SerializedName("LastUpdate")
    public String LastUpdate;
}
