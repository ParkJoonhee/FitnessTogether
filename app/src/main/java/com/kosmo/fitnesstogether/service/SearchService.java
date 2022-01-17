package com.kosmo.fitnesstogether.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SearchService {

    @GET("/api/cb8f0be721a04b56a16d/I2790/json/1/50/DESC_KOR={food}")
    Call<I2790> search(@Path("food") String food);

}
