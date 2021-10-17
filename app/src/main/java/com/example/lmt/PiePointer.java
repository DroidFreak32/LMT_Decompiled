package com.example.lmt;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.os.Handler;
import android.widget.FrameLayout;
import com.example.lmt.PieMenu;

class PiePointer implements PieMenu.PieView {
    private static final float EMPTY_ANGLE = 0.19634955f;
    private static final int STROKE_SIZE = 4;
    private boolean mActivated;
    private Point mCenter;
    private Context mContext;
    private boolean mFromTheEgdes;
    private int mLevels;
    private float mMargin;
    private boolean mOnTheBottom;
    private FrameLayout mPieMenu;
    private Paint mPointerPaint = new Paint();
    private Paint mPointerPaintOutline;
    private PointF mPolar;
    private int mRadius;
    private int mRadiusInc;
    private RootContext mRootContext;
    private SettingsValues mSettings;
    private float mWarpFactor;

    /* renamed from: mX */
    private float f37mX;
    private float mXO;

    /* renamed from: mY */
    private float f38mY;
    private float mYO;

    PiePointer(FrameLayout pieMenu, Context context, int color) {
        this.mContext = context;
        this.mPieMenu = pieMenu;
        this.mSettings = SettingsValues.getInstance(context);
        this.mRootContext = RootContext.getInstance(context);
        this.mPointerPaint.setAntiAlias(true);
        this.mPointerPaint.setStyle(Paint.Style.STROKE);
        this.mPointerPaint.setStrokeWidth(4.0f);
        this.mPointerPaint.setColor(color);
        this.mPointerPaintOutline = new Paint();
        this.mPointerPaintOutline.setAntiAlias(true);
        this.mPointerPaintOutline.setStyle(Paint.Style.STROKE);
        this.mPointerPaintOutline.setStrokeWidth(4.0f);
        this.mPointerPaintOutline.setColor(-1);
        this.mPointerPaintOutline.setAlpha(100);
        boolean z = false;
        this.mActivated = false;
        this.mFromTheEgdes = this.mSettings.loadPiePointerFromEdges() > 0 ? true : z;
        double loadPiePointerWarpFactor = (double) this.mSettings.loadPiePointerWarpFactor();
        Double.isNaN(loadPiePointerWarpFactor);
        this.mWarpFactor = (float) (loadPiePointerWarpFactor / 100.0d);
        this.mMargin = (float) (((int) Math.sqrt((double) (this.mSettings.getScreenWidth() * this.mSettings.getScreenHeight()))) / 30);
    }

    @Override // com.noname81.lmt.PieMenu.PieView
    public void activate() {
        this.mActivated = true;
    }

    @Override // com.noname81.lmt.PieMenu.PieView
    public void layout(boolean activate, Point center, int radius, int radiusInc, int levels, boolean onTheLeft, boolean onTheBootom, boolean relayout) {
        this.mCenter = center;
        this.mRadius = radius;
        this.mRadiusInc = radiusInc;
        this.mLevels = levels;
        this.mOnTheBottom = onTheBootom;
        this.mPointerPaint.setShader(new RadialGradient((float) this.mCenter.x, (float) this.mCenter.y, (float) (this.mRadius + (this.mRadiusInc * (this.mLevels + 1))), Color.argb(0, Color.red(this.mPointerPaint.getColor()), Color.green(this.mPointerPaint.getColor()), Color.blue(this.mPointerPaint.getColor())), this.mPointerPaint.getColor(), Shader.TileMode.CLAMP));
    }

    @Override // com.noname81.lmt.PieMenu.PieView
    public boolean drawForeground(Canvas canvas) {
        return this.mActivated;
    }

    @Override // com.noname81.lmt.PieMenu.PieView
    public boolean drawBackground(Canvas canvas) {
        Point point;
        if (!(!this.mActivated || (point = this.mCenter) == null || this.mPolar == null)) {
            canvas.drawLine((float) (point.x + 4), (float) (this.mCenter.y + 4), this.f37mX + 4.0f, this.f38mY + 4.0f, this.mPointerPaintOutline);
            canvas.drawCircle((float) (this.mCenter.x + 4), (float) (this.mCenter.y + 4), this.mPolar.y, this.mPointerPaintOutline);
            canvas.drawCircle(this.f37mX + 4.0f, this.f38mY + 4.0f, 16.0f, this.mPointerPaintOutline);
            canvas.drawLine((float) this.mCenter.x, (float) this.mCenter.y, this.f37mX, this.f38mY, this.mPointerPaint);
            canvas.drawCircle((float) this.mCenter.x, (float) this.mCenter.y, this.mPolar.y, this.mPointerPaint);
            canvas.drawCircle(this.f37mX, this.f38mY, 16.0f, this.mPointerPaint);
        }
        return this.mActivated;
    }

    @Override // com.noname81.lmt.PieMenu.PieView
    public boolean onTouchEvent(int action, float x, float y, float xo, float yo, PointF polar) {
        if (this.mFromTheEgdes && polar.y > ((float) this.mRadius)) {
            if ((this.mOnTheBottom && polar.x > 1.3744469f) || polar.x < -1.3744469f) {
                this.mActivated = true;
            } else if (!this.mOnTheBottom && (polar.x > 2.9452431f || polar.x < EMPTY_ANGLE)) {
                this.mActivated = true;
            }
        }
        if (2 == action && this.mActivated) {
            this.f37mX = ((this.mWarpFactor - 1.0f) * (x - ((float) this.mCenter.x))) + x;
            this.f38mY = ((this.mWarpFactor - 1.0f) * (y - ((float) this.mCenter.y))) + y;
            this.mXO = xo;
            this.mYO = yo;
            this.mPolar = polar;
            this.mPolar.y *= this.mWarpFactor;
            float f = this.f37mX;
            float f2 = this.mMargin;
            if (f < f2) {
                this.f37mX = f2;
            }
            if (this.f37mX + this.mXO > ((float) this.mSettings.getScreenWidth()) - this.mMargin) {
                this.f37mX = (((float) this.mSettings.getScreenWidth()) - this.mXO) - this.mMargin;
            }
            float f3 = this.f38mY;
            float f4 = this.mMargin;
            if (f3 < f4) {
                this.f38mY = f4;
            }
            if (this.f38mY + this.mYO > ((float) this.mSettings.getScreenHeight()) - this.mMargin) {
                this.f38mY = (((float) this.mSettings.getScreenHeight()) - this.mYO) - this.mMargin;
            }
            double d = (double) this.mRadius;
            double d2 = (double) this.mRadiusInc;
            Double.isNaN(d2);
            Double.isNaN(d);
            if (((double) polar.y) < d + (d2 * 0.9d)) {
                this.mActivated = false;
            }
        }
        return this.mActivated;
    }

    @Override // com.noname81.lmt.PieMenu.PieView
    public void onShrink() {
        PointF pointF;
        if (this.mActivated && (pointF = this.mPolar) != null && pointF.y > ((float) (this.mRadius + (this.mRadiusInc * this.mLevels)))) {
            new Handler().postDelayed(new Runnable() {
                /* class com.noname81.lmt.PiePointer.RunnableC05301 */

                public void run() {
                    PiePointer.this.sendTap();
                }
            }, 50);
        }
        this.mActivated = false;
        this.mPolar = null;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void sendTap() {
        if (this.mRootContext.isRootAvailable(false)) {
            this.mRootContext.runCommandRemote("input tap " + ((int) (this.f37mX + this.mXO)) + " " + ((int) (this.f38mY + this.mYO)), true);
        } else if (AccessibilityHandler.isAccessibilityAvailable(this.mContext, true)) {
            AccessibilityHandler.performClick(this.f37mX + this.mXO, this.f38mY + this.mYO);
        }
    }
}