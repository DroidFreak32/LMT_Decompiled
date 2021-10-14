package com.example.lmt;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

/* access modifiers changed from: package-private */
public abstract class PieControlBase {
    Context mContext;
    private int mItemSize;
    PieMenu mPie;

    /* access modifiers changed from: protected */
    public abstract void populateMenu();

    PieControlBase(Context context) {
        this.mContext = context;
        this.mItemSize = (int) context.getResources().getDimension(R.dimen.qc_item_size);
    }

    /* access modifiers changed from: protected */
    public void attachToContainer(FrameLayout container) {
        if (this.mPie == null) {
            this.mPie = new PieMenu(this.mContext);
            this.mPie.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            populateMenu();
        }
        container.addView(this.mPie);
    }

    /* access modifiers changed from: package-private */
    public void removeFromContainer(FrameLayout container) {
        container.removeView(this.mPie);
    }

    /* access modifiers changed from: protected */
    public void forceToTop(FrameLayout container) {
        if (this.mPie.getParent() != null) {
            container.removeView(this.mPie);
            container.addView(this.mPie);
        }
    }

    /* access modifiers changed from: package-private */
    public void setClickListener(View.OnClickListener listener, PieItem... items) {
        for (PieItem item : items) {
            item.getView().setOnClickListener(listener);
        }
    }

    /* access modifiers changed from: package-private */
    public void setLongClickListener(View.OnLongClickListener listener, PieItem... items) {
        for (PieItem item : items) {
            item.getView().setOnLongClickListener(listener);
        }
    }

    /* access modifiers changed from: package-private */
    public void setKeyListener(View.OnKeyListener listener, PieItem... items) {
        for (PieItem item : items) {
            item.getView().setOnKeyListener(listener);
        }
    }

    /* access modifiers changed from: package-private */
    public PieItem makeItem(Drawable d0, Drawable d1, int l) {
        ImageView view = new ImageView(this.mContext);
        if (d0 != null) {
            d0.mutate();
        }
        if (d1 != null) {
            d1.mutate();
        }
        view.setImageDrawable(d0);
        view.setMinimumWidth(this.mItemSize);
        view.setMinimumHeight(this.mItemSize);
        view.setScaleType(ImageView.ScaleType.CENTER);
        int i = this.mItemSize;
        view.setLayoutParams(new ViewGroup.LayoutParams(i, i));
        PieItem pieItem = new PieItem(view, l);
        pieItem.setDrawables(d0, d1);
        return pieItem;
    }
}