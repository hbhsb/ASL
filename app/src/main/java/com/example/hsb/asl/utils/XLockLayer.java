package com.example.hsb.asl.utils;

import android.app.Activity;
import android.view.View;
import android.view.WindowManager;

/**
 * 以X开头的类名，说明它很6
 * Created by 黄帅博 on 4/3/2016.
 */
public class XLockLayer {
    public static boolean isLocked;
    private static XLockLayer mXLockLayer;
    private Activity mActivity;
    private WindowManager mWindowsManager;
    private View mLockView;
    private WindowManager.LayoutParams mLockViewLayoutParams;

    private XLockLayer(Activity activity) {
        mActivity = activity;
        init();
    }

    public static synchronized XLockLayer getInstance(Activity activity) {
        if (mXLockLayer == null) {
            mXLockLayer = new XLockLayer(activity);
        }
        return mXLockLayer;
    }

    private void init() {
        isLocked = false;
        mWindowsManager = mActivity.getWindowManager();
        mLockViewLayoutParams = new WindowManager.LayoutParams();
        mLockViewLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mLockViewLayoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        //实现屏幕锁定的关键
        mLockViewLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
    }

    public synchronized void lock() {
        if (mLockView != null && !isLocked) {
            mWindowsManager.addView(mLockView, mLockViewLayoutParams);
        }
        isLocked = true;
    }

    public synchronized void unlock() {
        if (mWindowsManager != null && isLocked) {
            mWindowsManager.removeView(mLockView);
        }
        isLocked = false;
    }

    public synchronized void setLockView(View v) {
        mLockView = v;
    }
}
