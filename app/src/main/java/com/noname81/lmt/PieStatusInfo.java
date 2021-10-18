package com.noname81.lmt;

import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.format.DateFormat;
import android.util.TypedValue;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import com.noname81.lmt.PieMenu;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class PieStatusInfo implements SensorEventListener, PieMenu.PieView {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int AIContentInvalid = 0;
    private static final int AIContentOverview = 1;
    private static final int AINotification1 = 2;
    private static final int AINotification2 = 3;
    private static final int AINotification3 = 4;
    private static final int AINotification4 = 5;
    private static final int AINotification5 = 6;
    private int TextSizeBig = 65;
    private int TextSizeSmall = 13;
    private float mAIAnimationIn;
    private float mAIAnimationInOut;
    private boolean mAIAnimationInOutReversed;
    private ValueAnimator mAIAnimatorIn;
    private ValueAnimator mAIAnimatorOutIn;
    private int mAIContent;
    private Path mAIPath;
    private boolean mAISelected;
    private String[] mAIStrings;
    private Paint mAITextPaint;
    private int mAITextSize;
    private boolean mAnimationActive;
    private int mAnimationTime;
    private Timer mAnimationTimer;
    private Paint mBackgroundPaint;
    private int mBackroundAlpha;
    private Point mCenter;
    private float mClockAnimationIn;
    private ValueAnimator mClockAnimatorIn;
    private Path mClockPath;
    private boolean mClockSelected;
    private String[] mClockStrings;
    private Paint mClockTextPaint;
    private int mClockTextSizeBig;
    private int mClockTextSizeSmall;
    private int mColorClock;
    private int mColorNotifications;
    private int mColorSelected;
    private Context mContext;
    private String mDateFormat;
    private Launcher mLauncher;
    private int mLevels;
    private NotificationDataHelper mNotificationDataHelper;
    private boolean mOnTheBottom;
    private boolean mOnTheLeft;
    private FrameLayout mPieMenu;
    private int mPositionOffset;
    private int mRadius;
    private int mRadiusInc;
    private int mRadiusOffset;
    private RootContext mRootContext;
    private boolean mSensorActivated;
    private float mSensorAlpha;
    private float mSensorAnimation;
    private ValueAnimator mSensorAnimator;
    private float[] mSensorGravity;
    private long mSensorLastUpdate;
    private float[] mSensorLinearAcceleration;
    private SensorManager mSensorManager;
    private SettingsValues mSettings;
    private int mTwentyFour;

    PieStatusInfo(FrameLayout pieMenu, Context context, int colorClock, int colorNotifications, int colorSelected, int colorDim) {
        Typeface typeface;
        this.mPieMenu = pieMenu;
        this.mContext = context;
        this.mSettings = SettingsValues.getInstance(context);
        this.mNotificationDataHelper = NotificationDataHelper.getInstance();
        this.mLauncher = Launcher.getInstance(context);
        this.mRootContext = RootContext.getInstance(this.mContext);
        this.mColorClock = colorClock;
        this.mColorNotifications = colorNotifications;
        this.mColorSelected = colorSelected;
        this.mAnimationActive = true;
        this.mAnimationTime = this.mSettings.loadPieAnimation();
        this.mSensorActivated = this.mSettings.loadPieShowStatusInfos() == 3;
        this.mSensorManager = (SensorManager) this.mContext.getSystemService(Context.SENSOR_SERVICE);
        if (this.mAnimationActive) {
            this.mSensorAnimator = new ValueAnimator();
        }
        this.mSensorAnimation = 0.0f;
        this.mSensorAlpha = 0.8f;
        this.mSensorLinearAcceleration = new float[3];
        this.mSensorGravity = new float[3];
        Resources res = context.getResources();
        this.mRadiusOffset = (int) TypedValue.applyDimension(1, 10.0f, res.getDisplayMetrics());
        this.mPositionOffset = (int) TypedValue.applyDimension(1, 5.0f, res.getDisplayMetrics());
        int pieFont = this.mSettings.loadPieFont();
        if (pieFont < 3) {
            if (pieFont == 0) {
                this.TextSizeBig -= 20;
            }
            if (pieFont == 0) {
                this.TextSizeSmall -= 5;
            }
            if (pieFont == 2) {
                this.TextSizeBig += 15;
            }
            if (pieFont == 2) {
                this.TextSizeSmall += 4;
            }
            typeface = Typeface.createFromAsset(context.getAssets(), "Roboto-Thin.ttf");
        } else if (pieFont < 6) {
            if (pieFont == 3) {
                this.TextSizeBig -= 20;
            }
            if (pieFont == 3) {
                this.TextSizeSmall -= 5;
            }
            if (pieFont == 5) {
                this.TextSizeBig += 15;
            }
            if (pieFont == 5) {
                this.TextSizeSmall += 4;
            }
            typeface = Typeface.createFromAsset(context.getAssets(), "Roboto-Light.ttf");
        } else {
            if (pieFont == 6) {
                this.TextSizeBig -= 20;
            }
            if (pieFont == 6) {
                this.TextSizeSmall -= 5;
            }
            if (pieFont == 8) {
                this.TextSizeBig += 15;
            }
            if (pieFont == 8) {
                this.TextSizeSmall += 4;
            }
            typeface = Typeface.DEFAULT;
        }
        this.mClockPath = new Path();
        if (this.mAnimationActive) {
            this.mClockAnimatorIn = new ValueAnimator();
        }
        this.mClockAnimationIn = 0.0f;
        this.mClockTextSizeBig = (int) TypedValue.applyDimension(1, (float) this.TextSizeBig, res.getDisplayMetrics());
        this.mClockTextSizeSmall = (int) TypedValue.applyDimension(1, (float) this.TextSizeSmall, res.getDisplayMetrics());
        this.mClockTextPaint = new Paint();
        this.mClockTextPaint.setColor(this.mColorClock);
        this.mClockTextPaint.setAlpha(0);
        this.mClockTextPaint.setAntiAlias(true);
        this.mClockTextPaint.setTypeface(typeface);
        this.mClockTextPaint.setTextAlign(Paint.Align.RIGHT);
        this.mClockStrings = new String[2];
        this.mClockSelected = false;
        this.mAIPath = new Path();
        if (this.mAnimationActive) {
            this.mAIAnimatorIn = new ValueAnimator();
        }
        this.mAIAnimationIn = 0.0f;
        if (this.mAnimationActive) {
            this.mAIAnimatorOutIn = new ValueAnimator();
        }
        this.mAIAnimationInOut = 0.0f;
        this.mAIAnimationInOutReversed = false;
        this.mAITextSize = (int) TypedValue.applyDimension(1, (float) this.TextSizeSmall, res.getDisplayMetrics());
        this.mAITextPaint = new Paint();
        this.mAITextPaint.setColor(this.mColorNotifications);
        this.mAITextPaint.setAlpha(0);
        this.mAITextPaint.setAntiAlias(true);
        this.mAITextPaint.setTypeface(typeface);
        this.mAITextPaint.setTextAlign(Paint.Align.LEFT);
        this.mAITextPaint.setTextSize((float) this.mAITextSize);
        this.mAIStrings = new String[3];
        this.mAIContent = 0;
        this.mAISelected = false;
        this.mBackgroundPaint = new Paint();
        this.mBackgroundPaint.setColor(colorDim);
        this.mBackgroundPaint.setAlpha(0);
        this.mBackroundAlpha = Color.alpha(colorDim);
        this.mTwentyFour = DateFormat.is24HourFormat(this.mContext) ? 24 : 12;
        this.mDateFormat = new String(DateFormat.getDateFormatOrder(this.mContext));
    }

    @Override // com.noname81.lmt.PieMenu.PieView
    public void activate() {
    }

    @Override // com.noname81.lmt.PieMenu.PieView
    public void layout(boolean activate, Point center, int radius, int radiusInc, int levels, boolean onTheLeft, boolean onTheBootom, boolean relayout) {
        Timer timer;
        if (this.mAnimationActive) {
            this.mClockAnimatorIn.cancel();
        }
        float f = 0.0f;
        this.mClockAnimationIn = 0.0f;
        this.mClockTextPaint.setAlpha(0);
        if (this.mAnimationActive) {
            this.mAIAnimatorIn.cancel();
        }
        this.mAIAnimationIn = 0.0f;
        if (this.mAnimationActive) {
            this.mAIAnimatorOutIn.cancel();
        }
        this.mAIAnimationInOut = 0.0f;
        this.mAIAnimationInOutReversed = false;
        this.mAITextPaint.setAlpha(0);
        this.mAIContent = 0;
        this.mBackgroundPaint.setAlpha(0);
        if (!this.mAnimationActive && (timer = this.mAnimationTimer) != null) {
            timer.cancel();
        }
        handleSensorListener(activate);
        if (activate) {
            this.mCenter = center;
            this.mRadius = radius;
            this.mRadiusInc = radiusInc;
            this.mLevels = levels;
            this.mOnTheLeft = onTheLeft;
            this.mOnTheBottom = onTheBootom;
            if (this.mSettings.loadPieShowStatusInfos() == 2) {
                f = 1.0f;
            }
            makePath(f);
            fillClockStrings();
            fillAdditionalInfoStrings();
            if (!this.mAnimationActive || relayout) {
                this.mClockAnimationIn = 1.0f;
                this.mClockTextPaint.setAlpha(Color.alpha(this.mColorClock));
                this.mAIAnimationIn = 1.0f;
                this.mAITextPaint.setAlpha(Color.alpha(this.mColorNotifications));
                this.mBackgroundPaint.setAlpha(ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
                if (!relayout) {
                    TimerTask switchAdditionalInfos = new TimerTask() {
                        /* class com.noname81.lmt.PieStatusInfo.C05344 */

                        public void run() {
                            PieStatusInfo.this.fillAdditionalInfoStrings();
                            PieStatusInfo.this.mPieMenu.postInvalidate();
                        }
                    };
                    this.mAnimationTimer = new Timer();
                    this.mAnimationTimer.schedule(switchAdditionalInfos, 3000, 3000);
                    return;
                }
                return;
            }
            this.mClockAnimatorIn = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mClockAnimatorIn.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                /* class com.noname81.lmt.PieStatusInfo.C05311 */

                public void onAnimationUpdate(ValueAnimator animation) {
                    PieStatusInfo.this.mClockAnimationIn = animation.getAnimatedFraction();
                    PieStatusInfo.this.mClockTextPaint.setAlpha((int) (PieStatusInfo.this.mClockAnimationIn * ((float) Color.alpha(PieStatusInfo.this.mColorClock))));
                    PieStatusInfo.this.mBackgroundPaint.setAlpha((int) (PieStatusInfo.this.mClockAnimationIn * ((float) PieStatusInfo.this.mBackroundAlpha)));
                    PieStatusInfo.this.mPieMenu.invalidate();
                }
            });
            this.mClockAnimatorIn.setStartDelay((long) (this.mAnimationTime * 8));
            this.mClockAnimatorIn.setDuration((long) (this.mAnimationTime * 6));
            this.mClockAnimatorIn.setInterpolator(new DecelerateInterpolator());
            this.mClockAnimatorIn.start();
            this.mAIAnimatorIn = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mAIAnimatorIn.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                /* class com.noname81.lmt.PieStatusInfo.C05322 */

                public void onAnimationUpdate(ValueAnimator animation) {
                    PieStatusInfo.this.mAIAnimationIn = animation.getAnimatedFraction();
                    PieStatusInfo.this.mAITextPaint.setAlpha((int) (PieStatusInfo.this.mAIAnimationIn * ((float) Color.alpha(PieStatusInfo.this.mColorNotifications))));
                    PieStatusInfo.this.mPieMenu.invalidate();
                }
            });
            this.mAIAnimatorIn.setStartDelay((long) (this.mAnimationTime * 10));
            this.mAIAnimatorIn.setDuration((long) (this.mAnimationTime * 14));
            this.mAIAnimatorIn.start();
            this.mAIAnimatorOutIn = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mAIAnimatorOutIn.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                /* class com.noname81.lmt.PieStatusInfo.C05333 */

                public void onAnimationUpdate(ValueAnimator animation) {
                    float additionalInfoAnimationInOut;
                    if (animation.getAnimatedFraction() <= 0.2f) {
                        if (animation.getAnimatedFraction() < 0.1f) {
                            additionalInfoAnimationInOut = Math.max(animation.getAnimatedFraction() * 10.0f, 0.0f);
                        } else {
                            additionalInfoAnimationInOut = Math.max((0.18f - animation.getAnimatedFraction()) * 10.0f, 0.0f);
                        }
                        PieStatusInfo.this.mAITextPaint.setAlpha((int) ((1.0f - additionalInfoAnimationInOut) * ((float) Color.alpha(PieStatusInfo.this.mColorNotifications))));
                        if (PieStatusInfo.this.mAIAnimationInOut > additionalInfoAnimationInOut && !PieStatusInfo.this.mAIAnimationInOutReversed) {
                            PieStatusInfo.this.fillAdditionalInfoStrings();
                            PieStatusInfo.this.mAIAnimationInOutReversed = true;
                        } else if (PieStatusInfo.this.mAIAnimationInOut < additionalInfoAnimationInOut && PieStatusInfo.this.mAIAnimationInOutReversed) {
                            PieStatusInfo.this.mAIAnimationInOutReversed = false;
                        }
                        PieStatusInfo.this.mAIAnimationInOut = additionalInfoAnimationInOut;
                        PieStatusInfo.this.mPieMenu.invalidate();
                    }
                }
            });
            this.mAIAnimatorOutIn.setStartDelay(3000);
            this.mAIAnimatorOutIn.setDuration(3000L);
            this.mAIAnimatorOutIn.setRepeatCount(-1);
            this.mAIAnimatorOutIn.setInterpolator(new LinearInterpolator());
            this.mAIAnimatorOutIn.start();
        }
    }

    @Override // com.noname81.lmt.PieMenu.PieView
    public boolean drawBackground(Canvas canvas) {
        canvas.drawPaint(this.mBackgroundPaint);
        return false;
    }

    @Override // com.noname81.lmt.PieMenu.PieView
    public boolean drawForeground(Canvas canvas) {
        float inv = this.mOnTheLeft ? -1.0f : 1.0f;
        this.mClockTextPaint.setTextSize((float) this.mClockTextSizeBig);
        canvas.drawTextOnPath(this.mClockStrings[0], this.mClockPath, (this.mClockAnimationIn - 4.0f) * inv * ((float) this.mClockTextSizeBig), (float) (-this.mRadiusOffset), this.mClockTextPaint);
        this.mClockTextPaint.setTextSize((float) this.mClockTextSizeSmall);
        int i = this.mClockTextSizeBig;
        canvas.drawTextOnPath(this.mClockStrings[1], this.mClockPath, inv * ((float) (-this.mPositionOffset)), ((this.mClockAnimationIn - 4.0f) * ((float) i)) - ((float) i), this.mClockTextPaint);
        float animationInOut = 0.0f;
        if (this.mAnimationTime > 80) {
            animationInOut = this.mAIAnimationInOut;
        }
        canvas.drawTextOnPath(this.mAIStrings[0], this.mAIPath, inv * (((float) this.mPositionOffset) + (animationInOut * 400.0f)), (((float) (-this.mAITextSize)) * 2.4f) - ((float) this.mRadiusOffset), this.mAITextPaint);
        canvas.drawTextOnPath(this.mAIStrings[1], this.mAIPath, inv * (((float) this.mPositionOffset) + (animationInOut * 400.0f)), (((float) (-this.mAITextSize)) * 1.2f) - ((float) this.mRadiusOffset), this.mAITextPaint);
        canvas.drawTextOnPath(this.mAIStrings[2], this.mAIPath, inv * (((float) this.mPositionOffset) + (400.0f * animationInOut)), (float) (-this.mRadiusOffset), this.mAITextPaint);
        return true;
    }

    @Override // com.noname81.lmt.PieMenu.PieView
    public boolean onTouchEvent(int action, float x, float y, float xo, float yo, PointF polar) {
        boolean handled = false;
        if (2 == action) {
            if (polar.y <= ((float) (this.mRadius + (this.mRadiusInc * this.mLevels))) || (this.mClockAnimationIn < 1.0f && this.mAnimationActive)) {
                if (this.mAnimationActive && !this.mAIAnimatorOutIn.isStarted()) {
                    this.mAIAnimatorOutIn.setRepeatCount(-1);
                    this.mAIAnimatorOutIn.setStartDelay(0);
                    this.mAIAnimatorOutIn.start();
                }
                Paint paint = this.mClockTextPaint;
                paint.setColor(Color.argb(paint.getAlpha(), Color.red(this.mColorClock), Color.green(this.mColorClock), Color.blue(this.mColorClock)));
                Paint paint2 = this.mAITextPaint;
                paint2.setColor(Color.argb(paint2.getAlpha(), Color.red(this.mColorNotifications), Color.green(this.mColorNotifications), Color.blue(this.mColorNotifications)));
                if (this.mClockSelected || this.mAISelected) {
                    this.mPieMenu.invalidate();
                }
                this.mClockSelected = false;
                this.mAISelected = false;
            } else {
                handled = true;
                if (this.mAnimationActive && this.mAIAnimatorOutIn.getRepeatCount() == -1) {
                    if (this.mAIAnimationIn < 1.0f && this.mAIAnimatorOutIn.isStarted()) {
                        this.mAIAnimatorOutIn.setRepeatCount(0);
                        this.mAIAnimatorOutIn.end();
                    } else if (this.mAIAnimationIn >= 1.0f) {
                        this.mAIAnimatorOutIn.setRepeatCount(0);
                    }
                }
                if (isClockArea(polar)) {
                    Paint paint3 = this.mClockTextPaint;
                    paint3.setColor(Color.argb(paint3.getAlpha(), Color.red(this.mColorSelected), Color.green(this.mColorSelected), Color.blue(this.mColorSelected)));
                    Paint paint4 = this.mAITextPaint;
                    paint4.setColor(Color.argb(paint4.getAlpha(), Color.red(this.mColorNotifications), Color.green(this.mColorNotifications), Color.blue(this.mColorNotifications)));
                    if (!this.mClockSelected) {
                        this.mPieMenu.invalidate();
                    }
                    this.mClockSelected = true;
                    this.mAISelected = false;
                } else {
                    Paint paint5 = this.mAITextPaint;
                    paint5.setColor(Color.argb(paint5.getAlpha(), Color.red(this.mColorSelected), Color.green(this.mColorSelected), Color.blue(this.mColorSelected)));
                    Paint paint6 = this.mClockTextPaint;
                    paint6.setColor(Color.argb(paint6.getAlpha(), Color.red(this.mColorClock), Color.green(this.mColorClock), Color.blue(this.mColorClock)));
                    if (!this.mAISelected) {
                        this.mPieMenu.invalidate();
                    }
                    this.mAISelected = true;
                    this.mClockSelected = false;
                }
            }
        } else if (1 == action && polar.y > ((float) (this.mRadius + (this.mRadiusInc * this.mLevels))) && (this.mClockAnimationIn >= 1.0f || !this.mAnimationActive)) {
            handled = true;
            if (isClockArea(polar)) {
                this.mLauncher.doOpenNotificationBar();
            } else {
                int i = this.mAIContent;
                if (i == 1) {
                    this.mLauncher.doOpenQuickSettings();
                } else if (i > 1) {
                    PendingIntent startIntent = this.mNotificationDataHelper.getNotificationDataStartIntent(i - 2);
                    if (startIntent != null) {
                        try {
                            startIntent.send();
                        } catch (Exception e) {
                        }
                    }
                    this.mNotificationDataHelper.removeNotificationData(this.mAIContent - 2);
                }
            }
        }
        return handled;
    }

    @Override // com.noname81.lmt.PieMenu.PieView
    public void onShrink() {
    }

    private boolean isClockArea(PointF polar) {
        if (this.mOnTheBottom) {
            if (polar.x > 0.0f) {
                return true;
            }
            return false;
        } else if (((double) polar.x) > 1.5707963267948966d) {
            return true;
        } else {
            return false;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void makePath(float morph) {
        int i = this.mRadius;
        int i2 = this.mRadiusInc;
        float radius = (float) ((this.mLevels * i2) + i);
        float radius2 = (float) (i + i2);
        this.mClockPath.reset();
        this.mAIPath.reset();
        if (this.mOnTheLeft) {
            RectF bb = new RectF(((float) this.mCenter.x) - radius, ((float) this.mCenter.y) - radius, ((float) this.mCenter.x) + radius, ((float) this.mCenter.y) + radius);
            RectF bbm = new RectF(((float) this.mCenter.x) - radius, ((float) this.mCenter.y) - radius, mix(((float) this.mCenter.x) + radius, ((float) this.mCenter.x) + (2.0f * radius2), morph), mix(((float) this.mCenter.y) + radius, ((float) this.mCenter.y) - radius, morph));
            this.mClockPath.arcTo(bb, 0.0f, 180.0f);
            this.mAIPath.arcTo(bbm, 180.0f, 180.0f);
            this.mClockTextPaint.setTextAlign(Paint.Align.LEFT);
            this.mAITextPaint.setTextAlign(Paint.Align.RIGHT);
        } else if (this.mOnTheBottom) {
            RectF bb2 = new RectF(((float) this.mCenter.x) - radius, ((float) this.mCenter.y) - radius, ((float) this.mCenter.x) + radius, ((float) this.mCenter.y) + radius);
            RectF bbm2 = new RectF(((float) this.mCenter.x) - radius, mix(((float) this.mCenter.y) - radius, ((float) this.mCenter.y) - (radius * 2.0f), morph), mix(((float) this.mCenter.x) + radius, ((float) this.mCenter.x) + (3.0f * radius), morph), mix(((float) this.mCenter.y) + radius, ((float) this.mCenter.y) - (2.0f * radius), morph));
            float start1 = mix(270.0f, 225.0f, morph);
            this.mClockPath.arcTo(bb2, 90.0f, 180.0f);
            this.mAIPath.arcTo(bbm2, start1, 180.0f);
            this.mClockTextPaint.setTextAlign(Paint.Align.RIGHT);
            this.mAITextPaint.setTextAlign(Paint.Align.LEFT);
        } else {
            RectF bb3 = new RectF(((float) this.mCenter.x) - radius, ((float) this.mCenter.y) - radius, ((float) this.mCenter.x) + radius, ((float) this.mCenter.y) + radius);
            RectF bbm3 = new RectF(mix(((float) this.mCenter.x) - radius, ((float) this.mCenter.x) - (radius2 * 2.0f), morph), ((float) this.mCenter.y) - radius, mix(((float) this.mCenter.x) + radius, ((float) this.mCenter.x) + (2.0f * radius), morph), mix(((float) this.mCenter.y) + radius, ((float) this.mCenter.y) - radius, morph));
            this.mClockPath.arcTo(bb3, 0.0f, 180.0f);
            this.mAIPath.arcTo(bbm3, 180.0f, 180.0f);
            this.mClockTextPaint.setTextAlign(Paint.Align.RIGHT);
            this.mAITextPaint.setTextAlign(Paint.Align.LEFT);
        }
    }

    private float mix(float value1, float value2, float mix) {
        float offset = Math.abs((value1 - value2) * mix);
        if (value1 < value2) {
            return value1 + offset;
        }
        return value1 - offset;
    }

    private void fillClockStrings() {
        Calendar cal = Calendar.getInstance();
        if (this.mTwentyFour == 24) {
            this.mClockStrings[0] = String.format("%02d:%02d", Integer.valueOf(cal.get(11)), Integer.valueOf(cal.get(12)));
            this.mClockStrings[1] = "24h";
            return;
        }
        String[] strArr = this.mClockStrings;
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(cal.get(10) == 0 ? 12 : cal.get(10));
        objArr[1] = Integer.valueOf(cal.get(12));
        strArr[0] = String.format("%02d:%02d", objArr);
        this.mClockStrings[1] = cal.get(9) == 0 ? "AM" : "PM";
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void fillAdditionalInfoStrings() {
        Calendar cal = Calendar.getInstance();
        int i = this.mAIContent;
        if (i == 0) {
            fillOverview(cal);
            this.mAIContent = 1;
        } else if (i == 1) {
            fillNotification(cal, 0);
            this.mAIContent = 2;
        } else if (i == 2 && this.mNotificationDataHelper.getNotificationDataSize() > 1) {
            fillNotification(cal, 1);
            this.mAIContent = 3;
        } else if (this.mAIContent == 3 && this.mNotificationDataHelper.getNotificationDataSize() > 2) {
            fillNotification(cal, 2);
            this.mAIContent = 4;
        } else if (this.mAIContent == 4 && this.mNotificationDataHelper.getNotificationDataSize() > 3) {
            fillNotification(cal, 3);
            this.mAIContent = 5;
        } else if (this.mAIContent != 5 || this.mNotificationDataHelper.getNotificationDataSize() <= 4) {
            fillOverview(cal);
            this.mAIContent = 1;
        } else {
            fillNotification(cal, 4);
            this.mAIContent = 6;
        }
    }

    private void fillOverview(Calendar cal) {
        String str = this.mDateFormat;
        if (str == null || !str.equals("dMy")) {
            String str2 = this.mDateFormat;
            if (str2 == null || !str2.equals("yMd")) {
                this.mAIStrings[0] = String.format("%02d | %02d | %04d", Integer.valueOf(cal.get(2) + 1), Integer.valueOf(cal.get(5)), Integer.valueOf(cal.get(1)));
            } else {
                this.mAIStrings[0] = String.format("%04d | %02d | %02d", Integer.valueOf(cal.get(1)), Integer.valueOf(cal.get(2) + 1), Integer.valueOf(cal.get(5)));
            }
        } else {
            this.mAIStrings[0] = String.format("%02d | %02d | %04d", Integer.valueOf(cal.get(5)), Integer.valueOf(cal.get(2) + 1), Integer.valueOf(cal.get(1)));
        }
        Intent battery = this.mContext.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        this.mAIStrings[1] = String.format("Battery %02d%% | %s", Integer.valueOf((battery.getIntExtra("level", 0) * 100) / battery.getIntExtra("scale", 100)), getRingerModeString());
        this.mAIStrings[2] = getConnectivityString();
    }

    private void fillNotification(Calendar cal, int index) {
        if (this.mNotificationDataHelper.getNotificationDataSize() > index) {
            cal.setTimeInMillis(this.mNotificationDataHelper.getNotificationDataTime(index));
            if (this.mTwentyFour == 24) {
                this.mAIStrings[0] = String.format("%02d:%02d", Integer.valueOf(cal.get(11)), Integer.valueOf(cal.get(12)));
            } else {
                String[] strArr = this.mAIStrings;
                Object[] objArr = new Object[3];
                objArr[0] = Integer.valueOf(cal.get(10));
                objArr[1] = Integer.valueOf(cal.get(12));
                objArr[2] = cal.get(9) == 0 ? "AM" : "PM";
                strArr[0] = String.format("%02d:%02d %s", objArr);
            }
            this.mAIStrings[1] = this.mNotificationDataHelper.getNotificationDataName(index);
            if (this.mAIStrings[1].length() > 25) {
                String[] strArr2 = this.mAIStrings;
                strArr2[1] = this.mAIStrings[1].substring(0, 24) + "...";
            }
            this.mAIStrings[2] = this.mNotificationDataHelper.getNotificationDataText(index);
            if (this.mAIStrings[2].length() > 25) {
                String[] strArr3 = this.mAIStrings;
                strArr3[2] = this.mAIStrings[2].substring(0, 24) + "...";
                return;
            }
            return;
        }
        String[] strArr4 = this.mAIStrings;
        strArr4[0] = "No notifications";
        strArr4[1] = getCPUInfo();
        this.mAIStrings[2] = getMemInfo();
    }

    private String getConnectivityString() {
        NetworkInfo ni;
        ConnectivityManager cm = (ConnectivityManager) this.mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        String connectivity = "Not connected";
        if (cm == null || (ni = cm.getActiveNetworkInfo()) == null) {
            return connectivity;
        }
        if (ni.isConnectedOrConnecting()) {
            connectivity = "Connected";
        }
        if (!(ni.getTypeName() == null || ni.getTypeName().length() == 0)) {
            connectivity = connectivity + " | " + ni.getTypeName();
        }
        if (ni.getType() == 1) {
            WifiManager wifi = (WifiManager) this.mContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wifi.getWifiState() != 3) {
                return connectivity;
            }
            return connectivity + " | " + wifi.getConnectionInfo().getRssi() + " dBm";
        } else if (ni.getSubtypeName() == null || ni.getSubtypeName().length() == 0) {
            return connectivity;
        } else {
            return connectivity + " | " + ni.getSubtypeName();
        }
    }

    private String getRingerModeString() {
        AudioManager am = (AudioManager) this.mContext.getSystemService(Context.AUDIO_SERVICE);
        if (am == null) {
            return BuildConfig.FLAVOR;
        }
        int ringerMode = am.getRingerMode();
        if (ringerMode == 0) {
            return "Ringer" + " | silent";
        } else if (ringerMode == 1) {
            return "Ringer" + " | vibrate";
        } else if (ringerMode != 2) {
            return "Ringer";
        } else {
            return "Ringer" + " | " + am.getStreamVolume(2);
        }
    }

    private String getCPUInfo() {
        String cpuInfo = this.mRootContext.runCommandResult("cat /sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq", false);
        if (cpuInfo.length() > 4) {
            cpuInfo = cpuInfo.substring(0, cpuInfo.length() - 4) + "MHz | ";
        }
        String online = this.mRootContext.runCommandResult("cat /sys/devices/system/cpu/online", false).replace("\n", BuildConfig.FLAVOR);
        if (cpuInfo.length() <= 0) {
            return cpuInfo;
        }
        return cpuInfo + "Core " + online + " active";
    }

    private String getMemInfo() {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ((ActivityManager) this.mContext.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryInfo(mi);
        if (Build.VERSION.SDK_INT >= 16) {
            return "Used " + ((mi.totalMem - mi.availMem) / PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED) + " MB | Free " + (mi.availMem / PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED) + " MB";
        }
        return "Free " + (mi.availMem / PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED) + " MB";
    }

    private void handleSensorListener(boolean activate) {
        if (this.mAnimationActive && this.mSensorActivated) {
            if (activate) {
                SensorManager sensorManager = this.mSensorManager;
                sensorManager.registerListener(this, sensorManager.getDefaultSensor(1), 3);
                this.mSensorLastUpdate = System.currentTimeMillis() + 1000;
                return;
            }
            this.mSensorManager.unregisterListener(this);
            this.mSensorAnimator.cancel();
            this.mSensorAnimation = 0.0f;
            this.mAITextPaint.setTextSize((float) this.mAITextSize);
        }
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == 1) {
            long actualTime = System.currentTimeMillis();
            if (actualTime - this.mSensorLastUpdate >= 400) {
                float[] fArr = this.mSensorGravity;
                float f = this.mSensorAlpha;
                fArr[0] = (fArr[0] * f) + ((1.0f - f) * event.values[0]);
                float[] fArr2 = this.mSensorGravity;
                float f2 = this.mSensorAlpha;
                fArr2[1] = (fArr2[1] * f2) + ((1.0f - f2) * event.values[1]);
                float[] fArr3 = this.mSensorGravity;
                float f3 = this.mSensorAlpha;
                fArr3[2] = (fArr3[2] * f3) + ((1.0f - f3) * event.values[2]);
                this.mSensorLinearAcceleration[0] = event.values[0] - this.mSensorGravity[0];
                this.mSensorLinearAcceleration[1] = event.values[1] - this.mSensorGravity[1];
                this.mSensorLinearAcceleration[2] = event.values[2] - this.mSensorGravity[2];
                if (Math.abs(this.mSensorLinearAcceleration[0]) > 4.903325f) {
                    this.mAIAnimatorOutIn.setStartDelay(0);
                    this.mAIAnimatorOutIn.end();
                    this.mAIAnimatorOutIn.start();
                    this.mAIAnimationInOut = 0.0f;
                    this.mAIAnimationInOutReversed = false;
                    this.mAITextPaint.setAlpha(Color.alpha(this.mColorNotifications));
                    this.mNotificationDataHelper.removeNotificationData(this.mAIContent - 2);
                    this.mSensorLastUpdate = actualTime;
                }
                if (actualTime - this.mSensorLastUpdate >= ((long) (this.mAnimationTime * 12)) && Math.abs(this.mSensorLinearAcceleration[2]) > 4.903325f) {
                    this.mSensorAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
                    this.mSensorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        /* class com.noname81.lmt.PieStatusInfo.C05355 */

                        public void onAnimationUpdate(ValueAnimator animation) {
                            float fraction = animation.getAnimatedFraction();
                            PieStatusInfo.this.makePath(fraction);
                            PieStatusInfo.this.mSensorAnimation = fraction;
                            PieStatusInfo.this.mAITextPaint.setTextSize(((float) PieStatusInfo.this.mAITextSize) + ((((float) PieStatusInfo.this.mAITextSize) * fraction) / 6.0f));
                            PieStatusInfo.this.mPieMenu.invalidate();
                        }
                    });
                    this.mSensorAnimator.setDuration((long) (this.mAnimationTime * 12));
                    if (this.mSensorAnimation < 0.5f) {
                        this.mSensorAnimator.start();
                    } else {
                        this.mSensorAnimator.reverse();
                    }
                    this.mAIAnimatorOutIn.setStartDelay((long) (this.mAnimationTime * 24));
                    this.mAIAnimatorOutIn.end();
                    this.mAIAnimatorOutIn.start();
                    this.mAIAnimationInOut = 0.0f;
                    this.mAITextPaint.setAlpha(Color.alpha(this.mColorNotifications));
                    this.mAIAnimationInOutReversed = false;
                    this.mSensorLastUpdate = actualTime;
                }
            }
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}