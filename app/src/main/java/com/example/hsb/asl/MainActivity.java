package com.example.hsb.asl;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.hsb.asl.Service.MyService;
import com.example.hsb.asl.receiver.ScreenReceiver;
import com.example.hsb.asl.utils.XLockLayer;

public class MainActivity extends AppCompatActivity {

    public static boolean isShow;
    public static View view;
    public static XLockLayer xLockLayer;

    private ScreenReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isShow = true;
        view = View.inflate(this, R.layout.activity_main, null);
        xLockLayer = XLockLayer.getInstance(this);
        xLockLayer.setLockView(view);
        xLockLayer.lock();
        mReceiver = new ScreenReceiver(MainActivity.this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(mReceiver, filter);
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        isShow = false;
    }
}
