package com.kosmo.fitnesstogether.view;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosmo.fitnesstogether.Adapter.SearchAdapter;
import com.kosmo.fitnesstogether.MainActivity;
import com.kosmo.fitnesstogether.R;
import com.kosmo.fitnesstogether.service.FoodDataDTO;
import com.kosmo.fitnesstogether.service.I2790;
import com.kosmo.fitnesstogether.service.SearchService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


//1]Fragement상속
//※androidx.fragment.app.Fragment 상속
public class TabContent3 extends Fragment {

    private Retrofit retrofit;
    private EditText food;
    private Button btnSearch;
    private SearchAdapter adapter;
    private RecyclerView recyclerView;
    private AlertDialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //2]onCreateView()오버 라이딩
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabmenu3_layout, container, false);

        food = view.findViewById(R.id.food);
        btnSearch = view.findViewById(R.id.btnSearch);
        recyclerView = view.findViewById(R.id.recyclerView);

        btnSearch.setOnClickListener(v -> {

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://openapi.foodsafetykorea.go.kr")
                    .addConverterFactory(JacksonConverterFactory.create()).build();

            SearchService searchService=retrofit.create(SearchService.class);
            Call<FoodDataDTO> call=searchService.search(food.getText().toString());
            call.enqueue(new Callback<FoodDataDTO>() {
                @Override
                public void onResponse(Call<FoodDataDTO> call, Response<FoodDataDTO> response) {
                    Log.i("com.kosmo.app",String.valueOf(response.code()));

                    if(response.isSuccessful()){
                        I2790 items= response.body().getI2790();
                        //응답결과 전체를 문자열로 변환해서 출력 해보자
                        try {
                            ObjectMapper mapper = new ObjectMapper();                 ;
                            Log.i("com.kosmo.app",mapper.writerWithDefaultPrettyPrinter().writeValueAsString(items));
                        }
                        catch(JsonProcessingException e){e.printStackTrace();}
                        //리사이클러뷰에 표시
                        adapter = new SearchAdapter(TabContent3.this.getContext(),items,R.layout.data_items_layout);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(TabContent3.this.getContext()));
                    }
                    else{
                        Log.i("com.kosmo.app","에러:"+response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<FoodDataDTO> call, Throwable t) {
                    Log.i("com.kosmo.app",t.getMessage());
                }
            });
        });

        return view;
    }

}
