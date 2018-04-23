package cn.edu.gdmec.android.boxuegu.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import cn.edu.gdmec.android.boxuegu.R;

public class SplashActivity extends AppCompatActivity {
 private TextView tv_verson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv_verson=findViewById(R.id.tv_verson);
        try {
            //获取程序包信息
            PackageInfo info=getPackageManager().getPackageInfo(getPackageName(),0);
            tv_verson.setText("ver:"+info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //让此界面延迟3秒后再跳转，timer中有一个线程，这个线程不断执行task
        Timer timer=new Timer();
        //TimerTask类表示一个在指定时间内执行的task
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        };
        timer.schedule(task,3000);
    }
}
