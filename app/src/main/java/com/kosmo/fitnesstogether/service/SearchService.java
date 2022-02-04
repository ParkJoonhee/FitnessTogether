package com.kosmo.fitnesstogether.service;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface SearchService {

    @GET("/api/cc87557852b240dbb744/I2790/json/1/50/DESC_KOR={food}")
    Call<FoodDataDTO> search(@Path("food") String food);

    @GET("/api/cc87557852b240dbb744/C005/json/120/1/BAR_CD={barcode}")
    Call<BarcodeDataDTO> searchName(@Path("barcode") String barcode);

    @GET("/ft/aInsertFood")
    Call<Integer> aInsertFood(@QueryMap HashMap<String,String> map);

}
