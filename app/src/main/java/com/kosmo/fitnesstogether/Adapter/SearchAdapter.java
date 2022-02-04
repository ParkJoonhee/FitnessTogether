package com.kosmo.fitnesstogether.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.kosmo.fitnesstogether.LoginActivity;
import com.kosmo.fitnesstogether.R;
import com.kosmo.fitnesstogether.service.BarcodeDataDTO;
import com.kosmo.fitnesstogether.service.FoodDataDTO;
import com.kosmo.fitnesstogether.service.I2790;
import com.kosmo.fitnesstogether.service.SearchService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private Retrofit retrofit;
    private String username;
    private Context context;
    private I2790 items;
    private int itemLayoutResId;

    public SearchAdapter(Context context, I2790 items, int itemLayoutResId) {
        this.context = context;
        this.items = items;
        this.itemLayoutResId = itemLayoutResId;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.i("com.kosmo.app",String.valueOf(context));
        return new ViewHolder(LayoutInflater.from(context).inflate(itemLayoutResId, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemName.setText(items.getRow().get(position).getDESC_KOR());
        holder.itemOne.setText(items.getRow().get(position).getSERVING_SIZE());
        holder.itemKcal.setText(items.getRow().get(position).getNUTR_CONT1());
        holder.itemTan.setText(items.getRow().get(position).getNUTR_CONT2());
        holder.itemDan.setText(items.getRow().get(position).getNUTR_CONT3());
        holder.itemGi.setText(items.getRow().get(position).getNUTR_CONT4());
        HashMap<String,String> map = new HashMap<>();
        map.put("f_name",items.getRow().get(position).getDESC_KOR());
        map.put("f_size",items.getRow().get(position).getSERVING_SIZE());
        map.put("f_kcal",items.getRow().get(position).getNUTR_CONT1());
        map.put("f_tan",items.getRow().get(position).getNUTR_CONT2());
        map.put("f_dan",items.getRow().get(position).getNUTR_CONT3());
        map.put("f_gi",items.getRow().get(position).getNUTR_CONT4());
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
        String getTime = sdf.format(date);
        map.put("postdate",getTime);

        SharedPreferences preferences =
                context.getSharedPreferences("loginInfo", Activity.MODE_PRIVATE);
        username = preferences.getString("username",null);
        map.put("id",username);
        holder.cardView.setOnClickListener(v->{
            new AlertDialog.Builder(context)
                    .setIcon(android.R.drawable.ic_menu_save)
                    .setTitle("제품선택")
                    .setMessage("오늘 캘린더에 추가하시겠습니까?")
                    .setPositiveButton("확인",(dialog, which) -> {
                        sendData(map);
                    })
                    .setNegativeButton("취소",null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return items.getRow().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView itemName;
        private TextView itemOne;
        private TextView itemKcal;
        private TextView itemTan;
        private TextView itemDan;
        private TextView itemGi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
            itemName = itemView.findViewById(R.id.itemName);
            itemOne = itemView.findViewById(R.id.itemOne);
            itemKcal = itemView.findViewById(R.id.itemKcal);
            itemTan = itemView.findViewById(R.id.itemTan);
            itemDan = itemView.findViewById(R.id.itemDan);
            itemGi = itemView.findViewById(R.id.itemGi);
        }

    }

    private void sendData(HashMap<String,String> map){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://dafa-115-91-88-226.ngrok.io/")
                .addConverterFactory(JacksonConverterFactory.create()).build();

        SearchService searchService=retrofit.create(SearchService.class);
        Call<Integer> call=searchService.aInsertFood(map);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.i("com.kosmo.app",String.valueOf(response.code()));

                if(response.isSuccessful()){
                    if(response.body()!=0){
                        new AlertDialog.Builder(context)
                                .setIcon(android.R.drawable.ic_menu_save)
                                .setTitle("제품선택")
                                .setMessage("캘린더에 추가하였습니다.")
                                .setPositiveButton("확인",null)
                                .show();
                    }
                    else {
                        Log.i("com.kosmo.app","에러11:"+response.body());
                    }
                }
                else{
                    Log.i("com.kosmo.app","에러:"+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.i("com.kosmo.app",t.getMessage());
            }
        });
    }
}
