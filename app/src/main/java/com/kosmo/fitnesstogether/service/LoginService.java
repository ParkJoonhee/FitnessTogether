package com.kosmo.fitnesstogether.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LoginService {

    //Call<T> T는 서버로부터 받은 데이타를 저장할 데이타 타입
    //로그인용
    @GET("/ft/membership/{username}/{password}")
    Call<MemberDTO> aMemberIsLogin(@Path("username") String username, @Path("password") String password);
}
