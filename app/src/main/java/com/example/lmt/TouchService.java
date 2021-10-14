package com.example.lmt;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import java.io.File;
import java.lang.ref.WeakReference;

public class TouchService extends Service {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String TAG = "LMT::TouchService";
    private String NOTIFICATION_CHANNEL_ID = "com.noname81.lmt.TouchService";
    private String NOTIFICATION_CHANNEL_NAME = "TouchService";
    private Launcher mLauncher;
    private MessageHandler mMessageHandler = new MessageHandler(this);
    private PieContainer mPieContainer;
    private SettingsValues mSettings;
    private Toaster mToaster;
    private TouchServiceNative mTouchServiceNative;
    private TouchServiceThread mTouchServiceThread;

    private final class TouchServiceThread extends HandlerThread {
        boolean stopped = false;

        TouchServiceThread(String name) {
            super(name);
        }

        public void run() {
            while (!this.stopped) {
                TouchService.this.mMessageHandler.sendMessage(Message.obtain(TouchService.this.mMessageHandler, 0, TouchService.this.mTouchServiceNative.run()));
            }
        }

        public boolean quit() {
            this.stopped = true;
            return true;
        }
    }

    /* access modifiers changed from: private */
    public static class MessageHandler extends Handler {
        private WeakReference<TouchService> mTouchServiceReference;

        MessageHandler(TouchService touchService) {
            this.mTouchServiceReference = new WeakReference<>(touchService);
        }

        public void handleMessage(Message msg) {
            TouchServiceResult result = (TouchServiceResult) msg.obj;
            TouchService touchService = this.mTouchServiceReference.get();
            if (touchService == null) {
                return;
            }
            if (result.getGesture() != 23 && result.getGesture() <= 13) {
                Action action = touchService.mSettings.getGestureAction(result.getGesture());
                if (action.getType() > 1 && touchService.mSettings.isNotBlacklisted()) {
                    Log.d(TouchService.TAG, "Result: " + result.toDebugString());
                    touchService.mToaster.show(result);
                    touchService.mLauncher.fireAction(action);
                }
            } else if (result.getGesture() != 23 && result.getGesture() >= 14) {
                Action action2 = touchService.mSettings.getIsaAction(result.getGesture(), result.getStartX(), result.getStartY());
                if (action2.getType() > 1 && touchService.mSettings.isNotBlacklisted()) {
                    Log.d(TouchService.TAG, "Result: " + result.toDebugString());
                    touchService.mToaster.show(result);
                    touchService.mLauncher.fireAction(action2);
                }
            }
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        Log.d(TAG, "TouchService created");
        this.mSettings = SettingsValues.getInstance(this);
        if (checkTime()) {
            this.mToaster = Toaster.getInstance(this);
            this.mLauncher = Launcher.getInstance(this);
            Log.d(TAG, "App version " + this.mSettings.getVersion());
            this.mSettings.rotate();
            if (this.mSettings.loadTouchServiceMode() < 2) {
                if (this.mTouchServiceThread == null) {
                    this.mTouchServiceThread = new TouchServiceThread("TouchServiceThread");
                }
                if (this.mTouchServiceNative == null) {
                    this.mTouchServiceNative = TouchServiceNative.getInstance(this);
                }
                if (!this.mTouchServiceThread.isAlive() && this.mTouchServiceNative.setInputDeviceUnlock(this.mSettings.loadInputDeviceString()) >= 0) {
                    this.mTouchServiceNative.setSingleTouchGestureSupport(this.mSettings.loadSingleTouchGestureSupport());
                    this.mTouchServiceNative.setSingleSwipesBBox(this.mSettings.loadSingleSwipesBBox());
                    this.mTouchServiceNative.setMinScore(this.mSettings.loadMinScore());
                    this.mTouchServiceNative.setMinPathLength(this.mSettings.loadMinPathLength());
                    this.mTouchServiceNative.setOrientation(this.mSettings.getOrientation(), (int) (((float) this.mSettings.getScreenWidth()) * this.mSettings.loadTouchscreenScreenFactorX()), (int) (((float) this.mSettings.getScreenHeight()) * this.mSettings.loadTouchscreenScreenFactorY()));
                    this.mTouchServiceThread.start();
                }
            }
            if (this.mSettings.loadTouchServiceMode() > 0 && PermissionChecker.getInstance().hasDrawOverAppsPermission(this, false)) {
                if (this.mPieContainer == null) {
                    this.mPieContainer = new PieContainer(this);
                }
                this.mPieContainer.attachToWindowManager();
                if (this.mSettings.getVisiblePieActivationAreas()) {
                    this.mPieContainer.debug();
                    this.mSettings.setVisiblePieActivationAread(false);
                }
            }
            File resourcePath = new File(this.mSettings.loadResourceSearchPath());
            int numResourceFiles = 0;
            String[] resourceFiles = resourcePath.list();
            if (resourceFiles != null) {
                numResourceFiles = resourceFiles.length;
            }
            Log.d(TAG, "Resource path " + this.mSettings.loadResourceSearchPath());
            StringBuilder sb = new StringBuilder();
            sb.append("Resource path is ");
            sb.append(resourcePath.isDirectory() ? "available" : "not available");
            sb.append(" containing ");
            sb.append(numResourceFiles);
            sb.append(" file(s)");
            Log.d(TAG, sb.toString());
            this.mSettings.setServiceState(1);
        }
        startForeground(777, createNotification());
    }

    /* access modifiers changed from: package-private */
    public Notification createNotification() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((NotificationManager) getSystemService("notification")).createNotificationChannel(new NotificationChannel(this.NOTIFICATION_CHANNEL_ID, this.NOTIFICATION_CHANNEL_NAME, 0));
        }
        if (Build.VERSION.SDK_INT < 18) {
            return new Notification();
        }
        PendingIntent contentIntent = PendingIntent.getActivity(this, 777, new Intent(this, LMT.class), 268435456);
        if (Build.VERSION.SDK_INT >= 26) {
            return new Notification.Builder(this).setContentTitle("LMT").setContentText("Touch to open LMT").setContentIntent(contentIntent).setSmallIcon(C0536R.C0537drawable.piewhite_s).setChannelId(this.NOTIFICATION_CHANNEL_ID).build();
        }
        return new Notification.Builder(this).setContentTitle("LMT").setContentText("Touch to open LMT").setContentIntent(contentIntent).setSmallIcon(C0536R.C0537drawable.piewhite_s).setPriority(-2).build();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "LMT started", 0).show();
        Log.d(TAG, "TouchService started");
        return 1;
    }

    public void onDestroy() {
        Toast.makeText(this, "LMT stopped", 0).show();
        Log.d(TAG, "TouchService stopped");
        TouchServiceNative touchServiceNative = this.mTouchServiceNative;
        if (touchServiceNative != null) {
            touchServiceNative.quit();
        }
        TouchServiceThread touchServiceThread = this.mTouchServiceThread;
        if (touchServiceThread != null && touchServiceThread.isAlive()) {
            this.mTouchServiceThread.quit();
        }
        PieContainer pieContainer = this.mPieContainer;
        if (pieContainer != null) {
            pieContainer.removeFromWindowManager();
        }
        this.mSettings.setServiceState(0);
        stopForeground(true);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        PieContainer pieContainer;
        TouchServiceNative touchServiceNative;
        super.onConfigurationChanged(newConfig);
        this.mSettings.rotate();
        if (this.mSettings.loadTouchServiceMode() < 2 && (touchServiceNative = this.mTouchServiceNative) != null) {
            touchServiceNative.setOrientation(this.mSettings.getOrientation(), (int) (((float) this.mSettings.getScreenWidth()) * this.mSettings.loadTouchscreenScreenFactorX()), (int) (((float) this.mSettings.getScreenHeight()) * this.mSettings.loadTouchscreenScreenFactorY()));
        }
        if (this.mSettings.loadTouchServiceMode() > 0 && (pieContainer = this.mPieContainer) != null) {
            pieContainer.rotate();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean checkTime() {
        if (this.mSettings.getDays() > 0) {
            return true;
        }
        Toast.makeText(this, "This version of LMT expired! Please install the latest version.", 1).show();
        return false;
    }
}