package com.example.lmt;

import android.content.Context;
import android.util.Log;

/* access modifiers changed from: package-private */
public class TouchServiceNative {
    private static final String TAG = "LMT::TouchServiceNative";
    private static TouchServiceNative instance = null;
    private RootContext mRootContext;

    /* access modifiers changed from: package-private */
    public native String getServiceVersion();

    /* access modifiers changed from: package-private */
    public native void quit();

    /* access modifiers changed from: package-private */
    public native TouchServiceResult run();

    /* access modifiers changed from: package-private */
    public native int setDebug(int i);

    /* access modifiers changed from: package-private */
    public native int setInputDevice(String str);

    /* access modifiers changed from: package-private */
    public native int setMinPathLength(int i);

    /* access modifiers changed from: package-private */
    public native int setMinScore(int i);

    /* access modifiers changed from: package-private */
    public native int setOrientation(int i, int i2, int i3);

    /* access modifiers changed from: package-private */
    public native int setSingleSwipesBBox(int i);

    /* access modifiers changed from: package-private */
    public native int setSingleTouchGestureSupport(int i);

    static {
        System.loadLibrary("TouchServiceNative");
    }

    private TouchServiceNative(Context context) {
        this.mRootContext = RootContext.getInstance(context);
    }

    static TouchServiceNative getInstance(Context context) {
        if (instance == null) {
            instance = new TouchServiceNative(context);
        }
        return instance;
    }

    /* access modifiers changed from: package-private */
    public int setInputDeviceUnlock(String device) {
        this.mRootContext.runCommandRemote("version", false);
        Log.d(TAG, "Lib version " + getServiceVersion());
        Log.d(TAG, "Set input device");
        return setInputDevice(device);
    }
}