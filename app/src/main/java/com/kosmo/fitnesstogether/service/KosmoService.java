package com.kosmo.fitnesstogether.service;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface KosmoService {

    //Call<T> T는 서버로부터 받은 데이타를 저장할 데이타 타입
    //로그인용
    @GET("/membership")
    Call<MemberDTO> isMember(@Query("username") String username,@Query("password") String password);
}
