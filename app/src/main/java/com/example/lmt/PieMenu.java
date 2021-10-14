package com.example.lmt;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Handler;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import java.util.ArrayList;
import java.util.List;

/* access modifiers changed from: package-private */
public class PieMenu extends FrameLayout {
    private static boolean DEBUG = false;
    private static final int MAX_LEVELS = 5;
    private static final String TAG = "LMT::PieMenu";
    private int mAnimationTime;
    private Point mCenter;
    private int[] mCounts;
    private PieItem mCurrentItem;
    private boolean mExpandTriggerArea;
    private int mIconColor;
    private List<PieItem> mItems;
    private PieItem mLastItem;
    private int mLevels;
    private boolean mLongpress;
    private Handler mLongpressHandler;
    private Runnable mLongpressRunnable;
    private int mNormalGradient;
    private Paint mNormalPaint;
    private boolean mOpen;
    private int mOutlineGradient;
    private Paint mOutlinePaint;
    private PieView mPiePointer;
    private int mPointerColor;
    private int mRadius;
    private int mRadiusInc;
    private boolean mRotateImages;
    private Paint mSelectedPaint;
    private SettingsValues mSettings;
    private Paint mShiftPaint;
    private int mSlop;
    private PieView mStatusInfo;
    private int mStatusInfoClockColor;
    private int mStatusInfoDimColor;
    private int mStatusInfoNotificationsColor;
    private Toaster mToaster;
    private int mTouchOffset;
    private Rect mWindow;

    public interface PieView {
        void activate();

        boolean drawBackground(Canvas canvas);

        boolean drawForeground(Canvas canvas);

        void layout(boolean z, Point point, int i, int i2, int i3, boolean z2, boolean z3, boolean z4);

        void onShrink();

        boolean onTouchEvent(int i, float f, float f2, float f3, float f4, PointF pointF);
    }

    public PieMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public PieMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PieMenu(Context context) {
        super(context);
        init(context);
    }

    static void setDebug(boolean debug) {
        DEBUG = debug;
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    private void init(Context context) {
        char c;
        this.mSettings = SettingsValues.getInstance(context);
        this.mToaster = Toaster.getInstance(context);
        this.mLongpressHandler = new Handler();
        this.mLongpressRunnable = null;
        this.mLongpress = false;
        this.mItems = new ArrayList();
        this.mLevels = 0;
        this.mCounts = new int[5];
        Resources res = context.getResources();
        this.mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) this.mSettings.loadPieInnerRadius(), res.getDisplayMetrics());
        this.mRadiusInc = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) this.mSettings.loadPieOuterRadius(), res.getDisplayMetrics());
        this.mSlop = this.mSettings.loadPieAreaX();
        this.mTouchOffset = (int) res.getDimension(R.dimen.qc_touch_offset);
        this.mRotateImages = this.mSettings.loadPieRotateImages() == 1;
        this.mExpandTriggerArea = this.mSettings.loadPieExpandTriggerArea() == 1;
        this.mOpen = false;
        setWillNotDraw(false);
        setDrawingCacheEnabled(false);
        this.mWindow = new Rect();
        this.mCenter = new Point(0, 0);
        this.mNormalPaint = new Paint();
        this.mNormalPaint.setAntiAlias(true);
        this.mSelectedPaint = new Paint();
        this.mSelectedPaint.setAntiAlias(true);
        this.mShiftPaint = new Paint();
        this.mShiftPaint.setAntiAlias(true);
        this.mShiftPaint.setStyle(Paint.Style.STROKE);
        this.mShiftPaint.setStrokeWidth((float) this.mSettings.loadPieShiftSize());
        this.mOutlinePaint = new Paint();
        this.mOutlinePaint.setAntiAlias(true);
        this.mOutlinePaint.setStyle(Paint.Style.STROKE);
        this.mOutlinePaint.setStrokeWidth((float) this.mSettings.loadPieOutlineSize());
        this.mAnimationTime = this.mSettings.loadPieAnimation();
        String colorString = this.mSettings.loadPieColor();
        switch (colorString.hashCode()) {
            case 48:
                if (colorString.equals("0")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 49:
                if (colorString.equals("1")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 50:
                if (colorString.equals("2")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 51:
                if (colorString.equals("3")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 52:
                if (colorString.equals("4")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 53:
                if (colorString.equals("5")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 54:
                if (colorString.equals("6")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 55:
                if (colorString.equals("7")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                this.mNormalPaint.setColor(res.getColor(R.color.qc_normal_blue));
                this.mSelectedPaint.setColor(res.getColor(R.color.qc_selected_blue));
                this.mShiftPaint.setColor(res.getColor(R.color.qc_selected_blue));
                this.mOutlinePaint.setColor(res.getColor(R.color.qc_selected_blue));
                this.mOutlinePaint.setAlpha(0);
                this.mIconColor = -1;
                this.mNormalGradient = -2;
                this.mOutlineGradient = -2;
                break;
            case 1:
                this.mNormalPaint.setColor(res.getColor(R.color.qc_normal_red));
                this.mSelectedPaint.setColor(res.getColor(R.color.qc_selected_red));
                this.mShiftPaint.setColor(res.getColor(R.color.qc_selected_red));
                this.mOutlinePaint.setColor(res.getColor(R.color.qc_selected_white));
                this.mIconColor = -1;
                this.mNormalGradient = -2;
                this.mOutlineGradient = -2;
                break;
            case 2:
                this.mNormalPaint.setColor(res.getColor(R.color.qc_normal_grey));
                this.mSelectedPaint.setColor(res.getColor(R.color.qc_selected_grey));
                this.mShiftPaint.setColor(res.getColor(R.color.qc_selected_grey));
                this.mOutlinePaint.setColor(res.getColor(R.color.qc_selected_white));
                this.mNormalGradient = -2;
                this.mOutlineGradient = -2;
                break;
            case 3:
                this.mNormalPaint.setColor(res.getColor(R.color.qc_normal_transparent));
                this.mSelectedPaint.setColor(res.getColor(R.color.qc_selected_transparent));
                this.mShiftPaint.setColor(res.getColor(R.color.qc_selected_transparent));
                this.mOutlinePaint.setAlpha(0);
                this.mIconColor = -1;
                this.mNormalGradient = -2;
                this.mOutlineGradient = -2;
                break;
            case 4:
                this.mNormalPaint.setColor(res.getColor(R.color.qc_normal_white));
                this.mNormalPaint.setShadowLayer(5.0f, 0.0f, 0.0f, -12303292);
                this.mSelectedPaint.setColor(res.getColor(R.color.qc_selected_white));
                this.mSelectedPaint.setShadowLayer(5.0f, 0.0f, 0.0f, -3355444);
                this.mShiftPaint.setColor(res.getColor(R.color.qc_selected_white));
                this.mOutlinePaint.setAlpha(0);
                this.mIconColor = -12303292;
                this.mNormalGradient = -2;
                this.mOutlineGradient = -2;
                break;
            case 5:
                this.mNormalPaint.setColor(Color.rgb(190, 35, 90));
                this.mSelectedPaint.setColor(Color.rgb(255, 242, 30));
                this.mShiftPaint.setColor(Color.rgb(255, 242, 30));
                this.mOutlinePaint.setColor(res.getColor(R.color.qc_selected_white));
                this.mIconColor = -1;
                this.mNormalGradient = -2;
                this.mOutlineGradient = -2;
                break;
            case 6:
                this.mNormalPaint.setColor(Color.argb(127, 0, 0, 0));
                this.mSelectedPaint.setColor(Color.argb(64, 0, 255, 0));
                this.mShiftPaint.setColor(Color.argb(64, 0, 255, 0));
                this.mOutlinePaint.setColor(Color.argb(127, 0, 255, 0));
                this.mIconColor = Color.argb((int) ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 0, 255, 0);
                this.mNormalGradient = -2;
                this.mOutlineGradient = -2;
                break;
            case 7:
                this.mNormalPaint.setColor(Color.argb(127, 0, 0, 0));
                this.mSelectedPaint.setColor(Color.argb(64, 255, 0, 0));
                this.mShiftPaint.setColor(Color.argb(64, 255, 0, 0));
                this.mOutlinePaint.setColor(Color.argb(127, 255, 0, 0));
                this.mIconColor = Color.argb((int) ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 255, 0, 0);
                this.mNormalGradient = -2;
                this.mOutlineGradient = -2;
                break;
            default:
                String[] colorStrings = colorString.replace(" ", BuildConfig.FLAVOR).split(",");
                if (colorStrings.length <= 0 || colorStrings[0].length() == 0) {
                    this.mNormalPaint.setColor(res.getColor(R.color.qc_normal_blue));
                } else {
                    this.mNormalPaint.setColor(Color.parseColor(colorStrings[0]));
                }
                if (colorStrings.length <= 1 || colorStrings[1].length() == 0) {
                    float[] hsv = new float[3];
                    Color.colorToHSV(this.mNormalPaint.getColor(), hsv);
                    hsv[2] = hsv[2] * 0.8f;
                    this.mSelectedPaint.setColor(Color.HSVToColor(hsv));
                } else {
                    this.mSelectedPaint.setColor(Color.parseColor(colorStrings[1]));
                }
                if (colorStrings.length <= 2 || colorStrings[2].length() == 0) {
                    this.mIconColor = -1;
                } else {
                    this.mIconColor = Color.parseColor(colorStrings[2]);
                }
                if (colorStrings.length <= 3 || colorStrings[3].length() == 0) {
                    this.mOutlinePaint.setAlpha(0);
                } else {
                    this.mOutlinePaint.setColor(Color.parseColor(colorStrings[3]));
                }
                if (colorStrings.length <= 4 || colorStrings[4].length() == 0) {
                    this.mNormalGradient = -2;
                } else {
                    this.mNormalGradient = Color.parseColor(colorStrings[4]);
                }
                if (colorStrings.length <= 5 || colorStrings[5].length() == 0) {
                    this.mOutlineGradient = -2;
                } else {
                    this.mOutlineGradient = Color.parseColor(colorStrings[5]);
                }
                if (colorStrings.length <= 6 || colorStrings[6].length() == 0) {
                    this.mShiftPaint.setColor(res.getColor(R.color.qc_selected_blue));
                } else {
                    this.mShiftPaint.setColor(Color.parseColor(colorStrings[6]));
                }
                break;
        }
        String colorStatusInfoString = this.mSettings.loadPieStatusInfoColor();
        if (colorStatusInfoString.equals("0")) {
            this.mStatusInfoClockColor = -1;
            this.mStatusInfoNotificationsColor = -1;
            this.mStatusInfoDimColor = manipulateColor(this.mNormalPaint.getColor(), 0.2f);
        } else {
            try {
                String[] colorStatusInfoStrings = colorStatusInfoString.replace(" ", BuildConfig.FLAVOR).split(",");
                if (colorStatusInfoStrings.length <= 0 || colorStatusInfoStrings[0].length() == 0) {
                    this.mStatusInfoClockColor = -1;
                } else {
                    this.mStatusInfoClockColor = Color.parseColor(colorStatusInfoStrings[0]);
                }
                if (colorStatusInfoStrings.length <= 1 || colorStatusInfoStrings[1].length() == 0) {
                    this.mStatusInfoNotificationsColor = -1;
                } else {
                    this.mStatusInfoNotificationsColor = Color.parseColor(colorStatusInfoStrings[1]);
                }
                if (colorStatusInfoStrings.length <= 2 || colorStatusInfoStrings[2].length() == 0) {
                    this.mStatusInfoDimColor = Color.argb((int) ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 0, 0, 0);
                } else {
                    this.mStatusInfoDimColor = Color.parseColor(colorStatusInfoStrings[2]);
                }
            } catch (Exception e) {
                this.mStatusInfoClockColor = -1;
                this.mStatusInfoNotificationsColor = -1;
                this.mStatusInfoDimColor = Color.argb((int) ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 0, 0, 0);
            }
        }
        String colorPointerString = this.mSettings.loadPiePointerColor();
        if (colorPointerString.equals("0")) {
            this.mPointerColor = res.getColor(R.color.qc_normal_blue);
        } else {
            try {
                String[] colorPointerStrings = colorPointerString.replace(" ", BuildConfig.FLAVOR).split(",");
                if (colorPointerStrings.length <= 0 || colorPointerStrings[0].length() == 0) {
                    this.mPointerColor = res.getColor(R.color.qc_normal_blue);
                } else {
                    this.mPointerColor = Color.parseColor(colorPointerStrings[0]);
                }
            } catch (Exception e2) {
                this.mPointerColor = res.getColor(R.color.qc_normal_blue);
            }
        }
        this.mPiePointer = new PiePointer(this, context, this.mPointerColor);
        if (this.mSettings.loadPieShowStatusInfos() > 0) {
            this.mStatusInfo = new PieStatusInfo(this, context, this.mStatusInfoClockColor, this.mStatusInfoNotificationsColor, this.mSelectedPaint.getColor(), this.mStatusInfoDimColor);
        }
    }

    public void addItem(PieItem item) {
        this.mItems.add(item);
        int l = item.getLevel();
        this.mLevels = Math.max(this.mLevels, l);
        int[] iArr = this.mCounts;
        iArr[l] = iArr[l] + 1;
    }

    public void removeItem(PieItem item) {
        this.mItems.remove(item);
    }

    public void activatePiePointer() {
        this.mPiePointer.activate();
    }

    public void clearItems() {
        this.mItems.clear();
        this.mLevels = 0;
        for (int i = 0; i < 5; i++) {
            this.mCounts[i] = 0;
        }
    }

    public void relayoutPie() {
        show(true, true);
    }

    private boolean onTheLeft() {
        return this.mCenter.x - this.mWindow.left <= this.mSlop;
    }

    private boolean onTheRight() {
        return this.mCenter.x >= (this.mWindow.right - this.mWindow.left) - this.mSlop;
    }

    private boolean onTheBottom() {
        return !onTheLeft() && !onTheRight() && this.mCenter.y >= (this.mWindow.bottom - this.mWindow.top) - this.mSlop;
    }

    private boolean isValidPosition(int x, int y) {
        return onTheLeft() || onTheRight() || onTheBottom();
    }

    public void show(boolean show, boolean relayout) {
        if (show) {
            animateOpen();
            layoutPie();
            PieView pieView = this.mPiePointer;
            if (pieView != null) {
                pieView.layout(true, this.mCenter, this.mRadius, this.mRadiusInc, this.mLevels, onTheLeft(), onTheBottom(), relayout);
            }
            PieView pieView2 = this.mStatusInfo;
            if (pieView2 != null) {
                pieView2.layout(true, this.mCenter, this.mRadius, this.mRadiusInc, this.mLevels, onTheLeft(), onTheBottom(), relayout);
            }
        } else {
            this.mCurrentItem = null;
            this.mLastItem = null;
            PieView pieView3 = this.mPiePointer;
            if (pieView3 != null) {
                pieView3.layout(false, this.mCenter, this.mRadius, this.mRadiusInc, this.mLevels, onTheLeft(), onTheBottom(), relayout);
            }
            PieView pieView4 = this.mStatusInfo;
            if (pieView4 != null) {
                pieView4.layout(false, this.mCenter, this.mRadius, this.mRadiusInc, this.mLevels, onTheLeft(), onTheBottom(), relayout);
            }
        }
        this.mOpen = show;
        invalidate();
    }

    private void animateOpen() {
        ValueAnimator anim = ValueAnimator.ofFloat(0.0f, 1.0f);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.noname81.lmt.PieMenu.C05291 */

            public void onAnimationUpdate(ValueAnimator animation) {
                for (PieItem item : PieMenu.this.mItems) {
                    item.setAnimationAngle((1.0f - animation.getAnimatedFraction()) * (-item.getStart()));
                    item.setAlpha((int) (animation.getAnimatedFraction() * 255.0f));
                }
                PieMenu.this.invalidate();
            }
        });
        anim.setDuration((long) (this.mAnimationTime * 2));
        anim.start();
    }

    private void setCenter(int x, int y) {
        Point point = this.mCenter;
        point.x = x;
        point.y = y;
        if (onTheLeft()) {
            this.mCenter.x = 0;
        } else if (onTheRight()) {
            this.mCenter.x = this.mWindow.right - this.mWindow.left;
        } else if (onTheBottom()) {
            this.mCenter.y = this.mWindow.bottom - this.mWindow.top;
            if (!(this.mSettings.loadPiePos() == 3 || this.mSettings.loadPiePos() == 8)) {
                this.mCenter.x = (this.mWindow.right - this.mWindow.left) / 2;
            }
        }
        if (DEBUG) {
            Log.d(TAG, "setCenter " + this.mCenter.x + "," + this.mCenter.y + " inx:" + x + " iny:" + y);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void selectImages(int index) {
        for (int i = 0; i < this.mItems.size(); i++) {
            this.mItems.get(i).selectImage(index);
        }
    }

    private void fireClick(PieItem item) {
        if (this.mSettings.loadPieVibrate() >= 3) {
            this.mToaster.vibratePie();
        }
        if (item != null && item.getView() != null) {
            if (this.mLongpress) {
                item.getView().performLongClick();
            } else {
                item.getView().performClick();
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void fireHover(PieItem item) {
        if (item != null && item.getView() != null) {
            if (this.mLongpress) {
                item.getView().dispatchKeyEvent(new KeyEvent(0, 40));
            } else {
                item.getView().dispatchKeyEvent(new KeyEvent(0, 36));
            }
        }
    }

    private void setLongpressTimer(PointF polar) {
        if (polar == null || ((float) (this.mRadius - this.mTouchOffset)) > polar.y) {
            Runnable runnable = this.mLongpressRunnable;
            if (runnable != null) {
                this.mLongpressHandler.removeCallbacks(runnable);
                this.mLongpressRunnable = null;
                this.mLongpress = false;
                selectImages(0);
                postInvalidate();
            }
        } else if (this.mLongpressRunnable == null) {
            this.mLongpressRunnable = new Runnable() {
                /* class com.noname81.lmt.PieMenu.AnonymousClass1LongpressRunnable */

                public void run() {
                    if (PieMenu.this.mSettings.loadPieVibrate() > 0) {
                        PieMenu.this.mToaster.vibratePie();
                    }
                    PieMenu.this.mLongpress = true;
                    PieMenu pieMenu = PieMenu.this;
                    pieMenu.fireHover(pieMenu.mCurrentItem);
                    PieMenu.this.selectImages(1);
                    PieMenu.this.postInvalidate();
                }
            };
            this.mLongpressHandler.postDelayed(this.mLongpressRunnable, (long) this.mSettings.loadPieLongpress());
        }
    }

    private void layoutPie() {
        int outer;
        int i;
        int rgap;
        float gap;
        float emptyangle;
        float emptyangleInitial;
        int x;
        float emptyangleInitial2 = ((float) this.mSettings.loadPieStartGap()) / 100.0f;
        float emptyangle2 = emptyangleInitial2;
        if (onTheBottom()) {
            emptyangle2 -= 2.8584073f;
        }
        float emptyangle3 = emptyangle2;
        float gap2 = ((float) this.mSettings.loadPieSliceGap()) / 10.0f;
        int rgap2 = (int) (5.0f * gap2);
        int i2 = this.mRadius;
        int outer2 = (i2 + this.mRadiusInc) - rgap2;
        int inner = i2 + rgap2;
        int i3 = 0;
        while (i3 < this.mLevels) {
            int level = i3 + 1;
            double d = (double) (emptyangleInitial2 * 2.0f);
            Double.isNaN(d);
            float sweep = ((float) (3.141592653589793d - d)) / ((float) this.mCounts[level]);
            float angle = emptyangle3 + (sweep / 2.0f);
            Path slice = makeSlice(getDegrees(0.0d), getDegrees((double) sweep), outer2, inner, this.mCenter, gap2);
            float angle2 = angle;
            for (PieItem item : this.mItems) {
                if (item.getLevel() == level) {
                    View view = item.getView();
                    if (view != null) {
                        view.measure(view.getLayoutParams().width, view.getLayoutParams().height);
                        int w = view.getMeasuredWidth();
                        int h = view.getMeasuredHeight();
                        int r = (((outer2 - inner) * 2) / 3) + inner;
                        emptyangleInitial = emptyangleInitial2;
                        emptyangle = emptyangle3;
                        double d2 = (double) r;
                        gap = gap2;
                        rgap = rgap2;
                        double sin = Math.sin((double) angle2);
                        Double.isNaN(d2);
                        int x2 = (int) (d2 * sin);
                        int i4 = this.mCenter.y;
                        double d3 = (double) r;
                        i = i3;
                        outer = outer2;
                        double cos = Math.cos((double) angle2);
                        Double.isNaN(d3);
                        int y = (i4 - ((int) (d3 * cos))) - (h / 2);
                        if (onTheLeft()) {
                            x = (this.mCenter.x + x2) - (w / 2);
                        } else {
                            x = (this.mCenter.x - x2) - (w / 2);
                        }
                        view.layout(x, y, x + w, y + h);
                    } else {
                        emptyangleInitial = emptyangleInitial2;
                        emptyangle = emptyangle3;
                        gap = gap2;
                        rgap = rgap2;
                        i = i3;
                        outer = outer2;
                    }
                    item.setGeometry(angle2 - (sweep / 2.0f), sweep, inner, outer, slice);
                    item.setColor(this.mIconColor);
                    angle2 += sweep;
                } else {
                    emptyangleInitial = emptyangleInitial2;
                    emptyangle = emptyangle3;
                    gap = gap2;
                    rgap = rgap2;
                    i = i3;
                    outer = outer2;
                }
                emptyangleInitial2 = emptyangleInitial;
                emptyangle3 = emptyangle;
                gap2 = gap;
                rgap2 = rgap;
                i3 = i;
                outer2 = outer;
            }
            int i5 = this.mRadiusInc;
            inner += i5;
            outer2 += i5;
            i3++;
            rgap2 = rgap2;
        }
        if (this.mNormalGradient != -2) {
            this.mNormalPaint.setShader(new RadialGradient((float) this.mCenter.x, (float) this.mCenter.y, (float) (this.mRadius + (this.mRadiusInc * this.mLevels)), this.mNormalPaint.getColor(), this.mNormalGradient, Shader.TileMode.CLAMP));
        }
        if (this.mOutlineGradient != -2) {
            this.mOutlinePaint.setShader(new RadialGradient((float) this.mCenter.x, (float) this.mCenter.y, (float) (this.mRadius + (this.mRadiusInc * this.mLevels)), this.mOutlinePaint.getColor(), this.mOutlineGradient, Shader.TileMode.CLAMP));
        }
    }

    private float getDegrees(double angle) {
        return (float) (270.0d - ((180.0d * angle) / 3.141592653589793d));
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        PieView pieView;
        PieView pieView2;
        if (this.mOpen) {
            boolean handled = false;
            PieView pieView3 = this.mPiePointer;
            if (pieView3 != null) {
                handled = pieView3.drawBackground(canvas);
            }
            if (!handled && (pieView2 = this.mStatusInfo) != null) {
                handled = pieView2.drawBackground(canvas);
            }
            if (!handled) {
                for (PieItem item : this.mItems) {
                    int state = canvas.save();
                    if (onTheLeft()) {
                        canvas.scale(-1.0f, 1.0f);
                    }
                    canvas.rotate(getDegrees((double) item.getStartAngle()) - 270.0f, (float) this.mCenter.x, (float) this.mCenter.y);
                    drawPath(canvas, item.getPath(), item.isSelected());
                    canvas.restoreToCount(state);
                    drawItem(canvas, item);
                }
            }
            boolean handled2 = false;
            PieView pieView4 = this.mPiePointer;
            if (pieView4 != null) {
                handled2 = pieView4.drawForeground(canvas);
            }
            if (!(handled2 || (pieView = this.mStatusInfo) == null)) {
                pieView.drawForeground(canvas);
            }
        }
    }

    private void drawItem(Canvas canvas, PieItem item) {
        View view = item.getView();
        int state = canvas.save();
        if (this.mRotateImages) {
            float r = getDegrees((double) (item.getStartAngle() + (item.getSweep() / 2.0f))) - 270.0f;
            canvas.translate(((float) view.getLeft()) + (((float) view.getWidth()) / 2.0f), ((float) view.getTop()) + (((float) view.getHeight()) / 2.0f));
            canvas.rotate(onTheLeft() ? -r : r);
            canvas.translate(((float) (-view.getWidth())) / 2.0f, ((float) (-view.getHeight())) / 2.0f);
        } else {
            canvas.translate((float) view.getLeft(), (float) view.getTop());
        }
        view.draw(canvas);
        canvas.restoreToCount(state);
    }

    private void drawPath(Canvas canvas, Path path, boolean selected) {
        float shift = this.mShiftPaint.getStrokeWidth();
        if (shift > 0.0f) {
            canvas.drawPath(path, this.mShiftPaint);
            canvas.translate((-shift) / 2.0f, (-shift) / 2.0f);
        }
        canvas.drawPath(path, selected ? this.mSelectedPaint : this.mNormalPaint);
        if (this.mOutlinePaint.getAlpha() > 0) {
            canvas.drawPath(path, this.mOutlinePaint);
        }
    }

    private Path makeSlice(float start, float end, int outer, int inner, Point center, float gap) {
        RectF bb = new RectF((float) (center.x - outer), (float) (center.y - outer), (float) (center.x + outer), (float) (center.y + outer));
        RectF bbi = new RectF((float) (center.x - inner), (float) (center.y - inner), (float) (center.x + inner), (float) (center.y + inner));
        Path path = new Path();
        float start2 = start - gap;
        float end2 = end + gap;
        path.arcTo(bb, start2, end2 - start2, true);
        path.arcTo(bbi, end2, start2 - end2);
        path.close();
        return path;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        PieView pieView;
        PieView pieView2;
        getWindowVisibleDisplayFrame(this.mWindow);
        if (getHeight() > this.mWindow.bottom - this.mWindow.top) {
            this.mWindow.top = 0;
        }
        if (getWidth() > this.mWindow.right - this.mWindow.left) {
            this.mWindow.left = 0;
        }
        float x = ev.getRawX() - ((float) this.mWindow.left);
        float y = ev.getRawY() - ((float) this.mWindow.top);
        if (DEBUG) {
            Log.d(TAG, "onTouchEvent patched:" + x + "," + y + " raw:" + ev.getRawX() + "," + ev.getRawY() + " window:" + this.mWindow);
        }
        int action = ev.getActionMasked();
        if (action == 0) {
            setCenter((int) x, (int) y);
            if (isValidPosition((int) x, (int) y)) {
                show(true, false);
                return true;
            }
        } else if (1 == action) {
            if (this.mOpen) {
                boolean handled = false;
                PointF polar = getPolar(x, y);
                PieView pieView3 = this.mPiePointer;
                if (pieView3 != null) {
                    handled = pieView3.onTouchEvent(action, x, y, (float) this.mWindow.left, (float) this.mWindow.top, polar);
                }
                if (!handled && (pieView2 = this.mStatusInfo) != null) {
                    pieView2.onTouchEvent(action, x, y, (float) this.mWindow.left, (float) this.mWindow.top, polar);
                }
                PieItem item = this.mCurrentItem;
                deselect();
                show(false, false);
                if (!handled && item != null) {
                    fireClick(item);
                }
                setLongpressTimer(null);
                return true;
            }
        } else if (3 == action) {
            if (this.mOpen) {
                show(false, false);
                deselect();
                return false;
            }
        } else if (2 == action && this.mOpen) {
            boolean handled2 = false;
            PointF polar2 = getPolar(x, y);
            PieView pieView4 = this.mPiePointer;
            if (pieView4 != null) {
                handled2 = pieView4.onTouchEvent(action, x, y, (float) this.mWindow.left, (float) this.mWindow.top, polar2);
            }
            if (!handled2 && (pieView = this.mStatusInfo) != null) {
                handled2 = pieView.onTouchEvent(action, x, y, (float) this.mWindow.left, (float) this.mWindow.top, polar2);
            }
            if (handled2) {
                deselect();
                setLongpressTimer(null);
                invalidate();
            } else {
                setLongpressTimer(polar2);
                PieItem item2 = findItem(polar2, this.mExpandTriggerArea);
                if (this.mCurrentItem != item2) {
                    onEnter(item2);
                    invalidate();
                }
            }
            return true;
        }
        return false;
    }

    public void onShrink() {
        PieView pieView = this.mPiePointer;
        if (pieView != null) {
            pieView.onShrink();
        }
        PieView pieView2 = this.mStatusInfo;
        if (pieView2 != null) {
            pieView2.onShrink();
        }
    }

    private void onEnter(PieItem item) {
        PieItem pieItem = this.mCurrentItem;
        if (pieItem != null) {
            pieItem.setSelected(false);
            this.mLastItem = this.mCurrentItem;
        }
        if (item != null) {
            if (this.mSettings.loadPieVibrate() == 2 || this.mSettings.loadPieVibrate() >= 4) {
                this.mToaster.vibratePie();
            }
            playSoundEffect(0);
            item.setSelected(true);
            fireHover(item);
        }
        if (this.mLastItem == item && this.mSettings.loadPieMultiCommand() > 0) {
            fireClick(item);
        }
        this.mCurrentItem = item;
    }

    private void deselect() {
        PieItem pieItem = this.mCurrentItem;
        if (pieItem != null) {
            pieItem.setSelected(false);
        }
        this.mCurrentItem = null;
    }

    private PointF getPolar(float x, float y) {
        PointF res = new PointF();
        res.x = 1.5707964f;
        float x2 = ((float) this.mCenter.x) - x;
        if (this.mCenter.x < this.mSlop) {
            x2 = -x2;
        }
        float y2 = ((float) this.mCenter.y) - y;
        res.y = (float) Math.sqrt((double) ((x2 * x2) + (y2 * y2)));
        if (y2 > 0.0f) {
            res.x = (float) Math.asin((double) (x2 / res.y));
        } else if (y2 < 0.0f) {
            res.x = (float) (3.141592653589793d - Math.asin((double) (x2 / res.y)));
        }
        return res;
    }

    private PieItem findItem(PointF polar, boolean expandTriggerArea) {
        for (PieItem item : this.mItems) {
            if (item.getLevel() != this.mLevels || !expandTriggerArea) {
                if (((float) (item.getInnerRadius() - this.mTouchOffset)) < polar.y && ((float) (item.getOuterRadius() - this.mTouchOffset)) > polar.y && item.getStartAngle() < polar.x && item.getStartAngle() + item.getSweep() > polar.x) {
                    return item;
                }
            } else if (((float) (item.getInnerRadius() - this.mTouchOffset)) < polar.y && item.getStartAngle() < polar.x && item.getStartAngle() + item.getSweep() > polar.x) {
                return item;
            }
        }
        return null;
    }

    public static int manipulateColor(int color, float factor) {
        return Color.argb(Color.alpha(color), Math.min(Math.round(((float) Color.red(color)) * factor), 255), Math.min(Math.round(((float) Color.green(color)) * factor), 255), Math.min(Math.round(((float) Color.blue(color)) * factor), 255));
    }
}