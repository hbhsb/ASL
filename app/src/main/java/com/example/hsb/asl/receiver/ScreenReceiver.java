package com.example.hsb.asl.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;

import com.example.hsb.asl.MainActivity;

public class ScreenReceiver extends BroadcastReceiver {
    private Activity activity;

    public ScreenReceiver() {
    }

    public ScreenReceiver(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        FingerprintManager manager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
        ACallback callback = new ACallback();
        if (action.equals(Intent.ACTION_SCREEN_ON)) {
            if (activity != null) {
                activity.finish();
            }
            manager.authenticate(null, null, 0, callback, null);
        }
        if (!MainActivity.isShow) {
            MainActivity.isShow = true;
            Intent jmpMAC = new Intent(context, MainActivity.class);
            jmpMAC.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(jmpMAC);
        }
    }

    public class ACallback extends FingerprintManager.AuthenticationCallback {
        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
            MainActivity.xLockLayer.unlock();
        }
    }
}
