package com.kosmo.fitnesstogether.view;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kosmo.fitnesstogether.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class TabContent1 extends Fragment {
    private String username;
    private String password;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences =
                getContext().getSharedPreferences("loginInfo", Activity.MODE_PRIVATE);
        username = preferences.getString("username",null);
        password = preferences.getString("password",null);
    }

    //2]onCreateView()오버 라이딩
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.tabmenu1_layout,container,false);
        WebView webView = view.findViewById(R.id.webview);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        //webView.loadUrl("http://192.168.0.8:8686/ft");

        try {
            String params = "id=" + URLEncoder.encode(username, "UTF-8") + "&pwd=" + URLEncoder.encode(password, "UTF-8") + "&loginType=ft";
            webView.postUrl("https://92af-115-91-88-228.ngrok.io/ft/fnt/LoginProcess.do",params.getBytes());
        }
        catch(UnsupportedEncodingException e){e.printStackTrace();}
        return view;
    }
}
