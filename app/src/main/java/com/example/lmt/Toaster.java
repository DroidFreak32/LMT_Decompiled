package com.example.lmt;

import android.content.Context;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

/* access modifiers changed from: package-private */
public class Toaster {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int Debug = 1;
    static final int Image = 2;
    static final int None = 0;
    static final int Vibrate = 3;
    private static Toaster instance = null;
    private Context mContext;
    private int mMode;
    private SettingsValues mSettings = SettingsValues.getInstance(this.mContext);
    private Vibrator mVibrator = ((Vibrator) this.mContext.getSystemService("vibrator"));

    private Toaster(Context context) {
        this.mContext = context;
        this.mMode = SettingsValues.getInstance(context).loadFeedbackMode();
    }

    static Toaster getInstance(Context context) {
        if (instance == null) {
            instance = new Toaster(context);
        }
        return instance;
    }

    /* access modifiers changed from: package-private */
    public int getMode() {
        return this.mMode;
    }

    /* access modifiers changed from: package-private */
    public void setMode(int mode) {
        this.mMode = mode;
        this.mSettings.saveFeedbackMode(this.mMode);
    }

    /* access modifiers changed from: package-private */
    public void show(TouchServiceResult result) {
        int i = this.mMode;
        if (i == 1) {
            showDebug(result);
        } else if (i == 2) {
            showImage(result);
        } else if (i == 3) {
            vibrateGestures();
        }
    }

    private void showDebug(TouchServiceResult result) {
        Toast.makeText(this.mContext, result.toString(), 0).show();
    }

    private void showImage(TouchServiceResult result) {
        if (result.getGesture() < 14) {
            try {
                View layout = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(C0536R.layout.toaster, (ViewGroup) null);
                ((ImageView) layout.findViewById(C0536R.C0538id.image)).setImageResource(this.mContext.getResources().getIdentifier(TouchServiceResult.names[result.getGesture()].toLowerCase(), "drawable", this.mContext.getPackageName()));
                Toast toast = new Toast(this.mContext.getApplicationContext());
                toast.setGravity(16, 0, 0);
                toast.setDuration(0);
                toast.setView(layout);
                toast.show();
            } catch (Exception e) {
            }
        }
    }

    private void vibrateGestures() {
        this.mVibrator.vibrate((long) this.mSettings.loadGestureVibrationTime());
    }

    /* access modifiers changed from: package-private */
    public void vibratePie() {
        this.mVibrator.vibrate((long) this.mSettings.loadPieVibrationTime());
    }
}