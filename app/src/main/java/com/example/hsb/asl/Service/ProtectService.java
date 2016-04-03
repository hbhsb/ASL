package com.example.hsb.asl.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ProtectService extends Service {
    public ProtectService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
        startService(intent);
        super.onDestroy();
    }
}
