package com.noname81.lmt;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Objects;

abstract class ColorDialog extends AlertDialog.Builder implements DialogInterface.OnClickListener, View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private SeekBar mAlphaSeek;
    private TextView mAlphaValue;
    private SeekBar mBlueSeek;
    private TextView mBlueValue;
    private Button mColorPreview;
    private int mColorSelectionCurrent;
    private String[] mColorSelectionStrings;
    private int[] mColorSelectionValues;
    private EditText mColorString;
    private SeekBar mGreenSeek;
    private TextView mGreenValue;
    private SeekBar mRedSeek;
    private TextView mRedValue;

    public abstract boolean onOkClicked(String str);

    ColorDialog(Context context, String title, String[] colorSelectionStrings, String colorString) {
        super(context);
        setTitle(title);
        View layout = ((LayoutInflater) Objects.requireNonNull(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))).inflate(R.layout.color, (ViewGroup) null);
        setView(layout);

        TextView mAlphaLabel = layout.findViewById(R.id.alpha_label);
        mAlphaLabel.setText(R.string.alpha_string);
        this.mAlphaValue = layout.findViewById(R.id.alpha_value);
        this.mAlphaSeek = layout.findViewById(R.id.alpha_seek);
        this.mAlphaSeek.setOnSeekBarChangeListener(this);

        TextView mRedLabel = layout.findViewById(R.id.red_label);
        mRedLabel.setText(R.string.red_string);
        this.mRedValue = layout.findViewById(R.id.red_value);
        this.mRedSeek = layout.findViewById(R.id.red_seek);
        this.mRedSeek.setOnSeekBarChangeListener(this);

        TextView mGreenLabel = layout.findViewById(R.id.green_label);
        mGreenLabel.setText(R.string.green_string);
        this.mGreenValue = layout.findViewById(R.id.green_value);
        this.mGreenSeek = layout.findViewById(R.id.green_seek);
        this.mGreenSeek.setOnSeekBarChangeListener(this);

        TextView mBlueLabel = layout.findViewById(R.id.blue_label);
        mBlueLabel.setText(R.string.blue_string);
        this.mBlueValue = layout.findViewById(R.id.blue_value);
        this.mBlueSeek = layout.findViewById(R.id.blue_seek);
        this.mBlueSeek.setOnSeekBarChangeListener(this);

        this.mColorPreview = layout.findViewById(R.id.color_preview);
        this.mColorPreview.setOnClickListener(this);

        Button mColorLast = layout.findViewById(R.id.color_last);
        mColorLast.setText("<");
        mColorLast.setOnClickListener(this);

        if (colorSelectionStrings.length == 1) {
            mColorLast.setVisibility(View.INVISIBLE);
        }
        Button mColorNext = layout.findViewById(R.id.color_next);
        mColorNext.setText(">");
        mColorNext.setOnClickListener(this);
        if (colorSelectionStrings.length == 1) {
            mColorNext.setVisibility(View.INVISIBLE);
        }
        this.mColorString = layout.findViewById(R.id.color_string);
        this.mColorString.setText(colorString);
        this.mColorSelectionCurrent = 0;
        this.mColorSelectionStrings = colorSelectionStrings;
        this.mColorSelectionValues = new int[colorSelectionStrings.length];
        parseColorString(colorString);
        updateColorPreviews();
        setPositiveButton(R.string.dialog_ok, this);
        setNegativeButton(R.string.dialog_cancel, this);
    }

    private void onCancelClicked(DialogInterface dialog) {
        dialog.dismiss();
    }

    public void onClick(DialogInterface dialog, int which) {
        if (which == -1) {
            if (onOkClicked(this.mColorString.getText().toString())) {
                dialog.dismiss();
            }
        } else if (which == -2) {
            onCancelClicked(dialog);
        } else if (onOkClicked(Integer.toString(which))) {
            dialog.dismiss();
        }
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            switch (seekBar.getId()) {
                case R.id.alpha_seek /*{ENCODED_INT: 2131230751}*/:
                    this.mAlphaValue.setText(String.valueOf(progress));
                    break;
                case R.id.blue_seek /*{ENCODED_INT: 2131230760}*/:
                    this.mBlueValue.setText(String.valueOf(progress));
                    break;
                case R.id.green_seek /*{ENCODED_INT: 2131230809}*/:
                    this.mGreenValue.setText(String.valueOf(progress));
                    break;
                case R.id.red_seek /*{ENCODED_INT: 2131230869}*/:
                    this.mRedValue.setText(String.valueOf(progress));
                    break;
            }
            int color = Color.argb(this.mAlphaSeek.getProgress(), this.mRedSeek.getProgress(), this.mGreenSeek.getProgress(), this.mBlueSeek.getProgress());
            this.mColorPreview.setBackgroundColor(color);
            this.mColorSelectionValues[this.mColorSelectionCurrent] = color;
            updateColorPreviews();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.color_last /*{ENCODED_INT: 2131230774}*/:
                int i = this.mColorSelectionCurrent;
                if (i == 0) {
                    i = this.mColorSelectionStrings.length;
                }
                this.mColorSelectionCurrent = i - 1;
                updateColorPreviews();
                return;
            case R.id.color_next /*{ENCODED_INT: 2131230775}*/:
                int i2 = this.mColorSelectionCurrent;
                if (i2 >= this.mColorSelectionStrings.length) {
                    this.mColorSelectionCurrent = 0;
                } else {
                    this.mColorSelectionCurrent = i2 + 1;
                }
                int i3 = this.mColorSelectionCurrent;
                if (i3 >= this.mColorSelectionStrings.length) {
                    i3 = 0;
                } else {
                    this.mColorSelectionCurrent = i3 + 1;
                }
                this.mColorSelectionCurrent = i3;
                updateColorPreviews();
                return;
            default:
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    private void parseColorString(String colorString) {
        if (colorString.length() == 1) {
            int i = 0;
            while (true) {
                int[] iArr = this.mColorSelectionValues;
                if (i < iArr.length) {
                    iArr[i] = -2;
                    i++;
                } else {
                    return;
                }
            }
        } else {
            try {
                String[] colorStrings = colorString.replace(" ", BuildConfig.FLAVOR).split(",");
                for (int i2 = 0; i2 < this.mColorSelectionValues.length; i2++) {
                    if (colorStrings.length <= i2 || colorStrings[i2].length() == 0) {
                        this.mColorSelectionValues[i2] = -2;
                    } else {
                        this.mColorSelectionValues[i2] = Color.parseColor(colorStrings[i2]);
                    }
                }
            } catch (Exception e) {
                int i3 = 0;
                while (true) {
                    int[] iArr2 = this.mColorSelectionValues;
                    if (i3 < iArr2.length) {
                        iArr2[i3] = -2;
                        i3++;
                    } else {
                        return;
                    }
                }
            }
        }
    }

    private void updateColorPreviews() {
        int color = this.mColorSelectionValues[this.mColorSelectionCurrent];
        if (color == -2) {
            this.mAlphaSeek.setProgress(255);
            this.mAlphaValue.setText(String.valueOf(this.mAlphaSeek.getProgress()));
            this.mRedSeek.setProgress(0);
            this.mRedValue.setText(String.valueOf(this.mRedSeek.getProgress()));
            this.mGreenSeek.setProgress(0);
            this.mGreenValue.setText(String.valueOf(this.mGreenSeek.getProgress()));
            this.mBlueSeek.setProgress(0);
            this.mBlueValue.setText(String.valueOf(this.mBlueSeek.getProgress()));
            this.mColorPreview.setBackgroundColor(View.MEASURED_STATE_MASK);
        } else {
            this.mAlphaValue.setText(String.valueOf(Color.alpha(color)));
            this.mAlphaSeek.setProgress(Color.alpha(color));
            this.mRedValue.setText(String.valueOf(Color.red(color)));
            this.mRedSeek.setProgress(Color.red(color));
            this.mGreenValue.setText(String.valueOf(Color.green(color)));
            this.mGreenSeek.setProgress(Color.green(color));
            this.mBlueValue.setText(String.valueOf(Color.blue(color)));
            this.mBlueSeek.setProgress(Color.blue(color));
            this.mColorPreview.setBackgroundColor(this.mColorSelectionValues[this.mColorSelectionCurrent]);
        }
        this.mColorPreview.setText(this.mColorSelectionStrings[this.mColorSelectionCurrent]);
        boolean oneColorSet = false;
        StringBuilder overallColorString = new StringBuilder();
        for (int i = 0; i < this.mColorSelectionValues.length; i++) {
            if (i != 0) {
                overallColorString.append(",");
            }
            int[] iArr = this.mColorSelectionValues;
            if (iArr[i] != -2) {
                oneColorSet = true;
                overallColorString.append(String.format("#%02x%02x%02x%02x", Color.alpha(iArr[i]), Color.red(this.mColorSelectionValues[i]), Color.green(this.mColorSelectionValues[i]), Color.blue(this.mColorSelectionValues[i])));
            }
        }
        if (oneColorSet) {
            this.mColorString.setText(overallColorString.toString());
        }
    }
}