package com.example.lmt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartupReceiver extends BroadcastReceiver {
    private static final String TAG = "LMT::StartupReceiver";

    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            startIfRequested(context);
        }
    }

    /* access modifiers changed from: package-private */
    public void startIfRequested(Context context) {
        SettingsValues settings = SettingsValues.getInstance(context);
        if (settings.loadAutostart() > 0) {
            Log.d(TAG, "Restarting TouchService");
            settings.startService();
        }
    }
}