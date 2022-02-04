package com.kosmo.fitnesstogether;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.intro_layout);
        ImageView imageView = findViewById(R.id.loading);
        Glide.with(this).load(R.drawable.loading).into(imageView);
        imageView.setImageAlpha(178);

        //액션바 색상 변경-자바코드
        ActionBar actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(0x5500ff00));

        ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();
        //스레드 정의
        Runnable runnable= ()->{
            Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
            startActivity(intent);

        };
        //2초후에 스레드 실행
        worker.schedule(runnable,2, TimeUnit.SECONDS);


    }////////////onCreate


}
