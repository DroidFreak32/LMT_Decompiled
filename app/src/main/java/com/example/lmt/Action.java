package com.example.lmt;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import java.io.File;

/* access modifiers changed from: package-private */
public class Action {
    static final int Activity = 27;
    static final int Apex = 41;
    static final int App = 2;
    static final int Assistant = 37;
    static final int Back = 7;
    static final int BackLongpress = 8;
    static final int BluetoothToggle = 22;
    static final int DataToggle = 21;
    static final int GPSToggle = 23;
    static final int Holo = 42;
    static final int Home = 3;
    static final int HomeLongpress = 4;
    static final int ImmersiveModeToggle = 24;
    static final int Key = 26;
    static final int KillAllApps = 31;
    static final int KillApp = 30;
    static final int LastApp = 14;
    static final int Menu = 5;
    static final int MenuLongpress = 6;
    static final int NextApp = 12;
    static final int None = 1;
    static final int Nova = 40;
    static final int NowOnTap = 36;
    static final int OffsetAdvancedCommands = 25;
    static final int OffsetAppDrawer = 39;
    static final int OffsetNormalCommands = 0;
    static final int OffsetPieActions = 43;
    static final int OffsetToggleCommands = 19;
    static final int OpenKeyboard = 17;
    static final int OpenNotificationBar = 15;
    static final int OpenPowerMenu = 18;
    static final int OpenQuickSettings = 16;
    static final int PiePointer = 44;
    static final int PieRecentApps = 45;
    static final int PrevApp = 13;
    static final int RecentApps = 9;
    static final int Screenshot = 34;
    static final int Script = 29;
    static final int Search = 10;
    static final int SearchLongpress = 11;
    static final int Shortcut = 33;
    static final int SplitScreen = 38;
    private static final String TAG = "LMT::Action";
    static final int TaskerTask = 32;
    static final int UnpinApp = 35;
    static final int WebPage = 28;
    static final int WifiToggle = 20;
    static final String[] captions = {"OffsetNormalCommands", "Trigger no command", "Start an app [Root|None]", "Trigger home key [Accessibility|Root|None]", "Trigger longpress home key [Root]", "Trigger menu key [Root]", "Trigger longpress menu key [Root]", "Trigger back key [Accessibility|Root]", "Trigger longpress back key [Root]", "Trigger recent apps [Accessibility|Root]", "Activate search [Root|None]", "Activate voice search [None]", "Switch to next active app [Root]", "Switch to previous active app [Root]", "Switch to last active app [Root]", "Open the notification bar [Accessibility]", "Open the quick settings [Accessibility]", "Open the keyboard [None]", "Open the power menu [Accessibility|Root]", "OffsetToggleCommands", "Toggle Wifi mode [None]", "Toggle mobile data mode [Root]", "Toggle Bluetooth mode [None]", "Toggle GPS mode [None]", "Toggle Immersive mode [Root]", "OffsetAdvancedCommands", "Trigger an arbitrary key [Root]", "Start an activity [Root|None]", "Open an arbitrary webpage [None]", "Trigger an arbitrary script [Root]", "Kill the current app and switch to homescreen [Root]", "Kill all apps and switch to homescreen [Root]", "Trigger a tasker task [Root]", "Trigger a shortcut [None]", "Trigger a screenshot [Root]", "Unpin pinned app [Root]", "Activate Google Now On Tap [Root]", "Activate Google Assistant [Root]", "Toggle Split Screen [Accessibility]", "OffsetAppDrawer", "Open AppDrawer from NovaLauncher [Root]", "Open AppDrawer from ApexLauncher [Root]", "Open AppDrawer from HoloLauncher [Root]", "OffsetPieActions", "Activate pie pointer extension [Root]", "Activate Pie Recent Apps [Root|UsageStats]"};
    static final String[] names = {"OffsetNormalCommands", "None", "App", "Home", "HomeLongpress", "Menu", "MenuLongpress", "Back", "BackLongpress", "RecentApps", "Search", "SearchLongpress", "NextApp", "PrevApp", "LastApp", "OpenNotificationBar", "OpenQuickSettings", "OpenKeyboard", "OpenPowerMenu", "OffsetToggleCommands", "Wifi", "Data", "Bluetooth", "GPS", "ImmersiveMode", "OffsetAdvancedCommands", "Key", "Activity", "WebPage", "Script", "KillApp", "KillAllApps", "TaskerTask", "Shortcut", "Screenshot", "UnpinApp", "NowOnTap", "Assistant", "SplitScreen", "OffsetAppDrawer", "NovaLauncher", "ApexLauncher", "HoloLauncher", "OffsetPieActions", "PiePointer", "PieRecentApps"};
    private Drawable mIcon;
    private String mString;
    private int mType;

    Action(int type) {
        this.mType = type;
    }

    Action(int type, String string) {
        this.mType = type;
        this.mString = string;
        this.mIcon = null;
    }

    Action(int type, String string, Drawable icon) {
        this.mType = type;
        this.mString = string;
        this.mIcon = icon;
    }

    Action(int type, String string, String icon, Context context) {
        this.mType = type;
        this.mString = string;
        this.mIcon = IconUtils.convertBase64StringToDrawable(context, icon);
    }

    /* access modifiers changed from: package-private */
    public int getType() {
        return this.mType;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public String getString() {
        return this.mString;
    }

    public void setString(String string) {
        this.mString = string;
    }

    public Drawable getIcon() {
        return this.mIcon;
    }

    public void setIcon(Drawable icon) {
        this.mIcon = icon;
    }

    public String toString() {
        String type;
        int i = this.mType;
        String string = "none";
        if (i > 0) {
            String[] strArr = names;
            if (i <= strArr.length) {
                type = strArr[i];
                String str = this.mString;
                if (str != null && str.length() > 0) {
                    string = this.mString;
                }
                return "type: " + type + ", string: " + string;
            }
        }
        type = string;
        String str2 = this.mString;
        string = this.mString;
        return "type: " + type + ", string: " + string;
    }

    /* access modifiers changed from: package-private */
    public Drawable getDrawable(Context context, String namePie, int userImageScaling, int appImageScaling, boolean noneActionImage) {
        if (this.mType == 1 && !noneActionImage) {
            return null;
        }
        try {
            String resourcePath = SettingsValues.getInstance(context).loadResourceSearchPath();
            String nameAction = names[this.mType].toLowerCase() + ".png";
            if (namePie != null) {
                File filePie = new File(resourcePath + namePie);
                if (filePie.exists()) {
                    filePie.setReadable(true, false);
                    Drawable dPie = Drawable.createFromPath(resourcePath + namePie);
                    if (dPie != null) {
                        return IconUtils.resizeImage(context, dPie, userImageScaling);
                    }
                    Log.e(TAG, "Failed to load available image " + resourcePath + namePie);
                }
            }
            if (new File(resourcePath + nameAction).exists()) {
                Drawable dAction = Drawable.createFromPath(resourcePath + nameAction);
                if (dAction != null) {
                    return IconUtils.resizeImage(context, dAction, userImageScaling);
                }
                Log.e(TAG, "Failed to load available image " + resourcePath + nameAction);
            }
        } catch (Exception e) {
        }
        Drawable drawable = this.mIcon;
        if (drawable != null) {
            return IconUtils.resizeImage(context, drawable, appImageScaling);
        }
        if (this.mType == 2 && appImageScaling > 0) {
            try {
                return IconUtils.resizeImage(context, context.getPackageManager().getApplicationIcon(this.mString), appImageScaling);
            } catch (Exception e2) {
            }
        }
        if (this.mType == 27 && appImageScaling > 0) {
            try {
                return IconUtils.resizeImage(context, context.getPackageManager().getApplicationIcon(this.mString.substring(0, this.mString.lastIndexOf(47))), appImageScaling);
            } catch (Exception e3) {
            }
        }
        try {
            Resources resources = context.getResources();
            int resID = 0;
            int iconStyle = SettingsValues.getInstance(context).loadPieNavButtonStyle();
            if (iconStyle == 1) {
                resID = resources.getIdentifier(names[this.mType].toLowerCase() + "_kitkat", "drawable", context.getPackageName());
            } else if (iconStyle == 2) {
                resID = resources.getIdentifier(names[this.mType].toLowerCase() + "_pixel", "drawable", context.getPackageName());
            }
            if (resID == 0) {
                resID = resources.getIdentifier(names[this.mType].toLowerCase(), "drawable", context.getPackageName());
            }
            return IconUtils.getDrawable(context, resID);
        } catch (Exception e4) {
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public void checkNeededPermissions(Activity activity, boolean request) {
        switch (getType()) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            default:
                return;
            case 33:
                if (request && this.mString.contains("android.intent.action.CALL")) {
                    PermissionChecker.getInstance().checkAndRequestPhoneCallPermission(activity, true);
                    return;
                }
                return;
            case 45:
                if (request && Build.VERSION.SDK_INT >= 21 && !RootContext.getInstance(activity).isRootAvailable(false)) {
                    PermissionChecker.getInstance().checkAndRequestUsageStatsPermission(activity, true);
                    return;
                }
                return;
        }
    }
}