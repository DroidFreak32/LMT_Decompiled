package com.example.lmt;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
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

    public SettingsViewHelper() {

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
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onItemClick(android.widget.AdapterView<?> r22, android.view.View r23, int r24, long r25) {
        /*
        // Method dump skipped, instructions count: 2257
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.lmt.SettingsViewHelper.onItemClick(android.widget.AdapterView, android.view.View, int, long):void");
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

        void addButton(Context context, LinearLayout linearLayout, boolean activated, CompoundButton.OnCheckedChangeListener l) {
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
                params.gravity = 8388629;
                button.setLayoutParams(params);
                linearLayout.addView(button);
            }
        }
    }
}