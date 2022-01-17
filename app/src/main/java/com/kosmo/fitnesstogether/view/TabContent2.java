package com.kosmo.fitnesstogether.view;


import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kosmo.fitnesstogether.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TabContent2 extends Fragment {

    private Chronometer chronometer;
    private Button btnStart;
    private Button btnReset;

    private long millisecondTime = 0;
    private long pauseOffset=0;
    long elapsedTime=-32400000;
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.KOREAN);
    String timeString=null;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //2]onCreateView()오버 라이딩
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabmenu2_layout, null);
        chronometer = view.findViewById(R.id.chronometer);
        btnStart = view.findViewById(R.id.btnStart);
        btnReset = view.findViewById(R.id.btnReset);

        timeString=dateFormat.format(new Date(elapsedTime));
        chronometer.setText(timeString);

        chronometer.setOnChronometerTickListener(chronometer -> {
            elapsedTime=SystemClock.elapsedRealtime()-chronometer.getBase()-32400000;
            timeString=dateFormat.format(new Date(elapsedTime));
            chronometer.setText(timeString);
        });
        btnStart.setOnClickListener(v->{
            Timer();
        });
        btnReset.setOnClickListener(v -> {
            elapsedTime=-32400000;
            pauseOffset=0;
            timeString=dateFormat.format(new Date(elapsedTime));
            chronometer.setText(timeString);

        });

        return view;
    }
    public void Timer() {
        if(btnStart.getText().equals("시작")){
            chronometer.setBase(SystemClock.elapsedRealtime()-pauseOffset);
            chronometer.start();
            btnStart.setText("중지");
            btnReset.setEnabled(false);
        }
        else {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            btnStart.setText("시작");
            btnReset.setEnabled(true);
        }

    }



}
