package com.example.lmt;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Process;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

/* access modifiers changed from: package-private */
public class PermissionChecker {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static int DRAW_OVER_APPS_REQUEST_CODE = 5469;
    private static final String TAG = "LMT::PermissionChecker";
    static int USAGE_STATS_REQUEST_CODE = 5470;
    private static PermissionChecker instance = null;

    private PermissionChecker() {
    }

    public static PermissionChecker getInstance() {
        if (instance == null) {
            instance = new PermissionChecker();
        }
        return instance;
    }

    /* access modifiers changed from: package-private */
    public boolean hasPhoneCallPermission(Context context, boolean trace) {
        boolean result = true;
        if (Build.VERSION.SDK_INT >= 23) {
            result = ContextCompat.checkSelfPermission(context, "android.permission.CALL_PHONE") == 0;
        }
        if (trace) {
            Log.d(TAG, "hasPhoneCallPermission(" + result + ")");
        }
        return result;
    }

    /* access modifiers changed from: package-private */
    public boolean checkAndRequestPhoneCallPermission(Activity activity, boolean trace) {
        if (Build.VERSION.SDK_INT < 23 || hasPhoneCallPermission(activity, trace)) {
            return true;
        }
        Toast.makeText(activity, (int) R.string.app_please_grant_phone_call_permission, Toast.LENGTH_LONG).show();
        ActivityCompat.requestPermissions(activity, new String[]{"android.permission.CALL_PHONE"}, 0);
        return hasPhoneCallPermission(activity, trace);
    }

    /* access modifiers changed from: package-private */
    public boolean hasExternalStorageReadPermission(Context context, boolean trace) {
        boolean result = true;
        if (Build.VERSION.SDK_INT >= 23) {
            result = ContextCompat.checkSelfPermission(context, "android.permission.READ_EXTERNAL_STORAGE") == 0;
        }
        if (trace) {
            Log.d(TAG, "hasExternalStorageReadPermission(" + result + ")");
        }
        return result;
    }

    /* access modifiers changed from: package-private */
    public boolean checkAndRequestExternalStorageReadPermission(Activity activity, boolean trace) {
        if (Build.VERSION.SDK_INT < 23 || hasExternalStorageReadPermission(activity, trace)) {
            return true;
        }
        Toast.makeText(activity, (int) R.string.app_please_grant_external_storage_read_permission, Toast.LENGTH_LONG).show();
        ActivityCompat.requestPermissions(activity, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 0);
        return hasExternalStorageReadPermission(activity, trace);
    }

    /* access modifiers changed from: package-private */
    public boolean hasExternalStorageWritePermission(Context context, boolean trace) {
        boolean result = true;
        if (Build.VERSION.SDK_INT >= 23) {
            result = ContextCompat.checkSelfPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE") == 0;
        }
        if (trace) {
            Log.d(TAG, "hasExternalStorageWritePermission(" + result + ")");
        }
        return result;
    }

    /* access modifiers changed from: package-private */
    public boolean checkAndRequestExternalStorageWritePermission(Activity activity, boolean trace) {
        if (Build.VERSION.SDK_INT < 23 || hasExternalStorageWritePermission(activity, trace)) {
            return true;
        }
        Toast.makeText(activity, (int) R.string.app_please_grant_external_storage_write_permission, Toast.LENGTH_LONG).show();
        ActivityCompat.requestPermissions(activity, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 0);
        return hasExternalStorageWritePermission(activity, trace);
    }

    /* access modifiers changed from: package-private */
    public boolean hasDrawOverAppsPermission(Context context, boolean trace) {
        boolean result = true;
        if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(context)) {
            result = false;
        }
        if (trace) {
            Log.d(TAG, "hasDrawOverAppsPermission(" + result + ")");
        }
        return result;
    }

    /* access modifiers changed from: package-private */
    public boolean checkAndRequestDrawOverAppsPermission(Activity activity, boolean trace) {
        if (Build.VERSION.SDK_INT < 23 || hasDrawOverAppsPermission(activity, trace)) {
            return true;
        }
        Toast.makeText(activity, (int) R.string.app_please_grant_draw_over_apps_permission, Toast.LENGTH_LONG).show();
        Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + activity.getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivityForResult(intent, DRAW_OVER_APPS_REQUEST_CODE);
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean hasUsageStatsPermission(Context context, boolean trace) {
        boolean result = true;
        if (Build.VERSION.SDK_INT >= 23) {
            result = ((AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE)).checkOpNoThrow("android:get_usage_stats", Process.myUid(), context.getPackageName()) == 0;
        }
        if (trace) {
            Log.d(TAG, "hasUsageStatsPermission(" + result + ")");
        }
        return result;
    }

    /* access modifiers changed from: package-private */
    public boolean checkAndRequestUsageStatsPermission(Activity activity, boolean trace) {
        if (Build.VERSION.SDK_INT < 23 || hasUsageStatsPermission(activity, trace)) {
            return true;
        }
        Toast.makeText(activity, (int) R.string.app_please_grant_usage_stats_permission, Toast.LENGTH_LONG).show();
        Intent intent = new Intent("android.settings.USAGE_ACCESS_SETTINGS");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivityForResult(intent, USAGE_STATS_REQUEST_CODE);
        return false;
    }
}