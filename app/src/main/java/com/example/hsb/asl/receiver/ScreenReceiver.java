package com.example.hsb.asl.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.util.Log;

import com.example.hsb.asl.MainActivity;
import com.example.hsb.asl.utils.XLockLayer;

public class ScreenReceiver extends BroadcastReceiver {
    public static FingerprintManager manager;
    private Activity activity;


    public ScreenReceiver() {
    }

    public ScreenReceiver(Activity activity) {
        this.activity = activity;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        manager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);

        ACallback callback = new ACallback();
        if (action.equals(Intent.ACTION_SCREEN_ON)) {
            if (activity != null) {
                activity.finish();
            }
            manager.authenticate(null, null, 0, callback, null);
        }

        if (MainActivity.isShow) {

        } else if (!MainActivity.isShow && !XLockLayer.isLocked) {
            MainActivity.isShow = true;
            Intent jmpMAC = new Intent(context, MainActivity.class);
            jmpMAC.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(jmpMAC);
        }
    }

    public class ACallback extends FingerprintManager.AuthenticationCallback {
        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
            Log.e("hhhh", "认证了help");
            MainActivity.xLockLayer.unlock();
//            Log.e("hhhh", "isLock：" + XLockLayer.isLocked);
            manager.authenticate(null, null, 0, new VoidBack(), null);
            MainActivity.isShow = false;
            if (activity != null) {
                activity.finish();
            }
        }

        @Override
        public void onAuthenticationFailed() {
            Log.e("hhhh", "认证了Failed");
            MainActivity.xLockLayer.unlock();
//            Log.e("hhhh", "isLock：" + XLockLayer.isLocked);
            manager.authenticate(null, null, 0, new VoidBack(), null);
            MainActivity.isShow = false;
            if (activity != null) {
                activity.finish();
            }
        }

    }

    public class VoidBack extends FingerprintManager.AuthenticationCallback {
        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
//            Log.e("hhhh", "一个无用的回调");
        }
    }
}
