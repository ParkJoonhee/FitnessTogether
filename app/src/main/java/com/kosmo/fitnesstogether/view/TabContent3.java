package com.kosmo.fitnesstogether.view;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;
import com.kosmo.fitnesstogether.Adapter.SearchAdapter;
import com.kosmo.fitnesstogether.R;
import com.kosmo.fitnesstogether.service.BarcodeDataDTO;
import com.kosmo.fitnesstogether.service.C005;
import com.kosmo.fitnesstogether.service.FoodDataDTO;
import com.kosmo.fitnesstogether.service.I2790;
import com.kosmo.fitnesstogether.service.SearchService;
import com.kosmo.fitnesstogether.service.row2;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


public class TabContent3 extends Fragment {

    private Retrofit retrofit;
    private EditText food;
    private Button btnSearch;
    private Button btnBarcode;
    private SearchAdapter adapter;
    private RecyclerView recyclerView;

    private Context context;
    private Activity activity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof Activity)
            activity = (Activity) context;
    }


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
        btnBarcode = view.findViewById(R.id.btnBarcode);
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
                        adapter = new SearchAdapter(context,items,R.layout.data_items_layout);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
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
        btnBarcode.setOnClickListener(v -> {
            IntentIntegrator integrator = new IntentIntegrator(activity);
            barcodeIntent.launch(integrator.createScanIntent());
        });

        return view;
    }

    ActivityResultLauncher<Intent>  barcodeIntent= registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    switch(result.getResultCode()) {
                        case Activity.RESULT_OK:
                            Intent data = result.getData();
                            if (data == null) {
                                Toast.makeText(context, "바코드 가져오기 실패", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            String barcode=data.getStringExtra(Intents.Scan.RESULT);


                            retrofit = new Retrofit.Builder()
                                    .baseUrl("http://openapi.foodsafetykorea.go.kr/")
                                    .addConverterFactory(JacksonConverterFactory.create()).build();

                            SearchService searchService=retrofit.create(SearchService.class);
                            Call<BarcodeDataDTO> call=searchService.searchName(barcode);
                            call.enqueue(new Callback<BarcodeDataDTO>() {
                                @Override
                                public void onResponse(Call<BarcodeDataDTO> call, Response<BarcodeDataDTO> response) {
                                    Log.i("com.kosmo.app",String.valueOf(response.code()));

                                    if(response.isSuccessful()){
                                        String name=null;
                                        List<row2> items= response.body().getC005().getRow();
                                        for(row2 item: items){
                                            name=item.getPRDLST_NM();
                                        }
                                        //응답결과 전체를 문자열로 변환해서 출력 해보자
                                        try {
                                            ObjectMapper mapper = new ObjectMapper();                 ;
                                            Log.i("com.kosmo.app",mapper.writerWithDefaultPrettyPrinter().writeValueAsString(items));
                                        }
                                        catch(JsonProcessingException e){e.printStackTrace();}

                                        food.setText(name);
                                    }
                                    else{
                                        Log.i("com.kosmo.app","에러:"+response.errorBody());
                                    }
                                }

                                @Override
                                public void onFailure(Call<BarcodeDataDTO> call, Throwable t) {
                                    Log.i("com.kosmo.app",t.getMessage());
                                }
                            });

                            break;
                    }
                }
            }

    );

}
