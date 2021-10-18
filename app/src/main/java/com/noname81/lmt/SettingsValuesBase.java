package com.noname81.lmt;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Environment;
import android.support.v7.widget.helper.ItemTouchHelper;
import java.util.ArrayList;
import java.util.Vector;

class SettingsValuesBase extends SettingsSharedPrefsWrapper {
    static final String path = "/Android/data/com.noname81.lmt/files/";
    ActivityManager mActivityManager = ((ActivityManager) this.mContext.getSystemService(Context.ACTIVITY_SERVICE));
    ArrayList<String> mBlacklist = new ArrayList<>();
    ArrayList<String> mBlacklistPie = new ArrayList<>();
    private int mCurrentGesture = -1;
    private int mCurrentIsa = -1;
    private int mCurrentPie = -1;
    private Vector<Action> mGestureActions = new Vector<>();
    private Vector<Action> mIsaActions = new Vector<>();
    private Vector<Action> mPieActions = new Vector<>();
    RootContext mRootContext = RootContext.getInstance(this.mContext);
    private int mServiceState = 0;
    int mSingleSwipesAArea = loadSingleSwipesAArea();
    float mTouchscreenScreenFactorX = loadTouchscreenScreenFactorX();
    float mTouchscreenScreenFactorY = loadTouchscreenScreenFactorY();

    SettingsValuesBase(Context context) {
        super(context);
        loadActions();
        loadBlacklist();
        loadBlacklistPie();
    }

    int getServiceState() {
        return this.mServiceState;
    }

    void setServiceState(int state) {
        this.mServiceState = state;
    }

    private boolean getIsSmallScreen() {
        return false;
    }

    void startService() {
        if (Build.VERSION.SDK_INT >= 26) {
            this.mContext.startForegroundService(new Intent(this.mContext, TouchService.class));
        } else {
            this.mContext.startService(new Intent(this.mContext, TouchService.class));
        }
    }

    void stopService() {
        this.mContext.stopService(new Intent(this.mContext, TouchService.class));
    }

    void restartServiceIfRequired() {
        if (this.mServiceState > 0) {
            stopService();
            startService();
        }
    }

    Action getGestureAction(int gesture) {
        if (gesture < this.mGestureActions.size()) {
            return this.mGestureActions.get(gesture);
        }
        return new Action(Action.None);
    }

    Action getPieAction(int pie) {
        if (pie < this.mPieActions.size()) {
            return this.mPieActions.get(pie);
        }
        return new Action(Action.None);
    }

    Action getIsaAction(int isa) {
        if (isa < this.mIsaActions.size()) {
            return this.mIsaActions.get(isa);
        }
        return new Action(Action.None);
    }

    Action getCurrentAction() {
        int i = this.mCurrentGesture;
        if (i >= 0) {
            return this.mGestureActions.get(i);
        }
        int i2 = this.mCurrentPie;
        if (i2 >= 0) {
            return this.mPieActions.get(i2);
        }
        int i3 = this.mCurrentIsa;
        if (i3 >= 0) {
            return this.mIsaActions.get(i3);
        }
        return new Action(-1);
    }

    void setCurrentGesture(int currentGesture) {
        this.mCurrentGesture = currentGesture;
        this.mCurrentPie = -1;
        this.mCurrentIsa = -1;
    }

    void setCurrentPie(int currentPie) {
        this.mCurrentGesture = -1;
        this.mCurrentPie = currentPie;
        this.mCurrentIsa = -1;
    }

    void setCurrentIsa(int currentIsa) {
        this.mCurrentGesture = -1;
        this.mCurrentPie = -1;
        this.mCurrentIsa = currentIsa;
    }

    void setCurrentAction(Activity activity, Action action) {
        SharedPreferences.Editor editor = createAndReturnSharedPreferencesEditor();
        int i = this.mCurrentGesture;
        if (i >= 0) {
            this.mGestureActions.setElementAt(action, i);
            saveGestureAction(this.mCurrentGesture, action, editor);
        } else {
            int i2 = this.mCurrentPie;
            if (i2 >= 0) {
                this.mPieActions.setElementAt(action, i2);
                savePieAction(this.mCurrentPie, action, editor);
                restartServiceIfRequired();
            } else {
                int i3 = this.mCurrentIsa;
                if (i3 >= 0) {
                    this.mIsaActions.setElementAt(action, i3);
                    saveIsaAction(this.mCurrentIsa, action, editor);
                    restartServiceIfRequired();
                }
            }
        }
        if (editor != null) {
            editor.commit();
        }
        action.checkNeededPermissions(activity, true);
    }

    private void loadActions() {
        for (int i = 0; i < TouchServiceResult.names.length; i++) {
            this.mGestureActions.add(loadGestureAction(i));
        }
        for (int i2 = 0; i2 < 24; i2++) {
            this.mPieActions.add(loadPieAction(i2));
        }
        for (int i3 = 0; i3 < 12; i3++) {
            this.mIsaActions.add(loadIsaAction(i3));
        }
    }

    private Action loadGestureAction(int gesture) {
        int type = loadInt(TouchServiceResult.names[gesture] + " Type", 1);
        String string = loadString(TouchServiceResult.names[gesture] + " String", BuildConfig.FLAVOR);
        return new Action(type, string, loadString(TouchServiceResult.names[gesture] + " Icon", BuildConfig.FLAVOR), this.mContext);
    }

    private Action loadPieAction(int pie) {
        int type = loadInt("PieItem" + pie + " Type", 1);
        String string = loadString("PieItem" + pie + " String", BuildConfig.VERSION_NAME);
        return new Action(type, string, loadString("PieItem" + pie + " Icon", BuildConfig.FLAVOR), this.mContext);
    }

    private Action loadIsaAction(int isa) {
        int type = loadInt("IsaItem" + isa + " Type", 1);
        String string = loadString("IsaItem" + isa + " String", BuildConfig.FLAVOR);
        return new Action(type, string, loadString("IsaItem" + isa + " Icon", BuildConfig.FLAVOR), this.mContext);
    }

    private void saveGestureAction(int gesture, Action action, SharedPreferences.Editor editor) {
        saveInt(TouchServiceResult.names[gesture] + " Type", action.getType(), editor);
        saveString(TouchServiceResult.names[gesture] + " String", action.getString(), editor);
        saveString(TouchServiceResult.names[gesture] + " Icon", IconUtils.convertDrawableToBase64String(action.getIcon()), editor);
    }

    private void savePieAction(int pie, Action action, SharedPreferences.Editor editor) {
        saveInt("PieItem" + pie + " Type", action.getType(), editor);
        saveString("PieItem" + pie + " String", action.getString(), editor);
        saveString("PieItem" + pie + " Icon", IconUtils.convertDrawableToBase64String(action.getIcon()), editor);
    }

    private void saveIsaAction(int isa, Action action, SharedPreferences.Editor editor) {
        saveInt("IsaItem" + isa + " Type", action.getType(), editor);
        saveString("IsaItem" + isa + " String", action.getString(), editor);
        saveString("IsaItem" + isa + " Icon", IconUtils.convertDrawableToBase64String(action.getIcon()), editor);
    }

    int loadFeedbackMode() {
        return loadInt("Feedback", 3);
    }

    void saveFeedbackMode(int feedback) {
        saveInt("Feedback", feedback, null);
    }

    int loadGestureVibrationTime() {
        return loadInt("Vibration", 30);
    }

    void saveGestureVibrationTime(int vibration) {
        saveInt("Vibration", vibration, null);
    }

    int loadPieVibrationTime() {
        return loadInt("PieVibration", 30);
    }

    void savePieVibrationTime(int vibration) {
        saveInt("PieVibration", vibration, null);
    }

    int loadInputDevice() {
        return loadInt("Input", 4);
    }

    String loadInputDeviceString() {
        int inputDevice = loadInt("Input", 4);
        return "/dev/input/event" + inputDevice;
    }

    void saveInputDevice(int inputDevice) {
        saveInt("Input", inputDevice, null);
    }

    String loadResourceSearchPath() {
        return loadString("ResourceSearchPath", Environment.getExternalStorageDirectory().getPath() + path);
    }

    void saveResourceSearchPath(String resourceSearchPatch) {
        if (resourceSearchPatch == null || resourceSearchPatch.length() == 0) {
            resourceSearchPatch = Environment.getExternalStorageDirectory().getPath() + path;
        }
        if (!resourceSearchPatch.endsWith("/")) {
            resourceSearchPatch = resourceSearchPatch + "/";
        }
        saveString("ResourceSearchPath", resourceSearchPatch, null);
    }

    int loadSingleTouchGestureSupport() {
        return loadInt("STSupport", 1);
    }

    void saveSingleTouchGestureSupport(int support) {
        saveInt("STSupport", support, null);
    }

    int loadSingleSwipesBBox() {
        return loadInt("SingleSwipesBBox", 1);
    }

    void saveSingleSwipesBBox(int bbox) {
        saveInt("SingleSwipesBBox", bbox, null);
    }

    int loadSingleSwipesAArea() {
        this.mSingleSwipesAArea = loadInt("SingleSwipesAArea", 60);
        return this.mSingleSwipesAArea;
    }

    void saveSingleSwipesAArea(int area) {
        saveInt("SingleSwipesAArea", area, null);
        this.mSingleSwipesAArea = area;
    }

    float loadTouchscreenScreenFactorX() {
        this.mTouchscreenScreenFactorX = ((float) loadInt("TouchscreenScreenFactorX", 100)) / 100.0f;
        return this.mTouchscreenScreenFactorX;
    }

    void saveTouchscreenScreenFactorX(int factor) {
        saveInt("TouchscreenScreenFactorX", factor, null);
        this.mTouchscreenScreenFactorX = ((float) factor) / 100.0f;
    }

    float loadTouchscreenScreenFactorY() {
        this.mTouchscreenScreenFactorY = ((float) loadInt("TouchscreenScreenFactorY", 100)) / 100.0f;
        return this.mTouchscreenScreenFactorY;
    }

    void saveTouchscreenScreenFactorY(int factor) {
        saveInt("TouchscreenScreenFactorY", factor, null);
        this.mTouchscreenScreenFactorY = ((float) factor) / 100.0f;
    }

    int loadMinScore() {
        return loadInt("MinScore", 70);
    }

    void saveMinScore(int score) {
        saveInt("MinScore", score, null);
    }

    int loadMinPathLength() {
        return loadInt("MinPathLength", 7);
    }

    void saveMinPathLength(int length) {
        saveInt("MinPathLength", length, null);
    }

    int loadAutostart() {
        return loadInt("Autostart", 1);
    }

    void saveAutostart(int autostart) {
        saveInt("Autostart", autostart, null);
    }

    private void loadBlacklist() {
        String[] blacklistArray = loadString("Blacklist", BuildConfig.FLAVOR).split(";");
        for (String aBlacklistArray : blacklistArray) {
            if (aBlacklistArray.length() > 0) {
                this.mBlacklist.add(aBlacklistArray);
            }
        }
    }

    private void loadBlacklistPie() {
        String[] blacklistArray = loadString("BlacklistPie", BuildConfig.FLAVOR).split(";");
        for (String aBlacklistArray : blacklistArray) {
            if (aBlacklistArray.length() > 0) {
                this.mBlacklistPie.add(aBlacklistArray);
            }
        }
    }

    private void saveBlacklist() {
        StringBuilder blacklistString = new StringBuilder();
        if (this.mBlacklist.size() > 0) {
            blacklistString.append(this.mBlacklist.get(0));
            for (int i = 1; i < this.mBlacklist.size(); i++) {
                blacklistString.append(";");
                blacklistString.append(this.mBlacklist.get(i));
            }
        }
        saveString("Blacklist", blacklistString.toString(), null);
    }

    private void saveBlacklistPie() {
        StringBuilder blacklistString = new StringBuilder();
        if (this.mBlacklistPie.size() > 0) {
            blacklistString.append(this.mBlacklistPie.get(0));
            for (int i = 1; i < this.mBlacklistPie.size(); i++) {
                blacklistString.append(";");
                blacklistString.append(this.mBlacklistPie.get(i));
            }
        }
        saveString("BlacklistPie", blacklistString.toString(), null);
    }

    void setBlacklisted(String name) {
        this.mBlacklist.add(name);
        saveBlacklist();
    }

    void setBlacklistedPie(String name) {
        this.mBlacklistPie.add(name);
        saveBlacklistPie();
    }

    void clearBlacklisted() {
        this.mBlacklist.clear();
        saveBlacklist();
    }

    void clearBlacklistedPie() {
        this.mBlacklistPie.clear();
        saveBlacklistPie();
    }

    int loadTouchServiceMode() {
        return loadInt("TSMode", 2);
    }

    void saveTouchServiceMode(int mode) {
        saveInt("TSMode", mode, null);
    }

    int loadPiePos() {
        return loadInt("PiePos", 7);
    }

    void savePiePos(int mode) {
        saveInt("PiePos", mode, null);
    }

    int loadPieAreaX() {
        return loadInt("PieAreaX", 50);
    }

    void savePieAreaX(int mode) {
        saveInt("PieAreaX", mode, null);
    }

    int loadPieAreaY() {
        return loadInt("PieAreaY", getIsSmallScreen() ? 300 : 600);
    }

    void savePieAreaY(int mode) {
        saveInt("PieAreaY", mode, null);
    }

    int loadPieAreaGravity() {
        return loadInt("PieAreaGravity", 0);
    }

    void savePieAreaGravity(int mode) {
        saveInt("PieAreaGravity", mode, null);
    }

    String loadPieColor() {
        return loadString("PieColorString", "0");
    }

    void savePieColor(String colors) {
        saveString("PieColorString", colors, null);
    }

    String loadPieStatusInfoColor() {
        return loadString("PieStatusInfoColorString", "0");
    }

    void savePieStatusInfoColor(String colors) {
        saveString("PieStatusInfoColorString", colors, null);
    }

    String loadPiePointerColor() {
        return loadString("PiePointerColorString", "0");
    }

    void savePiePointerColor(String colors) {
        saveString("PiePointerColorString", colors, null);
    }

    int loadPieFont() {
        return loadInt("PieFont", getIsSmallScreen() ? 3 : 4);
    }

    void savePieFont(int font) {
        saveInt("PieFont", font, null);
    }

    int loadPieInnerRadius() {
        return loadInt("PieInnerRadius", getIsSmallScreen() ? 40 : 60);
    }

    void savePieInnerRadius(int value) {
        saveInt("PieInnerRadius", value, null);
    }

    int loadPieOuterRadius() {
        return loadInt("PieOuterRadius", getIsSmallScreen() ? 60 : 80);
    }

    void savePieOuterRadius(int value) {
        saveInt("PieOuterRadius", value, null);
    }

    int loadPieShiftSize() {
        return loadInt("PieShiftSize", 0);
    }

    void savePieShiftSize(int value) {
        saveInt("PieShiftSize", value, null);
    }

    int loadPieOutlineSize() {
        return loadInt("PieOutlineSize", 3);
    }

    void savePieOutlineSize(int value) {
        saveInt("PieOutlineSize", value, null);
    }

    int loadPieSliceGap() {
        return loadInt("PieSliceGap", 0);
    }

    void savePieSliceGap(int value) {
        saveInt("PieSliceGap", value, null);
    }

    int loadPieStartGap() {
        return loadInt("PieStartGap", 0);
    }

    void savePieStartGap(int value) {
        saveInt("PieStartGap", value, null);
    }

    int loadPieRotateImages() {
        return loadInt("PieRotateImages", 1);
    }

    void savePieRotateImages(int value) {
        saveInt("PieRotateImages", value, null);
    }

    int loadPieLongpress() {
        return loadInt("PieLongpress", 500);
    }

    void savePieLongpress(int time) {
        saveInt("PieLongpress", time, null);
    }

    int loadPieAnimation() {
        return loadInt("PieAnimation", 80);
    }

    void savePieAnimation(int time) {
        saveInt("PieAnimation", time, null);
    }

    int loadPieVibrate() {
        return loadInt("PieVibrate", 1);
    }

    void savePieVibrate(int value) {
        saveInt("PieVibrate", value, null);
    }

    int loadPieMultiCommand() {
        return loadInt("PieMultiCommand", 0);
    }

    void savePieMultiCommand(int value) {
        saveInt("PieMultiCommand", value, null);
    }

    int loadPiePointerFromEdges() {
        return loadInt("PiePointerFromEdges", 0);
    }

    void savePiePointerFromEdges(int value) {
        saveInt("PiePointerFromEdges", value, null);
    }

    int loadPiePointerWarpFactor() {
        return loadInt("PiePointerWarpFactorPercent", 300);
    }

    void savePiePointerWarpFactor(int value) {
        if (value < 200) {
            value = ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
        }
        if (value > 1000) {
            value = 1000;
        }
        saveInt("PiePointerWarpFactorPercent", value, null);
    }

    int loadPieShowStatusInfos() {
        return loadInt("PieShowStatusInfos", 0);
    }

    void savePieShowStatusInfos(int value) {
        saveInt("PieShowStatusInfos", value, null);
    }

    int loadPieShowScaleAppImages() {
        return loadInt("PieShowAppImages", 1);
    }

    void savePieShowScaleAppImages(int value) {
        saveInt("PieShowAppImages", value, null);
    }

    int loadPieShowScaleUserImages() {
        return loadInt("PieUserImageScaling", 0);
    }

    void savePieShowScaleUserImages(int value) {
        saveInt("PieUserImageScaling", value, null);
    }

    int loadPieNavButtonStyle() {
        return loadInt("NavButtonStyle", 0);
    }

    void savePieNavButtonsStyle(int value) {
        saveInt("NavButtonStyle", value, null);
    }

    int loadPieExpandTriggerArea() {
        return loadInt("PieExpandTriggerArea", 1);
    }

    void savePieExpandTriggerArea(int value) {
        saveInt("PieExpandTriggerArea", value, null);
    }

    int loadPieAreaBehindKeyboard() {
        return loadInt("PieBehindKeyboard", 0);
    }

    void savePieAreaBehindKeyboard(int value) {
        saveInt("PieBehindKeyboard", value, null);
    }

    int loadPieOnLockScreen() {
        return loadInt("PieOnLockScreen", 0);
    }

    void savePieOnLockScreen(int value) {
        saveInt("PieOnLockScreen", value, null);
    }
}