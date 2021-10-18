package com.noname81.lmt;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import java.util.List;
import java.util.Map;

class SettingsViewHelper implements AdapterView.OnItemClickListener {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private Activity mActivity;
    private RootContext mRootContext;
    private SettingsValues mSettings;
    private TouchServiceNative mTouchServiceNative;
    int posActivateTouchService = 255;
    int posAddBlacklist = 255;
    int posAddBlacklistPie = 255;
    int posAutoConfiguration = 255;
    int posAutostartTouchService = 255;
    int posClearBlacklist = 255;
    int posClearBlacklistPie = 255;
    int posFeedbackStyle = 255;
    int posMinPathLength = 255;
    int posMinScore = 255;
    int posOffsetGestureRecognition = 255;
    int posOffsetGestures = 255;
    int posOffsetIsas = 255;
    int posOffsetPieActivation = 255;
    int posOffsetPieBehavior = 255;
    int posOffsetPieExtensions = 255;
    int posOffsetPieIcons = 255;
    int posOffsetPieStyle = 255;
    int posOffsetTouchservice = 255;
    int posPieAnimation = 255;
    int posPieAreaBehindKeyboard = 255;
    int posPieAreaGravity = 255;
    int posPieAreaX = 255;
    int posPieAreaY = 255;
    int posPieColor = 255;
    int posPieExpandTriggerArea = 255;
    int posPieInnerRadius = 255;
    int posPieLongpress = 255;
    int posPieMultiCommand = 255;
    int posPieNavButtonStyle = 255;
    int posPieOnLockScreen = 255;
    int posPieOuterRadius = 255;
    int posPieOutlineSize = 255;
    int posPiePointerColor = 255;
    int posPiePointerFromEdges = 255;
    int posPiePointerWarpFactor = 255;
    int posPiePos = 255;
    int posPieRotateImages = 255;
    int posPieShiftSize = 255;
    int posPieShowScaleAppImages = 255;
    int posPieShowScaleUserImages = 255;
    int posPieShowStatusInfos = 255;
    int posPieSliceGap = 255;
    int posPieStartGap = 255;
    int posPieStatusInfoColor = 255;
    int posPieStatusInfoFont = 255;
    int posPieUserImageSearchPath = 255;
    int posPieVibrate = 255;
    int posPieVibrationTime = 255;
    int posSetInput = 255;
    int posSetMode = 255;
    int posSingleSwipesAArea = 255;
    int posSingleSwipesBBox = 255;
    int posSingleTouchGestures = 255;
    int posTouchscreenScreenFactorX = 255;
    int posTouchscreenScreenFactorY = 255;
    int posVibrationTime = 255;

    SettingsViewHelper(Activity activity) {
        this.mActivity = activity;
        this.mSettings = SettingsValues.getInstance(activity);
        this.mRootContext = RootContext.getInstance(activity);
        this.mTouchServiceNative = TouchServiceNative.getInstance(activity);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
        r13 = new android.util.DisplayMetrics();
        ((android.view.WindowManager) r21.mActivity.getSystemService("window")).getDefaultDisplay().getRealMetrics(r13);
        r14 = r13.widthPixels;
        r15 = r13.heightPixels;
        r6 = r7[r11].indexOf(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0103, code lost:
        r16 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
        r3 = java.lang.Math.round((((float) java.lang.Integer.parseInt(r7[r11].substring(r6 + 4, r7[r11].indexOf(",", r6)))) / ((float) r14)) * 100.0f);
        r1 = r7[r11 + 1].indexOf(r0);
        r18 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x014e, code lost:
        r4 = java.lang.Math.round((((float) java.lang.Integer.parseInt(r7[r11 + 1].substring(r1 + 4, r7[r11 + 1].indexOf(",", r1)))) / ((float) r15)) * 100.0f);
        r2 = r2;
     */
    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long arg3) {
        if (pos != this.posActivateTouchService) {
            if (pos == this.posAutostartTouchService) {
                new InputDialog(this.mActivity, "Autostart service", new String[]{"Disabled", "Enabled"}, Integer.toString(this.mSettings.loadAutostart())) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC05401 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.saveAutostart(Integer.parseInt(input));
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posSetMode) {
                new InputDialog(this.mActivity, "Define feature set", new String[]{"Gestures [Root] and ISAS [Root] ", "Gestures [Root] , ISAS [Root] and Pie", "Pie"}, Integer.toString(this.mSettings.loadTouchServiceMode())) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC05512 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.saveTouchServiceMode(Integer.parseInt(input));
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                            SettingsViewHelper.this.mActivity.recreate();
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posAutoConfiguration) {
                String getEventWidth;
                String getEventHeight;
                int inputDevice = -1;
                int factorWidth = -1;
                int factorHeight= -1;
                int beginIndex;
                int endIndex;
                int getEventWidthRes;
                int getEventHeightRes;
                int wmHeightPixels;
                int wmWidthPixels;

                String getEventOP = this.mRootContext.runCommandResult("getevent -p | grep -E '(device|name|0035|0036)'", true);
                String[] stringList = getEventOP.split("\n");
                try {
                    Log.d("LMT", String.valueOf(stringList.length));
                    for (int i = 0; i < stringList.length; i++ ) {
                        // Get resolution from getevent command
                        if (stringList[i].contains("0035") &&
                                stringList[i + 1].contains("0036")) {
                            getEventWidth = stringList[i];
                            getEventHeight = stringList[i +1];
                            Log.d("LMT", "Index is " + i);
                            // Go back till we get /dev/input/XXXX
                            int j = i - 1;
                            while (j >= 0) {
                                if (stringList[j].contains("/dev/input")) {
                                    Log.d("LMT", stringList[j]);

                                    // Split to get the /dev/input path
                                    String[] inputDevString = stringList[j].split(" ");

                                    // Remove all non-numeric characters from /dev/input/XXXX to get the final input device
                                    inputDevice = Integer.parseInt(
                                            inputDevString[inputDevString.length - 1].replaceAll("[^0-9]", "")
                                    );
                                    Log.d("LMT", String.valueOf(inputDevice));
                                    break;
                                }
                                j--;
                            }
                            Log.d("LMT", getEventWidth);
                            // Extract resolution from getEventX
                            // Sample Input:                0035  : value 0, min 0, max 1080, fuzz 0, flat 0, resolution 0
                            beginIndex = getEventWidth.indexOf("max") + 4;
                            endIndex = getEventWidth.indexOf(",", beginIndex);
                            getEventWidthRes = Integer.parseInt(getEventWidth.substring(beginIndex, endIndex));
                            getEventHeightRes = Integer.parseInt(getEventHeight.substring(beginIndex, endIndex));
                            Log.d("LMT", beginIndex + ":" + endIndex);
                            Log.d("LMT", "XRes = " + getEventWidthRes + " YRes = " + getEventHeightRes);

                            /*
                            Window Manager tasks here to get resolutions
                            */
                            WindowManager windowManager = (WindowManager)this.mActivity.getSystemService(Context.WINDOW_SERVICE);
                            DisplayMetrics displayMetrics = new DisplayMetrics();
                            if (windowManager != null) {
                                windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
                            }
                            wmWidthPixels = displayMetrics.widthPixels;
                            wmHeightPixels = displayMetrics.heightPixels;
                            factorWidth = Math.round(((float) getEventWidthRes / wmWidthPixels) * 100.0F);
                            factorHeight = Math.round(((float) getEventHeightRes / wmHeightPixels) * 100.0F);

                            Log.d("LMT", "FactorX: " + factorWidth + "\nFactorY: " + factorHeight + "\nInputDevice: " + inputDevice);
                            break;
                        }
                    }
                } catch (Exception e) {
                    Log.e("LMT", e.toString());
                } finally {
                    final int inputDeviceFinal = inputDevice;
                    final int factorWidthFinal = factorWidth;
                    final int factorHeightFinal = factorHeight;
                    if (inputDeviceFinal > -1 && factorWidthFinal > -1 && factorHeightFinal > -1) {
                        AlertDialog.Builder autoConfBuilder = new AlertDialog.Builder(this.mActivity).setTitle("Do you want to use these values?");
                        autoConfBuilder.setMessage("Input device: " + inputDevice
                                + "\nTouchscreen/screen factor x: " + factorWidth
                                + "\nTouchscreen/screen factor y: " + factorHeight);
                        autoConfBuilder.setCancelable(false);

                        autoConfBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SettingsViewHelper.this.mSettings.saveInputDevice(inputDeviceFinal);
                                SettingsViewHelper.this.mSettings.saveTouchscreenScreenFactorX(factorWidthFinal);
                                SettingsViewHelper.this.mSettings.saveTouchscreenScreenFactorY(factorHeightFinal);
                                SettingsViewHelper.this.mTouchServiceNative.setInputDeviceUnlock(SettingsViewHelper.this.mSettings.loadInputDeviceString());
                                SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                                dialog.dismiss();
                            }
                        });

                        autoConfBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        autoConfBuilder.create().show();
                    }
                }
            } else if (pos == this.posSetInput) {
                new InputDialog(this.mActivity, "Set input device", new String[]{"Input device 0", "Input device 1", "Input device 2", "Input device 3", "Input device 4", "Input device 5", "Input device 6", "Input device 7", "Input device 8", "Input device 9", "Input device 10", "Input device 11", "Input device 12", "Input device 13", "Input device 14", "Input device 15", "Input device 16", "Input device 17", "Input device 18", "Input device 19", "Input device 20"}, Integer.toString(this.mSettings.loadInputDevice())) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC05795 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.saveInputDevice(Integer.parseInt(input));
                            SettingsViewHelper.this.mTouchServiceNative.setInputDeviceUnlock(SettingsViewHelper.this.mSettings.loadInputDeviceString());
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posAddBlacklist) {
                MultiSelectActivity.selectMode = MultiSelectActivity.SelectBlacklisted;
                this.mActivity.startActivity(new Intent(this.mActivity, MultiSelectActivity.class));
            } else if (pos == this.posClearBlacklist) {
                this.mSettings.clearBlacklisted();
            } else if (pos == this.posAddBlacklistPie) {
                MultiSelectActivity.selectMode = MultiSelectActivity.SelectBlacklistedPie;
                this.mActivity.startActivity(new Intent(this.mActivity, MultiSelectActivity.class));
            } else if (pos == this.posClearBlacklistPie) {
                this.mSettings.clearBlacklistedPie();
            } else if (pos == this.posFeedbackStyle) {
                new InputDialog(this.mActivity, "Feedback style", new String[]{"No feedback", "Debug overlay", "Image overlay", "Tactile feedback"}, Integer.toString(this.mSettings.loadFeedbackMode())) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC05806 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            Toaster.getInstance(this.mContext).setMode(Integer.parseInt(input));
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posVibrationTime) {
                new InputDialog(this.mActivity, "Vibration time", "Set new value", Integer.toString(this.mSettings.loadGestureVibrationTime()), true) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC05817 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.saveGestureVibrationTime(Integer.parseInt(input));
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posSingleTouchGestures) {
                new InputDialog(this.mActivity, "Single touch gestures", new String[]{"Disbaled", "Enabled"}, Integer.toString(this.mSettings.loadSingleTouchGestureSupport())) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC05828 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            int value = Integer.parseInt(input);
                            SettingsViewHelper.this.mSettings.saveSingleTouchGestureSupport(value);
                            SettingsViewHelper.this.mTouchServiceNative.setSingleTouchGestureSupport(value);
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posMinScore) {
                new InputDialog(this.mActivity, "Min gesture score", "Set new value", Integer.toString(this.mSettings.loadMinScore()), true) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC05839 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            int value = Integer.parseInt(input);
                            SettingsViewHelper.this.mSettings.saveMinScore(value);
                            SettingsViewHelper.this.mTouchServiceNative.setMinScore(value);
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posMinPathLength) {
                new InputDialog(this.mActivity, "Min gesture path length", "Set new value", Integer.toString(this.mSettings.loadMinPathLength()), true) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC054110 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            int value = Integer.parseInt(input);
                            SettingsViewHelper.this.mSettings.saveMinPathLength(value);
                            SettingsViewHelper.this.mTouchServiceNative.setMinPathLength(value);
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posSingleSwipesBBox) {
                new InputDialog(this.mActivity, "Min bbox size", "Set new value", Integer.toString(this.mSettings.loadSingleSwipesBBox()), true) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC054211 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            int value = Integer.parseInt(input);
                            SettingsViewHelper.this.mSettings.saveSingleSwipesBBox(value);
                            SettingsViewHelper.this.mTouchServiceNative.setSingleSwipesBBox(value);
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posSingleSwipesAArea) {
                new InputDialog(this.mActivity, "Activation area thickness", "Set new value", Integer.toString(this.mSettings.loadSingleSwipesAArea()), true) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC054312 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.saveSingleSwipesAArea(Integer.parseInt(input));
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posTouchscreenScreenFactorX) {
                new InputDialog(this.mActivity, "Touchscreen to screen factor X", "Set new value", Integer.toString((int) (this.mSettings.loadTouchscreenScreenFactorX() * 100.0f)), true) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC054413 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.saveTouchscreenScreenFactorX(Integer.parseInt(input));
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posTouchscreenScreenFactorY) {
                new InputDialog(this.mActivity, "Touchscreen to screen factor Y", "Set new value", Integer.toString((int) (this.mSettings.loadTouchscreenScreenFactorY() * 100.0f)), true) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC054514 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.saveTouchscreenScreenFactorY(Integer.parseInt(input));
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posPiePos) {
                new InputDialog(this.mActivity, "Activation area position", new String[]{"Right", "Left", "Bottom", "Bottom\n(not centered)", "Left and right", "Left and bottom", "Right and bottom", "Left, right and bottom", "Left, right and bottom\n(not centered)"}, Integer.toString(this.mSettings.loadPiePos())) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC054615 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.savePiePos(Integer.parseInt(input));
                            SettingsViewHelper.this.mSettings.setVisiblePieActivationAread(true);
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                        } catch (NumberFormatException e) {
                        }
                        return true;
                    }
                }.show();
            } else if (pos == this.posPieAreaX) {
                new InputDialog(this.mActivity, "Activation area thickness", "Set new value", Integer.toString(this.mSettings.loadPieAreaX()), true) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC054716 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.savePieAreaX(Integer.parseInt(input));
                            SettingsViewHelper.this.mSettings.setVisiblePieActivationAread(true);
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                        } catch (NumberFormatException e) {
                        }
                        return true;
                    }
                }.show();
            } else if (pos == this.posPieAreaY) {
                new InputDialog(this.mActivity, "Activation area length", "Set new value", Integer.toString(this.mSettings.loadPieAreaY()), true) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC054817 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.savePieAreaY(Integer.parseInt(input));
                            SettingsViewHelper.this.mSettings.setVisiblePieActivationAread(true);
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                        } catch (NumberFormatException e) {
                        }
                        return true;
                    }
                }.show();
            } else if (pos == this.posPieAreaGravity) {
                new InputDialog(this.mActivity, "Activation area gravity", new String[]{"Center", "Top", "Bottom"}, Integer.toString(this.mSettings.loadPieAreaGravity())) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC054918 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.savePieAreaGravity(Integer.parseInt(input));
                            SettingsViewHelper.this.mSettings.setVisiblePieActivationAread(true);
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                        } catch (NumberFormatException e) {
                        }
                        return true;
                    }
                }.show();
            } else if (pos == this.posPieAreaBehindKeyboard) {
                new InputDialog(this.mActivity, "Set pie area behind keyboard", new String[]{"Disabled", "Enabled"}, Integer.toString(this.mSettings.loadPieAreaBehindKeyboard())) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC055019 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.savePieAreaBehindKeyboard(Integer.parseInt(input));
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posPieOnLockScreen) {
                new InputDialog(this.mActivity, "Set pie on lockscreen", new String[]{"Disabled", "Enabled"}, Integer.toString(this.mSettings.loadPieOnLockScreen())) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC055220 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.savePieOnLockScreen(Integer.parseInt(input));
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posPieColor) {
                new ColorDialog(this.mActivity, "Pie colors", new String[]{"normal color", "selected color", "icon color", "outline color", "gradient color", "gradient outline color", "shift color"}, this.mSettings.loadPieColor()) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC055321 */

                    @Override // com.noname81.lmt.ColorDialog
                    public boolean onOkClicked(String input) {
                        SettingsViewHelper.this.mSettings.savePieColor(input);
                        SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                        return true;
                    }
                }.show();
            } else if (pos == this.posPieStatusInfoColor) {
                new ColorDialog(this.mActivity, "Pie status info colors", new String[]{"clock color", "notification color", "dim color"}, this.mSettings.loadPieStatusInfoColor()) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC055422 */

                    @Override // com.noname81.lmt.ColorDialog
                    public boolean onOkClicked(String input) {
                        SettingsViewHelper.this.mSettings.savePieStatusInfoColor(input);
                        SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                        return true;
                    }
                }.show();
            } else if (pos == this.posPiePointerColor) {
                new ColorDialog(this.mActivity, "Pie pointer colors", new String[]{"pointer color"}, this.mSettings.loadPiePointerColor()) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC055523 */

                    @Override // com.noname81.lmt.ColorDialog
                    public boolean onOkClicked(String input) {
                        SettingsViewHelper.this.mSettings.savePiePointerColor(input);
                        SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                        return true;
                    }
                }.show();
            } else if (pos == this.posPieStatusInfoFont) {
                new InputDialog(this.mActivity, "Pie font", new String[]{"Roboto Thin (small)", "Roboto Thin (normal)", "Roboto Thin (big)", "Roboto Light (small)", "Roboto Light (normal)", "Roboto Light (big)", "System font (small)", "System font (normal)", "System font (big)"}, Integer.toString(this.mSettings.loadPieFont())) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC055624 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.savePieFont(Integer.parseInt(input));
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posPieInnerRadius) {
                new InputDialog(this.mActivity, "Pie inner radius", "Set new value", Integer.toString(this.mSettings.loadPieInnerRadius()), true) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC055725 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.savePieInnerRadius(Integer.parseInt(input));
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posPieOuterRadius) {
                new InputDialog(this.mActivity, "Pie outer radius", "Set new value", Integer.toString(this.mSettings.loadPieOuterRadius()), true) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC055826 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.savePieOuterRadius(Integer.parseInt(input));
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posPieShiftSize) {
                new InputDialog(this.mActivity, "Pie shift size", "Set new value", Integer.toString(this.mSettings.loadPieShiftSize()), true) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC055927 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.savePieShiftSize(Integer.parseInt(input));
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posPieOutlineSize) {
                new InputDialog(this.mActivity, "Pie outline size", "Set new value", Integer.toString(this.mSettings.loadPieOutlineSize()), true) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC056028 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.savePieOutlineSize(Integer.parseInt(input));
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posPieSliceGap) {
                new InputDialog(this.mActivity, "Pie slice gap", "Set new value", Integer.toString(this.mSettings.loadPieSliceGap()), true) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC056129 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.savePieSliceGap(Integer.parseInt(input));
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posPieStartGap) {
                new InputDialog(this.mActivity, "Pie start gap", "Set new value", Integer.toString(this.mSettings.loadPieStartGap()), true) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC056330 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.savePieStartGap(Integer.parseInt(input));
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posPieLongpress) {
                new InputDialog(this.mActivity, "Longpress time", "Set new value", Integer.toString(this.mSettings.loadPieLongpress()), true) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC056431 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.savePieLongpress(Integer.parseInt(input));
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posPieAnimation) {
                new InputDialog(this.mActivity, "Animation time", "Set new value", Integer.toString(this.mSettings.loadPieAnimation()), true) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC056532 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.savePieAnimation(Integer.parseInt(input));
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posPieVibrate) {
                new InputDialog(this.mActivity, "Feedback type", new String[]{"Disabled", "Only longpress", "Shortpress and longpress", "Longpress and fire", "Shortpress, longpress and fire"}, Integer.toString(this.mSettings.loadPieVibrate())) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC056633 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.savePieVibrate(Integer.parseInt(input));
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posPieVibrationTime) {
                new InputDialog(this.mActivity, "Pie vibration time", "Set new value", Integer.toString(this.mSettings.loadPieVibrationTime()), true) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC056734 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.savePieVibrationTime(Integer.parseInt(input));
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posPieMultiCommand) {
                new InputDialog(this.mActivity, "Multi command", new String[]{"Disabled", "Enabled"}, Integer.toString(this.mSettings.loadPieMultiCommand())) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC056835 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.savePieMultiCommand(Integer.parseInt(input));
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posPieExpandTriggerArea) {
                new InputDialog(this.mActivity, "Expand trigger area", new String[]{"Disabled", "Enabled"}, Integer.toString(this.mSettings.loadPieExpandTriggerArea())) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC056936 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.savePieExpandTriggerArea(Integer.parseInt(input));
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posPiePointerFromEdges) {
                new InputDialog(this.mActivity, "Show pointer", new String[]{"Disabled", "Enabled"}, Integer.toString(this.mSettings.loadPiePointerFromEdges())) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC057037 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.savePiePointerFromEdges(Integer.parseInt(input));
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posPiePointerWarpFactor) {
                new InputDialog(this.mActivity, "Set pie pointer warp factor", "Set new value", Integer.toString(this.mSettings.loadPiePointerWarpFactor()), true) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC057138 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.savePiePointerWarpFactor(Integer.parseInt(input));
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posPieShowStatusInfos) {
                new InputDialog(this.mActivity, "Show status infos", new String[]{"Disabled", "Enabled along pie", "Enabled horizontal", "Enabled with sensor control"}, Integer.toString(this.mSettings.loadPieShowStatusInfos())) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC057239 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.savePieShowStatusInfos(Integer.parseInt(input));
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posPieRotateImages) {
                new InputDialog(this.mActivity, "Set rotate Images", new String[]{"Disabled", "Enabled"}, Integer.toString(this.mSettings.loadPieRotateImages())) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC057440 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.savePieRotateImages(Integer.parseInt(input));
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posPieNavButtonStyle) {
                new InputDialog(this.mActivity, "Set nav button style", new String[]{"Android Lollipop", "Android KitKat", "Google Pixel"}, Integer.toString(this.mSettings.loadPieNavButtonStyle())) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC057541 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.savePieNavButtonsStyle(Integer.parseInt(input));
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posPieShowScaleAppImages) {
                new InputDialog(this.mActivity, "Show and scale app images", "Set new value", Integer.toString(this.mSettings.loadPieShowScaleAppImages()), true) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC057642 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.savePieShowScaleAppImages(Integer.parseInt(input));
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posPieShowScaleUserImages) {
                new InputDialog(this.mActivity, "Show and scale user images", "Set new value", Integer.toString(this.mSettings.loadPieShowScaleUserImages()), true) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC057743 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        try {
                            SettingsViewHelper.this.mSettings.savePieShowScaleUserImages(Integer.parseInt(input));
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                            return true;
                        } catch (NumberFormatException e) {
                            return true;
                        }
                    }
                }.show();
            } else if (pos == this.posPieUserImageSearchPath) {
                new InputDialog(this.mActivity, "User image search path", "Set new value", this.mSettings.loadResourceSearchPath(), false) {
                    /* class com.noname81.lmt.SettingsViewHelper.AlertDialog$BuilderC057844 */

                    @Override // com.noname81.lmt.InputDialog
                    public boolean onOkClicked(String input) {
                        SettingsViewHelper.this.mSettings.saveResourceSearchPath(input);
                        SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                        return true;
                    }
                }.show();
            }
        } else if (this.mSettings.getServiceState() == 0) {
            this.mSettings.startService();
        } else {
            this.mSettings.stopService();
        }
    }

    public class SettingsSimpleAdapter extends SimpleAdapter {
        static final int listitem_description_button = 2131034368;
        private Context mContext;
        private int mOffset;
        private boolean mSimpleUI;

        SettingsSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int offset, boolean simpleUI) {
            super(context, data, R.layout.listitem_description, new String[]{"title", "caption"}, new int[]{R.id.listitem_description_text, R.id.listitem_description_caption});
            this.mContext = context;
            this.mSimpleUI = simpleUI;
            this.mOffset = offset;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View row = super.getView(position, convertView, parent);
            LinearLayout linearLayout = (LinearLayout) row;
            boolean z = true;
            int pos = this.mOffset + position + 1;
            if (pos == SettingsViewHelper.this.posActivateTouchService) {
                Context context = this.mContext;
                if (SettingsViewHelper.this.mSettings.getServiceState() <= 0) {
                    z = false;
                }
                addButton(context, linearLayout, z, new CompoundButton.OnCheckedChangeListener() {
                    /* class com.noname81.lmt.SettingsViewHelper.SettingsSimpleAdapter.C05841 */

                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked && SettingsViewHelper.this.mSettings.getServiceState() == 0) {
                            SettingsViewHelper.this.mSettings.startService();
                        } else if (!isChecked && SettingsViewHelper.this.mSettings.getServiceState() == 1) {
                            SettingsViewHelper.this.mSettings.stopService();
                        }
                    }
                });
            } else if (pos == SettingsViewHelper.this.posAutostartTouchService) {
                Context context2 = this.mContext;
                if (SettingsViewHelper.this.mSettings.loadAutostart() <= 0) {
                    z = false;
                }
                addButton(context2, linearLayout, z, new CompoundButton.OnCheckedChangeListener() {
                    /* class com.noname81.lmt.SettingsViewHelper.SettingsSimpleAdapter.C05852 */

                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked && SettingsViewHelper.this.mSettings.loadAutostart() == 0) {
                            SettingsViewHelper.this.mSettings.saveAutostart(1);
                        } else if (!isChecked && SettingsViewHelper.this.mSettings.loadAutostart() == 1) {
                            SettingsViewHelper.this.mSettings.saveAutostart(0);
                        }
                    }
                });
            } else if (pos == SettingsViewHelper.this.posSingleTouchGestures) {
                Context context3 = this.mContext;
                if (SettingsViewHelper.this.mSettings.loadSingleTouchGestureSupport() <= 0) {
                    z = false;
                }
                addButton(context3, linearLayout, z, new CompoundButton.OnCheckedChangeListener() {
                    /* class com.noname81.lmt.SettingsViewHelper.SettingsSimpleAdapter.C05863 */

                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked && SettingsViewHelper.this.mSettings.loadSingleTouchGestureSupport() == 0) {
                            SettingsViewHelper.this.mSettings.saveSingleTouchGestureSupport(1);
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                        } else if (!isChecked && SettingsViewHelper.this.mSettings.loadSingleTouchGestureSupport() == 1) {
                            SettingsViewHelper.this.mSettings.saveSingleTouchGestureSupport(0);
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                        }
                    }
                });
            } else if (pos == SettingsViewHelper.this.posPieAreaBehindKeyboard) {
                Context context4 = this.mContext;
                if (SettingsViewHelper.this.mSettings.loadPieAreaBehindKeyboard() <= 0) {
                    z = false;
                }
                addButton(context4, linearLayout, z, new CompoundButton.OnCheckedChangeListener() {
                    /* class com.noname81.lmt.SettingsViewHelper.SettingsSimpleAdapter.C05874 */

                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked && SettingsViewHelper.this.mSettings.loadPieAreaBehindKeyboard() == 0) {
                            SettingsViewHelper.this.mSettings.savePieAreaBehindKeyboard(1);
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                        } else if (!isChecked && SettingsViewHelper.this.mSettings.loadPieAreaBehindKeyboard() == 1) {
                            SettingsViewHelper.this.mSettings.savePieAreaBehindKeyboard(0);
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                        }
                    }
                });
            } else if (pos == SettingsViewHelper.this.posPieOnLockScreen) {
                Context context5 = this.mContext;
                if (SettingsViewHelper.this.mSettings.loadPieOnLockScreen() <= 0) {
                    z = false;
                }
                addButton(context5, linearLayout, z, new CompoundButton.OnCheckedChangeListener() {
                    /* class com.noname81.lmt.SettingsViewHelper.SettingsSimpleAdapter.C05885 */

                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked && SettingsViewHelper.this.mSettings.loadPieOnLockScreen() == 0) {
                            SettingsViewHelper.this.mSettings.savePieOnLockScreen(1);
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                        } else if (!isChecked && SettingsViewHelper.this.mSettings.loadPieOnLockScreen() == 1) {
                            SettingsViewHelper.this.mSettings.savePieOnLockScreen(0);
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                        }
                    }
                });
            } else if (pos == SettingsViewHelper.this.posPieRotateImages) {
                Context context6 = this.mContext;
                if (SettingsViewHelper.this.mSettings.loadPieRotateImages() <= 0) {
                    z = false;
                }
                addButton(context6, linearLayout, z, new CompoundButton.OnCheckedChangeListener() {
                    /* class com.noname81.lmt.SettingsViewHelper.SettingsSimpleAdapter.C05896 */

                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked && SettingsViewHelper.this.mSettings.loadPieRotateImages() == 0) {
                            SettingsViewHelper.this.mSettings.savePieRotateImages(1);
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                        } else if (!isChecked && SettingsViewHelper.this.mSettings.loadPieRotateImages() == 1) {
                            SettingsViewHelper.this.mSettings.savePieRotateImages(0);
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                        }
                    }
                });
            } else if (pos == SettingsViewHelper.this.posPieExpandTriggerArea) {
                Context context7 = this.mContext;
                if (SettingsViewHelper.this.mSettings.loadPieExpandTriggerArea() <= 0) {
                    z = false;
                }
                addButton(context7, linearLayout, z, new CompoundButton.OnCheckedChangeListener() {
                    /* class com.noname81.lmt.SettingsViewHelper.SettingsSimpleAdapter.C05907 */

                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked && SettingsViewHelper.this.mSettings.loadPieExpandTriggerArea() == 0) {
                            SettingsViewHelper.this.mSettings.savePieExpandTriggerArea(1);
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                        } else if (!isChecked && SettingsViewHelper.this.mSettings.loadPieExpandTriggerArea() == 1) {
                            SettingsViewHelper.this.mSettings.savePieExpandTriggerArea(0);
                        }
                    }
                });
            } else if (pos == SettingsViewHelper.this.posPiePointerFromEdges) {
                Context context8 = this.mContext;
                if (SettingsViewHelper.this.mSettings.loadPiePointerFromEdges() <= 0) {
                    z = false;
                }
                addButton(context8, linearLayout, z, new CompoundButton.OnCheckedChangeListener() {
                    /* class com.noname81.lmt.SettingsViewHelper.SettingsSimpleAdapter.C05918 */

                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked && SettingsViewHelper.this.mSettings.loadPiePointerFromEdges() == 0) {
                            SettingsViewHelper.this.mSettings.savePiePointerFromEdges(1);
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                        } else if (!isChecked && SettingsViewHelper.this.mSettings.loadPiePointerFromEdges() == 1) {
                            SettingsViewHelper.this.mSettings.savePiePointerFromEdges(0);
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                        }
                    }
                });
            } else if (pos == SettingsViewHelper.this.posPieMultiCommand) {
                Context context9 = this.mContext;
                if (SettingsViewHelper.this.mSettings.loadPieMultiCommand() <= 0) {
                    z = false;
                }
                addButton(context9, linearLayout, z, new CompoundButton.OnCheckedChangeListener() {
                    /* class com.noname81.lmt.SettingsViewHelper.SettingsSimpleAdapter.C05929 */

                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked && SettingsViewHelper.this.mSettings.loadPieMultiCommand() == 0) {
                            SettingsViewHelper.this.mSettings.savePieMultiCommand(1);
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                        } else if (!isChecked && SettingsViewHelper.this.mSettings.loadPieMultiCommand() == 1) {
                            SettingsViewHelper.this.mSettings.savePieMultiCommand(0);
                            SettingsViewHelper.this.mSettings.restartServiceIfRequired();
                        }
                    }
                });
            }
            return row;
        }

        /* access modifiers changed from: package-private */
        public void addButton(Context context, LinearLayout linearLayout, boolean activated, CompoundButton.OnCheckedChangeListener l) {
            CompoundButton button;
            if (linearLayout.findViewById(listitem_description_button) == null) {
                if (this.mSimpleUI) {
                    button = new CheckBox(context);
                } else {
                    button = new Switch(context);
                }
                button.setId(listitem_description_button);
                button.setPadding(0, 0, 30, 0);
                button.setOnCheckedChangeListener(l);
                button.setChecked(activated);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
                params.gravity = Gravity.END;
                button.setLayoutParams(params);
                linearLayout.addView(button);
            }
        }
    }
}
