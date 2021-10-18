package com.noname81.lmt;

import android.content.pm.ActivityInfo;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

class PieItem {
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

    void setColor(int color) {
        if (color != this.mColor) {
            if (color != 0) {
                Drawable drawable = this.mDrawableShort;
                if (!(drawable == null || drawable.getChangingConfigurations() == ActivityInfo.CONFIG_MCC)) {
                    this.mDrawableShort.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
                }
                Drawable drawable2 = this.mDrawableLong;
                if (!(drawable2 == null || drawable2.getChangingConfigurations() ==  ActivityInfo.CONFIG_MCC)) {
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

    void setSelected(boolean s) {
        this.mSelected = s;
        View view = this.mView;
        if (view != null) {
            view.setSelected(s);
        }
    }

    void setAnimationAngle(float a) {
        this.animate = a;
    }

    float getAnimationAngle() {
        return this.animate;
    }

    boolean isSelected() {
        return this.mSelected;
    }

    int getLevel() {
        return this.level;
    }

    void setGeometry(float st, float sw, int inside, int outside, Path p) {
        this.start = st;
        this.sweep = sw;
        this.inner = inside;
        this.outer = outside;
        this.mPath = p;
    }

    float getStart() {
        return this.start;
    }

    float getStartAngle() {
        return this.start + this.animate;
    }

    float getSweep() {
        return this.sweep;
    }

    int getInnerRadius() {
        return this.inner;
    }

    int getOuterRadius() {
        return this.outer;
    }

    View getView() {
        return this.mView;
    }

    Path getPath() {
        return this.mPath;
    }

    void setDrawables(Drawable drawableShort, Drawable drawableLong) {
        this.mDrawableShort = drawableShort;
        this.mDrawableLong = drawableLong;
    }

    void setAlpha(int alpha) {
        Drawable drawable = this.mDrawableShort;
        if (drawable != null) {
            drawable.setAlpha(alpha);
        }
        Drawable drawable2 = this.mDrawableLong;
        if (drawable2 != null) {
            drawable2.setAlpha(alpha);
        }
    }

    void selectImage(int index) {
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