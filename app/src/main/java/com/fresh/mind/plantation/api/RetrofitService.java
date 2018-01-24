package com.fresh.mind.plantation.api;

import com.fresh.mind.plantation.model.PrimaryModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface RetrofitService {


    /**
     * Get Primary details from this link
     * @param taskName employee id
     * @return
     */
    @FormUrlEncoded
    @POST("testapi.php")
    Call<PrimaryModel> callCoreData(@Field("task") String taskName);
}
