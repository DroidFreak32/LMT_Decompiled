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

abstract class ColorDialog extends AlertDialog.Builder implements DialogInterface.OnClickListener, View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private TextView mAlphaLabel;
    private SeekBar mAlphaSeek;
    private TextView mAlphaValue;
    private TextView mBlueLabel;
    private SeekBar mBlueSeek;
    private TextView mBlueValue;
    private Button mColorLast;
    private Button mColorNext;
    private Button mColorPreview;
    private int mColorSelectionCurrent;
    private String[] mColorSelectionStrings;
    private int[] mColorSelectionValues;
    private EditText mColorString;
    private TextView mGreenLabel;
    private SeekBar mGreenSeek;
    private TextView mGreenValue;
    private TextView mRedLabel;
    private SeekBar mRedSeek;
    private TextView mRedValue;

    public abstract boolean onOkClicked(String str);

    ColorDialog(Context context, String title, String[] colorSelectionStrings, String colorString) {
        super(context);
        setTitle(title);
        View layout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.color, (ViewGroup) null);
        setView(layout);
        this.mAlphaLabel = (TextView) layout.findViewById(R.id.alpha_label);
        this.mAlphaValue = (TextView) layout.findViewById(R.id.alpha_value);
        this.mAlphaSeek = (SeekBar) layout.findViewById(R.id.alpha_seek);
        this.mAlphaLabel.setText(R.string.alpha_string);
        this.mAlphaSeek.setOnSeekBarChangeListener(this);
        this.mRedLabel = (TextView) layout.findViewById(R.id.red_label);
        this.mRedValue = (TextView) layout.findViewById(R.id.red_value);
        this.mRedSeek = (SeekBar) layout.findViewById(R.id.red_seek);
        this.mRedLabel.setText(R.string.red_string);
        this.mRedSeek.setOnSeekBarChangeListener(this);
        this.mGreenLabel = (TextView) layout.findViewById(R.id.green_label);
        this.mGreenValue = (TextView) layout.findViewById(R.id.green_value);
        this.mGreenSeek = (SeekBar) layout.findViewById(R.id.green_seek);
        this.mGreenLabel.setText(R.string.green_string);
        this.mGreenSeek.setOnSeekBarChangeListener(this);
        this.mBlueLabel = (TextView) layout.findViewById(R.id.blue_label);
        this.mBlueValue = (TextView) layout.findViewById(R.id.blue_value);
        this.mBlueSeek = (SeekBar) layout.findViewById(R.id.blue_seek);
        this.mBlueLabel.setText(R.string.blue_string);
        this.mBlueSeek.setOnSeekBarChangeListener(this);
        this.mColorPreview = (Button) layout.findViewById(R.id.color_preview);
        this.mColorPreview.setOnClickListener(this);
        this.mColorLast = (Button) layout.findViewById(R.id.color_last);
        this.mColorLast.setText("<");
        this.mColorLast.setOnClickListener(this);
        if (colorSelectionStrings.length == 1) {
            this.mColorLast.setVisibility(View.INVISIBLE);
        }
        this.mColorNext = (Button) layout.findViewById(R.id.color_next);
        this.mColorNext.setText(">");
        this.mColorNext.setOnClickListener(this);
        if (colorSelectionStrings.length == 1) {
            this.mColorNext.setVisibility(View.INVISIBLE);
        }
        this.mColorString = (EditText) layout.findViewById(R.id.color_string);
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
                    this.mAlphaValue.setText(Integer.toString(progress));
                    break;
                case R.id.blue_seek /*{ENCODED_INT: 2131230760}*/:
                    this.mBlueValue.setText(Integer.toString(progress));
                    break;
                case R.id.green_seek /*{ENCODED_INT: 2131230809}*/:
                    this.mGreenValue.setText(Integer.toString(progress));
                    break;
                case R.id.red_seek /*{ENCODED_INT: 2131230869}*/:
                    this.mRedValue.setText(Integer.toString(progress));
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
                return;
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
            this.mAlphaValue.setText(Integer.toString(this.mAlphaSeek.getProgress()));
            this.mRedSeek.setProgress(0);
            this.mRedValue.setText(Integer.toString(this.mRedSeek.getProgress()));
            this.mGreenSeek.setProgress(0);
            this.mGreenValue.setText(Integer.toString(this.mGreenSeek.getProgress()));
            this.mBlueSeek.setProgress(0);
            this.mBlueValue.setText(Integer.toString(this.mBlueSeek.getProgress()));
            this.mColorPreview.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        } else {
            this.mAlphaValue.setText(Integer.toString(Color.alpha(color)));
            this.mAlphaSeek.setProgress(Color.alpha(color));
            this.mRedValue.setText(Integer.toString(Color.red(color)));
            this.mRedSeek.setProgress(Color.red(color));
            this.mGreenValue.setText(Integer.toString(Color.green(color)));
            this.mGreenSeek.setProgress(Color.green(color));
            this.mBlueValue.setText(Integer.toString(Color.blue(color)));
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
                overallColorString.append(String.format("#%02x%02x%02x%02x", Integer.valueOf(Color.alpha(iArr[i])), Integer.valueOf(Color.red(this.mColorSelectionValues[i])), Integer.valueOf(Color.green(this.mColorSelectionValues[i])), Integer.valueOf(Color.blue(this.mColorSelectionValues[i]))));
            }
        }
        if (oneColorSet) {
            this.mColorString.setText(overallColorString.toString());
        }
    }
}