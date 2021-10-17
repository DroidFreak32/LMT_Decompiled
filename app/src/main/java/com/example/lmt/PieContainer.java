package com.example.lmt;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import java.util.Collections;
import java.util.List;

public class PieContainer {
    private static boolean DEBUG = false;
    private static final String TAG = "LMT::PieContainer";
    private boolean mAddedLayout1 = false;
    private boolean mAddedLayout2 = false;
    private boolean mAddedLayout3 = false;
    private PieControl mPieControl;
    private PieLayout mPieLayout1;
    private PieLayout mPieLayout2;
    private PieLayout mPieLayout3;
    private SettingsValues mSettings;
    private WindowManager mWindowManager;

    static void setDebug(boolean debug) {
        DEBUG = debug;
    }

    class PieLayout extends FrameLayout {
        boolean mAllowed;
        boolean mBottom;
        boolean mPieControlAdded = false;
        boolean mShrinking = false;

        public PieLayout(Context context, boolean bottom) {
            super(context);
            setWillNotDraw(true);
            setEnabled(false);
            this.mBottom = bottom;
        }

        public boolean dispatchTouchEvent(MotionEvent ev) {
            if (PieContainer.DEBUG) {
                Log.d(PieContainer.TAG, "dispatchTouchEvent " + ev);
            }
            if (ev.getToolType(0) == 0) {
                return false;
            }
            int action = ev.getActionMasked();
            if (action == 0) {
                this.mAllowed = PieContainer.this.mSettings.isNotBlacklistedPie();
                if (this.mAllowed) {
                    expandLayout();
                }
            } else if (1 == action) {
                shrinkLayout();
            }
            if (!this.mAllowed) {
                return false;
            }
            PieContainer.this.mPieControl.mPie.onTouchEvent(ev);
            return true;
        }

        public void onSizeChanged(int w, int h, int oldw, int oldh) {
            if (PieContainer.DEBUG) {
                Log.d(PieContainer.TAG, "onSizeChanged new:" + w + "," + h + " old:" + oldw + "," + oldh);
            }
            super.onSizeChanged(w, h, oldw, oldh);
            if (this.mShrinking) {
                PieContainer.this.mPieControl.mPie.onShrink();
                this.mShrinking = false;
            }
            if (oldw == 0 && oldh == 0 && Build.VERSION.SDK_INT >= 29) {
                setExclusionRects();
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.Q)
        private void setExclusionRects() {
            Rect boundingBox = new Rect();
            List<Rect> exclusions = Collections.singletonList(boundingBox);
            boundingBox.set(getLeft(), getTop(), getRight(), getBottom());
            if (PieContainer.DEBUG) {
                Log.d(PieContainer.TAG, "setExclusionRects:" + boundingBox);
            }
            setSystemGestureExclusionRects(exclusions);
        }

        public void expandLayout() {
            if (PieContainer.DEBUG) {
                Log.d(PieContainer.TAG, "expandLayout");
            }
            WindowManager.LayoutParams params = (WindowManager.LayoutParams) getLayoutParams();
            if (!(params == null || PieContainer.this.mSettings == null || PieContainer.this.mWindowManager == null)) {
                params.width = -1;
                params.height = -1;
                params.format = -2;
                try {
                    PieContainer.this.mWindowManager.updateViewLayout(this, params);
                } catch (Exception e) {
                }
            }
            if (PieContainer.this.mPieControl != null && !this.mPieControlAdded) {
                PieContainer.this.mPieControl.attachToContainer(this);
                this.mPieControlAdded = true;
            }
        }

        public void shrinkLayout() {
            if (PieContainer.DEBUG) {
                Log.d(PieContainer.TAG, "shrinkLayout");
            }
            WindowManager.LayoutParams params = (WindowManager.LayoutParams) getLayoutParams();
            if (!(params == null || PieContainer.this.mSettings == null || PieContainer.this.mWindowManager == null)) {
                params.width = this.mBottom ? PieContainer.this.mSettings.loadPieAreaY() : PieContainer.this.mSettings.loadPieAreaX();
                params.height = this.mBottom ? PieContainer.this.mSettings.loadPieAreaX() : PieContainer.this.mSettings.loadPieAreaY();
                params.format = -2;
                try {
                    PieContainer.this.mWindowManager.updateViewLayout(this, params);
                } catch (Exception e) {
                }
            }
            if (PieContainer.this.mPieControl != null && this.mPieControlAdded) {
                PieContainer.this.mPieControl.removeFromContainer(this);
                this.mPieControlAdded = false;
                this.mShrinking = true;
            }
        }

        public void debug(boolean activate) {
            WindowManager.LayoutParams params = (WindowManager.LayoutParams) getLayoutParams();
            if (params != null && PieContainer.this.mSettings != null && PieContainer.this.mWindowManager != null && PieContainer.this.mPieControl != null) {
                if (activate) {
                    params.format = -1;
                } else {
                    params.format = -2;
                }
                try {
                    PieContainer.this.mWindowManager.updateViewLayout(this, params);
                } catch (Exception e) {
                }
            }
        }
    }

    PieContainer(Context context) {
        this.mSettings = SettingsValues.getInstance(context);
        this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        this.mPieControl = new PieControl(context);
        this.mPieLayout1 = new PieLayout(context, false);
        this.mPieLayout2 = new PieLayout(context, false);
        this.mPieLayout3 = new PieLayout(context, true);
    }

    void attachToWindowManager() {
        if (DEBUG) {
            Log.d(TAG, "attachToWindowManager");
        }
        int piePos = this.mSettings.loadPiePos();
        int pieAreaBehindKeyboard = this.mSettings.loadPieAreaBehindKeyboard();
        int pieOnLockScreen = this.mSettings.loadPieOnLockScreen();
        int pieGravity = this.mSettings.loadPieAreaGravity();
        int pieGravityFlag = 0;
        int pieYOffset = 0;
        if (1 == pieGravity) {
            pieGravityFlag = 48;
            pieYOffset = this.mSettings.getScreenHeight() / 10;
        }
        if (2 == pieGravity) {
            pieGravityFlag = 80;
            pieYOffset = this.mSettings.getScreenHeight() / 10;
        }
        if (piePos == 0 || piePos == 4 || piePos >= 6) {
            this.mWindowManager.addView(this.mPieLayout1, new WindowManager.LayoutParams(-2, -2, getWindowType(pieOnLockScreen), getWindowFlags(pieAreaBehindKeyboard), -2));
            WindowManager.LayoutParams params = (WindowManager.LayoutParams) this.mPieLayout1.getLayoutParams();
            params.width = this.mSettings.loadPieAreaX();
            params.height = this.mSettings.loadPieAreaY();
            params.gravity = 8388613 | pieGravityFlag;
            params.y = pieYOffset;
            this.mWindowManager.updateViewLayout(this.mPieLayout1, params);
            this.mAddedLayout1 = true;
        }
        if (piePos == 1 || piePos == 4 || piePos == 5 || piePos >= 7) {
            this.mWindowManager.addView(this.mPieLayout2, new WindowManager.LayoutParams(-2, -2, getWindowType(pieOnLockScreen), getWindowFlags(pieAreaBehindKeyboard), -2));
            WindowManager.LayoutParams params2 = (WindowManager.LayoutParams) this.mPieLayout2.getLayoutParams();
            params2.width = this.mSettings.loadPieAreaX();
            params2.height = this.mSettings.loadPieAreaY();
            params2.gravity = 8388611 | pieGravityFlag;
            params2.y = pieYOffset;
            this.mWindowManager.updateViewLayout(this.mPieLayout2, params2);
            this.mAddedLayout2 = true;
        }
        if (piePos == 2 || piePos == 3 || piePos >= 5) {
            this.mWindowManager.addView(this.mPieLayout3, new WindowManager.LayoutParams(-2, -2, getWindowType(pieOnLockScreen), getWindowFlags(pieAreaBehindKeyboard), -2));
            WindowManager.LayoutParams params3 = (WindowManager.LayoutParams) this.mPieLayout3.getLayoutParams();
            params3.width = this.mSettings.loadPieAreaY();
            params3.height = this.mSettings.loadPieAreaX();
            params3.gravity = 81;
            this.mWindowManager.updateViewLayout(this.mPieLayout3, params3);
            this.mAddedLayout3 = true;
        }
    }

    void removeFromWindowManager() {
        if (DEBUG) {
            Log.d(TAG, "removeFromWindowManager");
        }
        if (this.mAddedLayout1) {
            this.mWindowManager.removeView(this.mPieLayout1);
            this.mAddedLayout1 = false;
        }
        if (this.mAddedLayout2) {
            this.mWindowManager.removeView(this.mPieLayout2);
            this.mAddedLayout2 = false;
        }
        if (this.mAddedLayout3) {
            this.mWindowManager.removeView(this.mPieLayout3);
            this.mAddedLayout3 = false;
        }
    }

    void rotate() {
        if (DEBUG) {
            Log.d(TAG, "rotate");
        }
        if (this.mAddedLayout1) {
            this.mPieLayout1.shrinkLayout();
        }
        if (this.mAddedLayout2) {
            this.mPieLayout2.shrinkLayout();
        }
        if (this.mAddedLayout3) {
            this.mPieLayout3.shrinkLayout();
        }
        if (Build.VERSION.SDK_INT < 16) {
            removeFromWindowManager();
            attachToWindowManager();
        }
    }

    public void debug() {
        if (this.mAddedLayout1) {
            this.mPieLayout1.debug(true);
        }
        if (this.mAddedLayout2) {
            this.mPieLayout2.debug(true);
        }
        if (this.mAddedLayout3) {
            this.mPieLayout3.debug(true);
        }
    }

    private int getWindowType(int pieOnLockScreen) {
        if (Build.VERSION.SDK_INT < 26) {
            return pieOnLockScreen > 0 ? 2010 : 2003;
        }
        return 2038;
    }

    private int getWindowFlags(int pieAreaBehindKeyboard) {
        return 32 | (pieAreaBehindKeyboard > 0 ? 131072 : 32) | 8;
    }
}