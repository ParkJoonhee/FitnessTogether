package com.kosmo.fitnesstogether;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.kosmo.fitnesstogether.service.LoginService;
import com.kosmo.fitnesstogether.service.MemberDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private EditText id;
    private EditText pwd;
    private Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        //타이틀바 색상 변경-자바코드
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0x99FF0000));
        //위젯 얻기]
        initView();
        //버튼 배경 투명처리
        btnLogin.getBackground().setAlpha(178);
        //버튼에 리스너 부착
        btnLogin.setOnClickListener(v->{
            //프로그래스바용 다이얼로그 생성]
            //빌더 생성 및 다이얼로그창 설정
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this)
                    .setCancelable(false)
                    .setIcon(android.R.drawable.ic_menu_compass)
                    .setTitle("로그인")
                    .setView(R.layout.progress_layout);
            //빌더로 다이얼로그창 생성
            AlertDialog dialog = builder.create();
            dialog.show();
            //사용자 입력값 받기
            String username = id.getText().toString();
            String password = pwd.getText().toString();
            //스프링 서버로 요청 보내기
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(JacksonConverterFactory.create())
                    .baseUrl("http://192.168.0.8:8686/")
                    .build();

            LoginService loginService=retrofit.create(LoginService.class);
            Call<MemberDTO> call =loginService.aMemberIsLogin(username,password);
            call.enqueue(new Callback<MemberDTO>() {
                @Override
                public void onResponse(Call<MemberDTO> call, Response<MemberDTO> response) {
                    Log.i("com.kosmo.app","응답코드:"+response.code());
                    Log.i("com.kosmo.app","isSuccessful:"+response.isSuccessful());

                    if(response.isSuccessful()){
                        MemberDTO member=response.body();
                        Log.i("com.kosmo.app","member:"+member);

                        if(member.getId() !=null) {//회원
                            //컨텐츠 화면(MainActivity)으로 전환
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);                        //finish()불필요-NO_HISTORY로 설정했기때문에(매니페스트에서)

                            //다른 화면에서 로그인 여부 판단을 위한 아이디 저장
                            SharedPreferences preferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();

                            editor.putString("username", member.getId());
                            editor.putString("password", member.getPwd());
                            editor.commit();
                        }
                        else{//비회원
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setIcon(android.R.drawable.ic_dialog_map)
                                    .setTitle("로그인 결과")
                                    .setMessage("아이디와 비번 불일치")
                                    .setPositiveButton("확인",null)
                                    .show();
                        }

                    }
                    else{
                        Log.i("com.kosmo.app","응답에러:"+response.errorBody());
                    }
                    SystemClock.sleep(1000);//속도가 너무 빨라서 다이얼로그창 실행되는 보여주기 위함
                    //다이얼로그 닫기
                    dialog.dismiss();

                }
                @Override
                public void onFailure(Call<MemberDTO> call, Throwable t) {t.printStackTrace();}
            });
        });
    }

    private void initView() {
        id = findViewById(R.id.id);
        pwd = findViewById(R.id.pwd);
        btnLogin = findViewById(R.id.btn_login);
    }
}
