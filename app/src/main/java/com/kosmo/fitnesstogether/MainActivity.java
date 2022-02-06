package com.kosmo.fitnesstogether;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.kosmo.fitnesstogether.view.MyPagerAdapter;
import com.kosmo.fitnesstogether.view.TabContent1;
import com.kosmo.fitnesstogether.view.TabContent2;
import com.kosmo.fitnesstogether.view.TabContent3;

import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    //권한요청 관련 상수]
    public static final int MY_REQUEST_PERMISSION_LOCATION=1;
    //현재 앱에서 필요한 권한들
    private String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    //허용이 안된 권한들을 저장할 컬렉션
    private List<String> deniedPermissions = new Vector<>();


    //androidx로 시작하는 패키지에 있는 Fragment
    private List<Fragment> fragments = new Vector<>();

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    //백키 클릭시 이전 탭으로 가기위한 변수
    private TabLayout.Tab currentTab;
    private TabLayout.Tab previousTab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //액션바 숨기기
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        //위젯 얻기
        initView();

        if(Build.VERSION.SDK_INT >=23){ //마쉬멜로우 이후 버전부터 사용자에게 권한 요청보낸다
            requestUserPermissions();
        }


        //탭메뉴 구성
        //방법1-setText() 및 setIcon()메소드 사용
        currentTab = tabLayout.newTab();
        tabLayout.addTab(currentTab.setIcon(R.drawable.home));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.stopwatch));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.search));

        //2.Fragment 생성후 컬렉션에 저장
        TabContent1 tabContent1 = new TabContent1();
        TabContent2 tabContent2 = new TabContent2();
        TabContent3 tabContent3 = new TabContent3();
        fragments.add(tabContent1);
        fragments.add(tabContent2);
        fragments.add(tabContent3);

        //3.PageAdapter객체 생성
        MyPagerAdapter adapter = new MyPagerAdapter(this, fragments);
        //4.ViewPager에 PageAdapter를 연결 - 앱 실행시 탭 메뉴 이벤트가 작동 안됨
        viewPager.setAdapter(adapter);
        //5.탭 메뉴 이벤트 처리
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //탭이 선택되었을때 자동 호출
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i("com.kosmo.app", "tab.getPosition():" + tab.getPosition());
                Log.i("com.kosmo.app", "tab.getText():" + tab.getText());
                //화면 전환하기
                viewPager.setCurrentItem(tab.getPosition());

                //이전 및 현재 탭 인덱스 저장
                //1.현재 탭을  이전 탭으로 설정
                previousTab=currentTab;
                //2.현재 탭은 선택한 탭으로 설정
                currentTab = tab;
            }
            //탭이 선택되지 않았을때
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            //탭이 다시 선택되었을때
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });


    }//////////////////onCreate

    //Home키 클릭시 자동으로 호출되는 콜백 메소드]
    @Override
    protected void onUserLeaveHint() {super.onUserLeaveHint(); }

    //백키를 누를때 자동으로 호출되는 콜백 메소드]
    @Override
    public void onBackPressed() {
        //super.onBackPressed(); 주석처리 안하면 앱 종료
        if(currentTab.getPosition() == 0){
            new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("어플리케이션 종료")
                    .setMessage("프로그램을 종료 하시겠습니까?")
                    .setPositiveButton("예",(dialog,which)->finish())
                    .setNegativeButton("아니오",null).show();
            return;
        }
        //이전 탭메뉴로 이동시키기
        tabLayout.selectTab(previousTab);
    }///////////////////


    private void initView() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
    }

    //권한 요청
    private boolean requestUserPermissions() {
        for(String permission:permissions){//permissions:앱에서 필요한 모든 권한 배열
            //권한 획득시 0,없을시 -1
            int checkPermission = ActivityCompat.checkSelfPermission(this,permission);//해당 권한 허용 여부 체크
            //권한이 없는 경우
            if(checkPermission == PackageManager.PERMISSION_DENIED){//권한이 없는 경우
                deniedPermissions.add(permission);
            }/////////
        }//////////for
        if(deniedPermissions.size() !=0){//권한이 없는게 있다면
            new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setTitle("권한 요청")
                    .setMessage("권한을 허용해야 앱을 정상적으로 사용할 수 있습니다")
                    .setPositiveButton("예",(dialog,which)->{
                        //사용자에게 권한 요청창(OS제공) 띄우기
                        ActivityCompat.requestPermissions(MainActivity.this,
                                deniedPermissions.toArray(new String[deniedPermissions.size()]),
                                MY_REQUEST_PERMISSION_LOCATION);

                    })
                    .setNegativeButton("종료",(dialog,which)->finish()).show();

            return false;//획득하지 못한 권한이 있는 경우
        }
        return true;//모든 권한이 있는 경우

    }///////////////requestUserPermissions

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case MY_REQUEST_PERMISSION_LOCATION:
                if(grantResults.length > 0){//사용자가  allow(허용)나 deny를 누른 경우
                    for(int i=0;i < permissions.length;i++){
                        if(grantResults[i]== PackageManager.PERMISSION_GRANTED){//허용한 경우
                            deniedPermissions.remove(permissions[i]);
                        }
                        else{
                            Toast.makeText(this,"권한을 허용해야만 앱을 사용할 수 있습니다",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }//for
                }
                break;
        }/////////////switch
    }///////////////////onRequestPermissionsResult

}