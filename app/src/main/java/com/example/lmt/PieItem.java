package com.example.lmt;

import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

/* access modifiers changed from: package-private */
public class PieItem {
    private float animate;
    private int inner;
    private int level;
    private int mColor = -1;
    private Drawable mDrawableLong;
    private Drawable mDrawableShort;
    private Path mPath;
    private boolean mSelected;
    private View mView;
    private int outer;
    private float start;
    private float sweep;

    PieItem(View view, int level2) {
        this.mView = view;
        this.level = level2;
    }

    /* access modifiers changed from: package-private */
    public void setColor(int color) {
        if (color != this.mColor) {
            if (color != 0) {
                Drawable drawable = this.mDrawableShort;
                if (!(drawable == null || drawable.getChangingConfigurations() == 1)) {
                    this.mDrawableShort.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                }
                Drawable drawable2 = this.mDrawableLong;
                if (!(drawable2 == null || drawable2.getChangingConfigurations() == 1)) {
                    this.mDrawableLong.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                }
            } else {
                Drawable drawable3 = this.mDrawableShort;
                if (drawable3 != null) {
                    drawable3.clearColorFilter();
                }
                Drawable drawable4 = this.mDrawableLong;
                if (drawable4 != null) {
                    drawable4.clearColorFilter();
                }
            }
            this.mColor = color;
        }
    }

    /* access modifiers changed from: package-private */
    public void setSelected(boolean s) {
        this.mSelected = s;
        View view = this.mView;
        if (view != null) {
            view.setSelected(s);
        }
    }

    /* access modifiers changed from: package-private */
    public void setAnimationAngle(float a) {
        this.animate = a;
    }

    /* access modifiers changed from: package-private */
    public float getAnimationAngle() {
        return this.animate;
    }

    /* access modifiers changed from: package-private */
    public boolean isSelected() {
        return this.mSelected;
    }

    /* access modifiers changed from: package-private */
    public int getLevel() {
        return this.level;
    }

    /* access modifiers changed from: package-private */
    public void setGeometry(float st, float sw, int inside, int outside, Path p) {
        this.start = st;
        this.sweep = sw;
        this.inner = inside;
        this.outer = outside;
        this.mPath = p;
    }

    /* access modifiers changed from: package-private */
    public float getStart() {
        return this.start;
    }

    /* access modifiers changed from: package-private */
    public float getStartAngle() {
        return this.start + this.animate;
    }

    /* access modifiers changed from: package-private */
    public float getSweep() {
        return this.sweep;
    }

    /* access modifiers changed from: package-private */
    public int getInnerRadius() {
        return this.inner;
    }

    /* access modifiers changed from: package-private */
    public int getOuterRadius() {
        return this.outer;
    }

    /* access modifiers changed from: package-private */
    public View getView() {
        return this.mView;
    }

    /* access modifiers changed from: package-private */
    public Path getPath() {
        return this.mPath;
    }

    /* access modifiers changed from: package-private */
    public void setDrawables(Drawable drawableShort, Drawable drawableLong) {
        this.mDrawableShort = drawableShort;
        this.mDrawableLong = drawableLong;
    }

    /* access modifiers changed from: package-private */
    public void setAlpha(int alpha) {
        Drawable drawable = this.mDrawableShort;
        if (drawable != null) {
            drawable.setAlpha(alpha);
        }
        Drawable drawable2 = this.mDrawableLong;
        if (drawable2 != null) {
            drawable2.setAlpha(alpha);
        }
    }

    /* access modifiers changed from: package-private */
    public void selectImage(int index) {
        Drawable drawable;
        ImageView imageView = (ImageView) this.mView;
        if (index != 0 || (drawable = this.mDrawableShort) == null) {
            Drawable drawable2 = this.mDrawableLong;
            if (drawable2 != null) {
                imageView.setImageDrawable(drawable2);
                return;
            }
            return;
        }
        imageView.setImageDrawable(drawable);
    }
}