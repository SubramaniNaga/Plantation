package com.fresh.mind.plantation.model;

import com.fresh.mind.plantation.model.json1.Json1TreePecieas;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AND I5 on 18-12-2017.
 */

public class PrimaryModel {
    @SerializedName("status")
    public boolean status;
    @SerializedName("Json1")
    public Json1TreePecieas json1TreePecieas;

}
