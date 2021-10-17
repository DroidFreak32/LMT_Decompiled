package com.example.lmt;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v7.widget.ActivityChooserView;
//import androidx.appcompat.widget.ActivityChooserView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

class Launcher {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static boolean DEBUG = false;
    private static final String TAG = "LMT::Launcher";
    private static Launcher instance = null;
    private ActivityManager mActivityManager;
    private Context mContext;
    private List<ActivityManager.RecentTaskInfo> mRecentTaskInfo;
    private RootContext mRootContext;
    private List<ActivityManager.RunningTaskInfo> mRunningTaskInfo;
    private SettingsValues mSettings;

    private Launcher(Context context) {
        this.mContext = context;
        this.mSettings = SettingsValues.getInstance(context);
        mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        mRootContext = RootContext.getInstance(context);
    }

    static Launcher getInstance(Context context) {
        if (instance == null) {
            instance = new Launcher(context);
        }
        return instance;
    }

    static void setDebug(int value) {
        boolean z = true;
        if (value != 1) {
            z = false;
        }
        DEBUG = z;
    }

    void fireAction(Action action) {
        Log.d(TAG, "Fire action " + action.toString());
        switch (action.getType()) {
            case 2:
                doAppAction(action.getString());
                return;
            case 3:
                doHomeAction();
                return;
            case 4:
                doHomeLongpressAction();
                return;
            case 5:
                doMenuAction();
                return;
            case 6:
                doMenuLongpressAction();
                return;
            case 7:
                doBackAction();
                return;
            case 8:
                doBackLongpressAction();
                return;
            case 9:
                doRecentAppsAction();
                return;
            case 10:
                doSearchAction();
                return;
            case 11:
                doSearchLongpressAction();
                return;
            case 12:
                doNextApp();
                return;
            case 13:
                doPrevApp();
                return;
            case 14:
                doLastApp();
                return;
            case 15:
                doOpenNotificationBar();
                return;
            case 16:
                doOpenQuickSettings();
                return;
            case 17:
                doOpenKeyboard();
                return;
            case 18:
                doPowerMenuAction();
                return;
            case 19:
            case 25:
            case 39:
            default:
                return;
            case 20:
                doWifiToggleAction();
                return;
            case 21:
                doDataToggleAction();
                return;
            case 22:
                doBluetoothToggleAction();
                return;
            case 23:
                doGPSToggleAction();
                return;
            case 24:
                doImmersiveModeToggleAction();
                return;
            case 26:
                doKeyAction(action.getString());
                return;
            case 27:
                doActivityAction(action.getString());
                return;
            case 28:
                doWebPageAction(action.getString());
                return;
            case 29:
                doScriptAction(action.getString());
                return;
            case 30:
                doKillAppAction();
                return;
            case 31:
                doKillAllAppsAction();
                return;
            case 32:
                doTaskerAction(action.getString());
                return;
            case 33:
                doShortcutAction(action.getString());
                return;
            case 34:
                doScreenshotAction();
                return;
            case 35:
                doUnpinAppAction();
                return;
            case 36:
                doNowOnTapAction();
                return;
            case 37:
                doAssistantAction();
                return;
            case 38:
                doSplitScreenAction();
                return;
            case 40:
                doOpenNova();
                return;
            case 41:
                doOpenApex();
                return;
            case 42:
                doOpenHolo();
                return;
        }
    }

    private void doHomeAction() {
        if (DEBUG) {
            Log.d(TAG, "doHomeAction()");
        }
        if (AccessibilityHandler.isAccessibilityAvailable(this.mContext, false)) {
            AccessibilityHandler.performAction(2);
        } else if (this.mRootContext.isRootAvailable(false)) {
            doKeyAction(3);
        } else {
            Intent startMain = new Intent("android.intent.action.MAIN");
            startMain.addCategory("android.intent.category.HOME");
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.mContext.startActivity(startMain);
        }
    }

    private void doHomeLongpressAction() {
        if (DEBUG) {
            Log.d(TAG, "doHomeLongpressAction()");
        }
        doKeyAction(2003);
    }

    private void doRecentAppsAction() {
        if (DEBUG) {
            Log.d(TAG, "doRecentAppsAction()");
        }
        if (AccessibilityHandler.isAccessibilityAvailable(this.mContext, false)) {
            AccessibilityHandler.performAction(3);
        } else if (this.mRootContext.isRootAvailable(false)) {
            doKeyAction(187);
        } else {
            AccessibilityHandler.isAccessibilityAvailable(this.mContext, false);
        }
    }

    private void doMenuAction() {
        if (DEBUG) {
            Log.d(TAG, "doMenuAction()");
        }
        doKeyAction(82);
    }

    private void doMenuLongpressAction() {
        if (DEBUG) {
            Log.d(TAG, "doMenuLongpressAction()");
        }
        doKeyAction(2082);
    }

    private void doBackAction() {
        if (DEBUG) {
            Log.d(TAG, "doBackAction()");
        }
        if (AccessibilityHandler.isAccessibilityAvailable(this.mContext, false)) {
            AccessibilityHandler.performAction(1);
        } else if (this.mRootContext.isRootAvailable(false)) {
            doKeyAction(4);
        } else {
            AccessibilityHandler.isAccessibilityAvailable(this.mContext, true);
        }
    }

    private void doBackLongpressAction() {
        if (DEBUG) {
            Log.d(TAG, "doBackLongpressAction()");
        }
        doKeyAction(2004);
    }

    private void doSearchAction() {
        if (DEBUG) {
            Log.d(TAG, "doSearchAction()");
        }
        if (this.mRootContext.isRootAvailable(false)) {
            doKeyAction(84);
            return;
        }
        Intent ni = new Intent("android.intent.action.SEARCH");
        ni.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            this.mContext.startActivity(ni);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void doSearchLongpressAction() {
        if (DEBUG) {
            Log.d(TAG, "doSearchLongpressAction()");
        }
        Intent ni = new Intent("android.intent.action.SEARCH_LONG_PRESS");
        ni.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            this.mContext.startActivity(ni);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void doNextApp() {
        if (DEBUG) {
            Log.d(TAG, "doNextApp()");
        }
        if (Build.VERSION.SDK_INT >= 21) {
            this.mRootContext.runCommandRemote("am next-app 0", true);
            return;
        }
        this.mRecentTaskInfo = this.mActivityManager.getRecentTasks(20, 0);
//        int nextID = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        int nextID = Integer.MAX_VALUE;
        int nextPos = 0;
//        int firstID = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        int firstID = Integer.MAX_VALUE;
        int firstPos = 0;
        for (int i = 1; i < this.mRecentTaskInfo.size(); i++) {
            if (this.mRecentTaskInfo.get(i).id < nextID && this.mRecentTaskInfo.get(i).id > this.mRecentTaskInfo.get(0).id && !this.mRecentTaskInfo.get(i).baseIntent.hasCategory("android.intent.category.HOME")) {
                nextID = this.mRecentTaskInfo.get(i).id;
                nextPos = i;
            }
            if (this.mRecentTaskInfo.get(i).id < firstID && !this.mRecentTaskInfo.get(i).baseIntent.hasCategory("android.intent.category.HOME") && this.mRecentTaskInfo.get(i).id > -1) {
                firstID = this.mRecentTaskInfo.get(i).id;
                firstPos = i;
            }
        }
        ComponentName component = this.mRecentTaskInfo.get(nextPos > 0 ? nextPos : firstPos).baseIntent.getComponent();
        component.getClass();
        moveTaskToFront(component.flattenToString(), nextPos > 0 ? nextID : firstID);
    }

    private void doPrevApp() {
        if (DEBUG) {
            Log.d(TAG, "doPrevApp()");
        }
        if (Build.VERSION.SDK_INT >= 21) {
            this.mRootContext.runCommandRemote("am prev-app 0", true);
            return;
        }
        this.mRecentTaskInfo = this.mActivityManager.getRecentTasks(20, 0);
        int prevID = 0;
        int prevPos = 0;
        int lastID = 0;
        int lastPos = 0;
        for (int i = 1; i < this.mRecentTaskInfo.size(); i++) {
            if (this.mRecentTaskInfo.get(i).id > prevID && this.mRecentTaskInfo.get(i).id < this.mRecentTaskInfo.get(0).id && !this.mRecentTaskInfo.get(i).baseIntent.hasCategory("android.intent.category.HOME")) {
                prevID = this.mRecentTaskInfo.get(i).id;
                prevPos = i;
            }
            if (this.mRecentTaskInfo.get(i).id > lastID && !this.mRecentTaskInfo.get(i).baseIntent.hasCategory("android.intent.category.HOME") && this.mRecentTaskInfo.get(i).id > -1) {
                lastID = this.mRecentTaskInfo.get(i).id;
                lastPos = i;
            }
        }
        ComponentName component = this.mRecentTaskInfo.get(prevPos > 0 ? prevPos : lastPos).baseIntent.getComponent();
        component.getClass();
        moveTaskToFront(component.flattenToString(), prevPos > 0 ? prevID : lastID);
    }

    private void doLastApp() {
        if (DEBUG) {
            Log.d(TAG, "doLastApp()");
        }
        if (Build.VERSION.SDK_INT < 21) {
            this.mRecentTaskInfo = this.mActivityManager.getRecentTasks(3, 0);
            int taskToChoose = 0;
            if (this.mRecentTaskInfo.size() > 1 && !this.mRecentTaskInfo.get(1).baseIntent.hasCategory("android.intent.category.HOME")) {
                taskToChoose = 1;
            } else if (this.mRecentTaskInfo.size() > 2 && !this.mRecentTaskInfo.get(2).baseIntent.hasCategory("android.intent.category.HOME")) {
                taskToChoose = 2;
            }
            ComponentName component = this.mRecentTaskInfo.get(taskToChoose).baseIntent.getComponent();
            component.getClass();
            moveTaskToFront(component.flattenToString(), this.mRecentTaskInfo.get(taskToChoose).id);
        } else if (this.mRootContext.isRootAvailable(false)) {
            this.mRootContext.runCommandRemote("am last-app 0", true);
        } else {
            String packageNames = this.mSettings.getPackageNamesOfRecentApps(3);
            if (packageNames.length() > 1) {
                String[] packageNamesArray = packageNames.split(" ");
                if (!doAppAction(packageNamesArray[1]) && packageNames.length() > 2) {
                    doAppAction(packageNamesArray[2]);
                }
            }
        }
    }

    private void doPowerMenuAction() {
        if (DEBUG) {
            Log.d(TAG, "doPowerMenuAction()");
        }
        if (AccessibilityHandler.isAccessibilityAvailable(this.mContext, false)) {
            AccessibilityHandler.performAction(6);
        } else if (this.mRootContext.isRootAvailable(false)) {
            doKeyAction(1026);
        } else {
            AccessibilityHandler.isAccessibilityAvailable(this.mContext, true);
        }
    }

    private void doKeyAction(String key) {
        if (DEBUG) {
            Log.d(TAG, "doKeyAction(" + key + ")");
        }
        RootContext rootContext = this.mRootContext;
        rootContext.runCommandRemote("input keyevent " + key, true);
    }

    private void doKeyAction(int key) {
        if (DEBUG) {
            Log.d(TAG, "doKeyAction()");
        }
        RootContext rootContext = this.mRootContext;
        rootContext.runCommandRemote("input keyevent " + key, true);
    }

    private boolean doAppAction(String name) {
        Intent ni;
        if (DEBUG) {
            Log.d(TAG, "doAppAction(" + name + ")");
        }
        if (name == null || name.length() <= 0 || (ni = this.mContext.getPackageManager().getLaunchIntentForPackage(name)) == null) {
            return false;
        }
        doActivityAction(ni.getComponent().flattenToString());
        return true;
    }

    private void moveTaskToFront(String name, int id) {
        if (DEBUG) {
            Log.d(TAG, "moveTaskToFront(" + name + ", " + id + ")");
        }
        if (Build.VERSION.SDK_INT >= 16) {
            RootContext rootContext = this.mRootContext;
            rootContext.runCommandRemote("am move-to-front " + id, true);
            return;
        }
        this.mActivityManager.moveTaskToFront(id, 0);
    }

    private void startActivity(String name) {
        if (DEBUG) {
            Log.d(TAG, "startActivity(" + name + ")");
        }
        if (Build.VERSION.SDK_INT >= 18) {
            RootContext rootContext = this.mRootContext;
            rootContext.runCommandRemote("am start -a android.intent.action.MAIN -n " + name, true);
            return;
        }
        RootContext rootContext2 = this.mRootContext;
        rootContext2.runCommandRoot("am start -a android.intent.action.MAIN -n " + name, true);
    }

    private void doActivityAction(String name) {
        if (DEBUG) {
            Log.d(TAG, "doActivityAction(" + name + ")");
        }
        if (name != null && name.length() > 0) {
            if (!this.mRootContext.isRootAvailable(false)) {
                Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
                intent.setComponent(ComponentName.unflattenFromString(name));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.mContext.startActivity(intent);
            } else if (Build.VERSION.SDK_INT >= 21) {
                RootContext rootContext = this.mRootContext;
                rootContext.runCommandRemote("am move-to-front-or-start " + name, true);
            } else if (Build.VERSION.SDK_INT >= 16) {
                this.mRunningTaskInfo = this.mActivityManager.getRunningTasks(20);
                for (int i = 1; i < this.mRunningTaskInfo.size(); i++) {
                    if (this.mRunningTaskInfo.get(i).baseActivity.flattenToString().equals(name)) {
                        moveTaskToFront(name, this.mRunningTaskInfo.get(i).id);
                        return;
                    }
                }
                startActivity(name);
            } else {
                startActivity(name);
            }
        }
    }

    private void doWebPageAction(String page) {
        if (DEBUG) {
            Log.d(TAG, "doWebPageAction(" + page + ")");
        }
        if (!page.contains("http")) {
            if (!page.contains("www")) {
                page = "http://www." + page;
            } else {
                page = "http://" + page;
            }
        }
        Intent ni = new Intent("android.intent.action.VIEW");
        ni.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ni.setData(Uri.parse(page));
        try {
            this.mContext.startActivity(ni);
        } catch (ActivityNotFoundException e) {
        }
    }

    private void doScriptAction(String script) {
        if (DEBUG) {
            Log.d(TAG, "doScriptAction(" + script + ")");
        }
        if (script != null && script.length() > 0) {
            if (script.contains(".sh")) {
                RootContext rootContext = this.mRootContext;
                rootContext.runCommandRoot("sh " + script, true);
                return;
            }
            this.mRootContext.runCommandRoot(script, true);
        }
    }

    private void doWifiToggleAction() {
        if (DEBUG) {
            Log.d(TAG, "doWifiToggleAction()");
        }
        if (Build.VERSION.SDK_INT < 29) {
            WifiManager wfm = (WifiManager) this.mContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            wfm.setWifiEnabled(!wfm.isWifiEnabled());
        } else if (((WifiManager) this.mContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE)).isWifiEnabled()) {
            this.mRootContext.runCommandRoot("svc wifi disable", true);
        } else {
            this.mRootContext.runCommandRoot("svc wifi enable", true);
        }
    }

    private void doDataToggleAction() {
        if (DEBUG) {
            Log.d(TAG, "doDataToggleAction()");
        }
        boolean z = true;
        if (Build.VERSION.SDK_INT < 20) {
            try {
                ConnectivityManager connectivityManager = (ConnectivityManager) this.mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
                Field iConnectivityManagerField = Class.forName(connectivityManager.getClass().getName()).getDeclaredField("mService");
                iConnectivityManagerField.setAccessible(true);
                Object iConnectivityManager = iConnectivityManagerField.get(connectivityManager);
                Method setMobileDataEnabledMethod = Class.forName(iConnectivityManager.getClass().getName()).getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
                setMobileDataEnabledMethod.setAccessible(true);
                Object[] objArr = new Object[1];
                if (((TelephonyManager) this.mContext.getSystemService(Context.TELEPHONY_SERVICE)).getDataState() == 2) {
                    z = false;
                }
                objArr[0] = Boolean.valueOf(z);
                setMobileDataEnabledMethod.invoke(iConnectivityManager, objArr);
            } catch (Exception e) {
            }
        } else if (((TelephonyManager) this.mContext.getSystemService(Context.TELEPHONY_SERVICE)).getDataState() == 2) {
            this.mRootContext.runCommandRoot("svc data disable", true);
        } else {
            this.mRootContext.runCommandRoot("svc data enable", true);
        }
    }

    private void doBluetoothToggleAction() {
        if (DEBUG) {
            Log.d(TAG, "doBluetoothToggleAction()");
        }
        BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
        if (!ba.isEnabled()) {
            ba.enable();
        } else {
            ba.disable();
        }
    }

    private void doGPSToggleAction() {
        if (DEBUG) {
            Log.d(TAG, "doGPSToggleAction()");
        }
        Intent ni = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
        ni.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.mContext.startActivity(ni);
    }

    private void doImmersiveModeToggleAction() {
        if (DEBUG) {
            Log.d(TAG, "doImmersiveModeToggleAction()");
        }
        boolean useRoot = this.mRootContext.isRootAvailable(false);
        String result = this.mRootContext.runCommandResult("settings get global policy_control", useRoot);
        if (result.contains("null")) {
            this.mRootContext.runCommandResult("settings put global policy_control immersive.navigation=*,-com.android.systemui,-com.whatsapp", useRoot);
        } else if (result.contains("navigation")) {
            this.mRootContext.runCommandResult("settings put global policy_control immersive.full=*,-com.android.systemui,-com.whatsapp", useRoot);
        } else {
            this.mRootContext.runCommandResult("settings put global policy_control null*", useRoot);
        }
    }

    private void doKillAppAction() {
        if (DEBUG) {
            Log.d(TAG, "doKillAppAction()");
        }
        if (Build.VERSION.SDK_INT >= 21) {
            this.mRootContext.runCommandRemote("am kill-app 0", false);
            return;
        }
        this.mRunningTaskInfo = this.mActivityManager.getRunningTasks(1);
        TimerTask killAppTask = new TimerTask() {
            /* class com.noname81.lmt.Launcher.C05211 */

            public void run() {
                String packageName = ((ActivityManager.RunningTaskInfo) Launcher.this.mRunningTaskInfo.get(0)).baseActivity.getPackageName();
                if (!packageName.contains(BuildConfig.APPLICATION_ID) && !packageName.contains("com.android.systemui")) {
                    RootContext rootContext = Launcher.this.mRootContext;
                    rootContext.runCommandRoot("am force-stop " + packageName, false);
                    if (Launcher.DEBUG) {
                        Log.d(Launcher.TAG, "Kill app " + packageName);
                    }
                }
            }
        };
        doKeyAction(3);
        new Timer().schedule(killAppTask, 1000);
    }

    private void doKillAllAppsAction() {
        if (DEBUG) {
            Log.d(TAG, "doKillAllAppsAction()");
        }
        if (Build.VERSION.SDK_INT >= 21) {
            this.mRootContext.runCommandRemote("am kill-all-apps 0", false);
            return;
        }
        this.mRunningTaskInfo = this.mActivityManager.getRunningTasks(100);
        TimerTask killAllAppsTask = new TimerTask() {
            /* class com.noname81.lmt.Launcher.C05222 */

            public void run() {
                for (int i = 0; i < Launcher.this.mRunningTaskInfo.size(); i++) {
                    String packageName = ((ActivityManager.RunningTaskInfo) Launcher.this.mRunningTaskInfo.get(i)).baseActivity.getPackageName();
                    if (!packageName.contains(BuildConfig.APPLICATION_ID) && !packageName.contains("com.android.systemui")) {
                        Launcher.this.mActivityManager.killBackgroundProcesses(packageName);
                        if (Launcher.DEBUG) {
                            Log.d(Launcher.TAG, "Kill app " + packageName);
                        }
                    }
                }
            }
        };
        doKeyAction(3);
        new Timer().schedule(killAllAppsTask, 1000);
    }

    private void doTaskerAction(String name) {
        if (DEBUG) {
            Log.d(TAG, "doTaskerAction()");
        }
        if (name != null && name.length() > 0) {
            RootContext rootContext = this.mRootContext;
            rootContext.runCommandRoot("am broadcast -a net.dinglisch.android.tasker.ACTION_TASK -e task_name '" + name + "'", true);
        }
    }

    void doOpenNotificationBar() {
        if (DEBUG) {
            Log.d(TAG, "doOpenNotificationBar()");
        }
        if (AccessibilityHandler.isAccessibilityAvailable(this.mContext, false)) {
            AccessibilityHandler.performAction(4);
        } else if (Build.VERSION.SDK_INT >= 17) {
            try {
                Class.forName("android.app.StatusBarManager").getMethod("expandNotificationsPanel", new Class[0]).invoke(this.mContext.getApplicationContext().getSystemService("statusbar"), new Object[0]);
            } catch (Exception e) {
                AccessibilityHandler.isAccessibilityAvailable(this.mContext, true);
            }
        } else {
            try {
                Class.forName("android.app.StatusBarManager").getMethod("expand", new Class[0]).invoke(this.mContext.getApplicationContext().getSystemService("statusbar"), new Object[0]);
            } catch (Exception e2) {
                AccessibilityHandler.isAccessibilityAvailable(this.mContext, true);
            }
        }
    }

    void doOpenQuickSettings() {
        if (DEBUG) {
            Log.d(TAG, "doOpenQuickSettings()");
        }
        if (AccessibilityHandler.isAccessibilityAvailable(this.mContext, false)) {
            AccessibilityHandler.performAction(5);
        } else if (Build.VERSION.SDK_INT >= 17) {
            try {
                Class.forName("android.app.StatusBarManager").getMethod("expandSettingsPanel", new Class[0]).invoke(this.mContext.getApplicationContext().getSystemService("statusbar"), new Object[0]);
            } catch (Exception e) {
                AccessibilityHandler.isAccessibilityAvailable(this.mContext, true);
            }
        } else {
            AccessibilityHandler.isAccessibilityAvailable(this.mContext, true);
        }
    }

    private void doOpenKeyboard() {
        if (DEBUG) {
            Log.d(TAG, "doOpenKeyboard()");
        }
        ((InputMethodManager) this.mContext.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(2, 1);
    }

    private void doShortcutAction(String name) {
        if (DEBUG) {
            Log.d(TAG, "doShortcutAction()");
        }
        try {
            Intent shortcutIntent = Intent.parseUri(name, 0);
            shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.mContext.startActivity(shortcutIntent);
        } catch (Exception e) {
        }
    }

    private void doScreenshotAction() {
        if (DEBUG) {
            Log.d(TAG, "doScreenshotAction()");
        }
        doKeyAction("1000 120");
    }

    private void doUnpinAppAction() {
        if (DEBUG) {
            Log.d(TAG, "doUnpinAppAction()");
        }
        this.mRootContext.runCommandRoot("am task lock stop", true);
    }

    private void doNowOnTapAction() {
        if (DEBUG) {
            Log.d(TAG, "doNowOnTapAction()");
        }
        doKeyAction("219");
    }

    private void doAssistantAction() {
        if (DEBUG) {
            Log.d(TAG, "doAssistantAction()");
        }
        doKeyAction("219");
    }

    private void doSplitScreenAction() {
        if (DEBUG) {
            Log.d(TAG, "doSplitScreenAction()");
        }
        if (AccessibilityHandler.isAccessibilityAvailable(this.mContext, false)) {
            AccessibilityHandler.performAction(7);
        } else {
            AccessibilityHandler.isAccessibilityAvailable(this.mContext, true);
        }
    }

    private void doOpenNova() {
        if (DEBUG) {
            Log.d(TAG, "doOpenNova()");
        }
        this.mRootContext.runCommandRoot("am start -a com.teslacoilsw.launcher.ACTION -n com.teslacoilsw.launcher/.NovaShortcutHandler", true);
    }

    private void doOpenApex() {
        if (DEBUG) {
            Log.d(TAG, "doOpenApex()");
        }
        this.mRootContext.runCommandRoot("am start -a com.anddoes.launcher.ACTION -n com.anddoes.launcher/.Launcher -e LAUNCHER_ACTION 'APP_DRAWER'", true);
    }

    private void doOpenHolo() {
        if (DEBUG) {
            Log.d(TAG, "doOpenHolo()");
        }
        this.mRootContext.runCommandRoot("am start -a com.mobint.hololauncher.ACTION -n com.mobint.hololauncher/.Launcher -e ACTION 'DRAWER'", true);
    }
}