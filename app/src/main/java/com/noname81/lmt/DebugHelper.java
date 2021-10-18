package com.noname81.lmt;

import android.content.Context;

class DebugHelper {
    private static DebugHelper instance = null;
    private boolean mDebugLauncherEnabled = true;
    private boolean mDebugPieEnabled = true;
    private boolean mDebugTouchServiceEnabled = true;
    private boolean mNoRootEnabled = true;

    private DebugHelper() {
    }

    public static DebugHelper getInstance() {
        if (instance == null) {
            instance = new DebugHelper();
        }
        return instance;
    }

    void showDebugMenu(final Context context) {
//        new InputDialog(context.getString(R.string.dialog_set_debug_mode), new String[]{context.getString(R.string.dialog_none), context.getString(R.string.dialog_launcher_debug), context.getString(R.string.dialog_touch_service_debug), context.getString(R.string.dialog_pie_service_debug), context.getString(R.string.dialog_no_root)}, Integer.toString(0), context) {
        new InputDialog(context, context.getString(R.string.dialog_set_debug_mode), new String[]{context.getString(R.string.dialog_none), context.getString(R.string.dialog_launcher_debug), context.getString(R.string.dialog_touch_service_debug), context.getString(R.string.dialog_pie_service_debug), context.getString(R.string.dialog_no_root)}, Integer.toString(0)) {
            /* class com.noname81.lmt.DebugHelper.AlertDialog$BuilderC05151 */

            @Override // com.noname81.lmt.InputDialog
            public boolean onOkClicked(String input) {
                try {
                    int value = Integer.parseInt(input);
                    boolean z = false;
                    if (value == 1) {
                        DebugHelper debugHelper = DebugHelper.this;
                        if (!DebugHelper.this.mDebugLauncherEnabled) {
                            z = true;
                        }
                        debugHelper.mDebugLauncherEnabled = z;
                        DebugHelper.this.setDebugLauncher(context, DebugHelper.this.mDebugLauncherEnabled);
                    } else if (value == 2) {
                        DebugHelper debugHelper2 = DebugHelper.this;
                        if (!DebugHelper.this.mDebugTouchServiceEnabled) {
                            z = true;
                        }
                        debugHelper2.mDebugTouchServiceEnabled = z;
                        DebugHelper.this.setDebugTouchService(context, DebugHelper.this.mDebugTouchServiceEnabled);
                    } else if (value == 3) {
                        DebugHelper debugHelper3 = DebugHelper.this;
                        if (!DebugHelper.this.mDebugPieEnabled) {
                            z = true;
                        }
                        debugHelper3.mDebugPieEnabled = z;
                        DebugHelper.this.setPieDebug(DebugHelper.this.mDebugPieEnabled);
                    } else if (value == 4) {
                        DebugHelper debugHelper4 = DebugHelper.this;
                        if (!DebugHelper.this.mNoRootEnabled) {
                            z = true;
                        }
                        debugHelper4.mNoRootEnabled = z;
                        RootContext.getInstance(context).setInitialized(DebugHelper.this.mNoRootEnabled);
                    }
                } catch (NumberFormatException e) {
                }
                return true;
            }
        }.show();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setDebugTouchService(Context context, boolean enable) {
        TouchServiceNative.getInstance(context).setDebug(enable ? 1 : 0);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setDebugLauncher(Context context, boolean enable) {
        if (enable) {
            Launcher.setDebug(1);
            AccessibilityHandler.setDebug(1);
            RootContext.getInstance(context).runCommandRemote("debug 1", false);
            RootContext.getInstance(context).setDebug(1);
            return;
        }
        Launcher.setDebug(0);
        AccessibilityHandler.setDebug(0);
        RootContext.getInstance(context).runCommandRemote("debug 0", false);
        RootContext.getInstance(context).setDebug(0);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setPieDebug(boolean enable) {
        PieContainer.setDebug(enable);
        PieContainer.setDebug(enable);
        PieMenu.setDebug(enable);
    }
}