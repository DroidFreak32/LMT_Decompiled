package com.example.lmt;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.GestureDescription;
import android.app.Notification;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

public class AccessibilityHandler extends AccessibilityService {
    private static boolean DEBUG = false;
    private static final String TAG = "LMT::Accessibility";
    private static AccessibilityHandler instance = null;
    private static boolean mInitialized = false;
    private NotificationDataHelper mNotificationDataHelper;

    public void onAccessibilityEvent(AccessibilityEvent event) {
        ApplicationInfo ai;
        String text;
        if (event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED && event.getClassName().equals("android.app.Notification")) {
            Notification notification = (Notification) event.getParcelableData();
            String packageName = String.valueOf(event.getPackageName());
            PackageManager pm = getPackageManager();
            Drawable icon = null;
            try {
                ai = pm.getApplicationInfo(packageName, 0);
                icon = pm.getResourcesForApplication(packageName).getDrawable(notification.icon);
            } catch (Exception e) {
                ai = null;
            }
            String name = (String) (ai != null ? pm.getApplicationLabel(ai) : BuildConfig.FLAVOR);
            long time = (System.currentTimeMillis() - SystemClock.uptimeMillis()) + event.getEventTime();
            if (event.getText().size() > 0) {
                text = String.valueOf(event.getText().get(0));
            } else {
                text = getString(R.string.pie_status_info_no_additional_info);
            }
            if (this.mNotificationDataHelper == null) {
                this.mNotificationDataHelper = NotificationDataHelper.getInstance();
            }
            NotificationDataHelper notificationDataHelper = this.mNotificationDataHelper;
            if (notificationDataHelper != null) {
                notificationDataHelper.addNotificationData(name, time, text, notification.contentIntent, notification.deleteIntent, icon);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onServiceConnected() {
        if (mInitialized) {
            instance = this;
            return;
        }
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = 64;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        setServiceInfo(info);
        this.mNotificationDataHelper = NotificationDataHelper.getInstance();
        instance = this;
        mInitialized = true;
    }

    public void onInterrupt() {
        instance = null;
        mInitialized = false;
    }

    static void setDebug(int value) {
        boolean z = true;
        if (value != 1) {
            z = false;
        }
        DEBUG = z;
    }

    static boolean isAccessibilityAvailable(Context context, boolean trace) {
        boolean result = Build.VERSION.SDK_INT >= 16 && mInitialized && instance != null;
        if (trace && !result) {
            Toast.makeText(context, (int) R.string.accessibility_activate_lmts_accessibility_service, Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Please activate LMT's accessibility service!");
        }
        return result;
    }

    static void performAction(int action) {
        AccessibilityHandler accessibilityHandler;
        if (DEBUG) {
            Log.d(TAG, "performAction(" + action + ")");
        }
        if (mInitialized && (accessibilityHandler = instance) != null) {
            boolean result = accessibilityHandler.performGlobalAction(action);
            if (DEBUG) {
                Log.d(TAG, "result: " + result);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static GestureDescription createClick(float x, float y) {
        Path clickPath = new Path();
        clickPath.moveTo(x, y);
        GestureDescription.StrokeDescription clickStroke = new GestureDescription.StrokeDescription(clickPath, 0, 1);
        GestureDescription.Builder clickBuilder = new GestureDescription.Builder();
        clickBuilder.addStroke(clickStroke);
        return clickBuilder.build();
    }

    static void performClick(float x, float y) {
        AccessibilityHandler accessibilityHandler;
        if (DEBUG) {
            Log.d(TAG, "performClick(x " + x + " y " + y + ")");
        }
        if (Build.VERSION.SDK_INT >= 24 && mInitialized && (accessibilityHandler = instance) != null) {
            boolean result = accessibilityHandler.dispatchGesture(createClick(x, y), new AccessibilityService.GestureResultCallback() {
                /* class com.noname81.lmt.AccessibilityHandler.C05121 */

                public void onCompleted(GestureDescription gestureDescription) {
                    super.onCompleted(gestureDescription);
                    if (AccessibilityHandler.DEBUG) {
                        Log.d(AccessibilityHandler.TAG, "gesture completed");
                    }
                }

                public void onCancelled(GestureDescription gestureDescription) {
                    super.onCancelled(gestureDescription);
                    if (AccessibilityHandler.DEBUG) {
                        Log.d(AccessibilityHandler.TAG, "gesture cancelled");
                    }
                }
            }, null);
            if (DEBUG) {
                Log.d(TAG, "gesture result: " + result);
            }
        }
    }
}