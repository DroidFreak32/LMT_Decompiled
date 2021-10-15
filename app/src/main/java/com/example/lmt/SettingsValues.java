package com.example.lmt;

import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/* access modifiers changed from: package-private */
public class SettingsValues extends SettingsValuesBase {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static SettingsValues instance = null;
    private int mOrientation;
    private int mScreenHeight;
    private int mScreenWidth;
    private boolean mVisiblePieActivationAreas = false;

    private SettingsValues(Context context) {
        super(context);
    }

    static SettingsValues getInstance(Context context) {
        if (instance == null) {
            instance = new SettingsValues(context);
        }
        return instance;
    }

    /* access modifiers changed from: package-private */
    public String getVersion() {
        return "v3.1";
    }

    /* access modifiers changed from: package-private */
    public long getDays() {
        long delta = 1641771032000L - System.currentTimeMillis();
        if (delta > 0) {
            return (((delta / 1000) / 60) / 60) / 24;
        }
        return 0;
    }

    /* access modifiers changed from: package-private */
    public int getOrientation() {
        return this.mOrientation;
    }

    /* access modifiers changed from: package-private */
    public int getScreenHeight() {
        return this.mScreenHeight;
    }

    /* access modifiers changed from: package-private */
    public int getScreenWidth() {
        return this.mScreenWidth;
    }

    /* access modifiers changed from: package-private */
    public void rotate() {
        WindowManager windowManager = (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
        this.mOrientation = windowManager.getDefaultDisplay().getRotation();
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getRealMetrics(metrics);
        this.mScreenWidth = metrics.widthPixels;
        this.mScreenHeight = metrics.heightPixels;
    }

    /* access modifiers changed from: package-private */
    public boolean getVisiblePieActivationAreas() {
        return this.mVisiblePieActivationAreas;
    }

    /* access modifiers changed from: package-private */
    public void setVisiblePieActivationAread(boolean visible) {
        this.mVisiblePieActivationAreas = visible;
    }

    /* access modifiers changed from: package-private */
    public Action getIsaAction(int gesture, float startX, float startY) {
        float startX2 = startX / this.mTouchscreenScreenFactorX;
        float startY2 = startY / this.mTouchscreenScreenFactorY;
        int i = this.mOrientation;
        if (i == 1 || i == 3) {
            startY2 = ((float) this.mScreenHeight) - startY2;
        }
        if (startY2 > ((float) (this.mScreenHeight - this.mSingleSwipesAArea)) && gesture == 16) {
            return getIsaAction((int) ((3.0f * startX2) / ((float) this.mScreenWidth)));
        }
        if (startY2 < ((float) this.mSingleSwipesAArea) && gesture == 17) {
            return getIsaAction(((int) ((3.0f * startX2) / ((float) this.mScreenWidth))) + 3);
        }
        if (startX2 < ((float) this.mSingleSwipesAArea) && gesture == 14) {
            return getIsaAction(((int) ((3.0f * startY2) / ((float) this.mScreenHeight))) + 6);
        }
        if (startX2 <= ((float) (this.mScreenWidth - this.mSingleSwipesAArea)) || gesture != 15) {
            return new Action(1);
        }
        return getIsaAction(((int) ((3.0f * startY2) / ((float) this.mScreenHeight))) + 9);
    }

    /* access modifiers changed from: package-private */
    public String getPackageNamesOfRecentApps(int numberOfRecentApps) {
        if (Build.VERSION.SDK_INT < 21) {
            List<ActivityManager.RunningTaskInfo> taskInfo = this.mActivityManager.getRunningTasks(numberOfRecentApps);
            if (taskInfo.size() <= 0) {
                return BuildConfig.FLAVOR;
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < taskInfo.size(); i++) {
                if (i != 0) {
                    stringBuilder.append(" ");
                }
                stringBuilder.append(taskInfo.get(i).topActivity.getPackageName());
            }
            return stringBuilder.toString();
        } else if (this.mRootContext.isRootAvailable(false)) {
            RootContext rootContext = this.mRootContext;
            return rootContext.runCommandRemoteResult("am get-recent-app " + numberOfRecentApps, true);
        } else {
            long time = System.currentTimeMillis();
            List<UsageStats> appList = ((UsageStatsManager) this.mContext.getSystemService(Context.USAGE_STATS_SERVICE)).queryUsageStats(0, time - TimeUnit.DAYS.toMillis(1), time);
            if (appList == null || appList.size() <= 0) {
                return BuildConfig.FLAVOR;
            }
            if (numberOfRecentApps > 1) {
                SortedMap<Long, UsageStats> sortedMap = new TreeMap<>(Collections.reverseOrder());
                for (UsageStats app : appList) {
                    sortedMap.put(Long.valueOf(app.getLastTimeUsed()), app);
                }
                int i2 = 0;
                StringBuilder stringBuilder2 = new StringBuilder();
                for (Map.Entry<Long, UsageStats> entry : sortedMap.entrySet()) {
                    if (i2 != 0) {
                        stringBuilder2.append(" ");
                    }
                    stringBuilder2.append(entry.getValue().getPackageName());
                    if (i2 >= numberOfRecentApps) {
                        break;
                    }
                    i2++;
                }
                return stringBuilder2.toString();
            }
            int pos = 0;
            long timeLastUsed = 0;
            for (int i3 = 0; i3 < appList.size(); i3++) {
                if (appList.get(i3).getLastTimeUsed() > timeLastUsed) {
                    timeLastUsed = appList.get(i3).getLastTimeUsed();
                    pos = i3;
                }
            }
            return appList.get(pos).getPackageName();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isNotBlacklisted() {
        return this.mBlacklist.size() <= 0 || !this.mBlacklist.contains(getPackageNamesOfRecentApps(1));
    }

    /* access modifiers changed from: package-private */
    public boolean isNotBlacklistedPie() {
        return this.mBlacklistPie.size() <= 0 || !this.mBlacklistPie.contains(getPackageNamesOfRecentApps(1));
    }
}