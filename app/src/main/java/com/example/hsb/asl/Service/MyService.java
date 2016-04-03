package com.example.hsb.asl.Service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.example.hsb.asl.receiver.ScreenReceiver;

public class MyService extends Service {
    private ScreenReceiver receiver;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        receiver = new ScreenReceiver();
        registerReceiver(receiver, filter);
        Intent intent = new Intent(this, ProtectService.class);
        startService(intent);
    }

    @Override
    public void onDestroy() {
        Intent intent = new Intent(this, ProtectService.class);
        stopService(intent);
//        startService(intent);
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
