package com.kosmo.fitnesstogether.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SearchService {

    @GET("/api/cc87557852b240dbb744/I2790/json/1/50/DESC_KOR={food}")
    Call<FoodDataDTO> search(@Path("food") String food);


    @GET("/api/cc87557852b240dbb744/C005/json/1/1/BAR_CD={barcode}")
    Call<BarcodeDataDTO> searchName(@Path("barcode") String barcode);

}
