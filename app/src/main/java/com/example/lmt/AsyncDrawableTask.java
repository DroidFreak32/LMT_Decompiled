package com.example.lmt;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;
import java.lang.ref.WeakReference;

abstract class AsyncDrawableTask extends AsyncTask<Void, Void, Drawable> {
    private static final int FADE_IN_TIME = 500;
    private final WeakReference<ImageView> mImageViewReference;

    /* access modifiers changed from: protected */
    public abstract Drawable doInBackground(Void... voidArr);

    AsyncDrawableTask(ImageView imageView, int placeholderResID) {
        this.mImageViewReference = new WeakReference<>(imageView);
        imageView.setImageDrawable(IconUtils.getDrawable(imageView.getContext(), placeholderResID));
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(Drawable drawable) {
        ImageView imageView;
        if (isCancelled()) {
            drawable = null;
        }
        WeakReference<ImageView> weakReference = this.mImageViewReference;
        if (weakReference != null && (imageView = weakReference.get()) != null) {
            Drawable placeholder = imageView.getDrawable();
            if (drawable != null) {
                TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{placeholder, drawable});
                imageView.setImageDrawable(transitionDrawable);
                transitionDrawable.setCrossFadeEnabled(true);
                transitionDrawable.startTransition(FADE_IN_TIME);
                return;
            }
            imageView.setImageDrawable(placeholder);
        }
    }
}