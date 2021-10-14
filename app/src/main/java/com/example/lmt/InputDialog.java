package com.example.lmt;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;


/* access modifiers changed from: package-private */
public abstract class InputDialog extends AlertDialog.Builder implements DialogInterface.OnClickListener {
    Context mContext;
    private final EditText mInput;

    public abstract boolean onOkClicked(String str);

    InputDialog(Context context, String title, String message, String text, boolean digitKeyboard) {
        super(context);
        this.mContext = context;
        setTitle(title);
        setMessage(message);
        this.mInput = new EditText(context);
        setView(this.mInput);
        if (text != null) {
            this.mInput.setText(text);
            if (digitKeyboard) {
                this.mInput.setInputType(2);
            }
            this.mInput.setSelection(text.length());
        }
        setPositiveButton(R.string.dialog_ok, this);
        setNegativeButton(R.string.dialog_cancel, this);
    }

    InputDialog(Context context, String title, String[] items, String itemChecked) {
        super(context);
        this.mContext = context;
        setTitle(title);
        int itemCheckedValue = -1;
        try {
            itemCheckedValue = Integer.parseInt(itemChecked);
        } catch (NumberFormatException e) {
        }
        setSingleChoiceItems(items, itemCheckedValue, this);
        this.mInput = null;
    }

    private void onCancelClicked(DialogInterface dialog) {
        dialog.dismiss();
    }

    public void onClick(DialogInterface dialog, int which) {
        if (which == -1) {
            if (onOkClicked(this.mInput.getText().toString())) {
                dialog.dismiss();
            }
        } else if (which == -2) {
            onCancelClicked(dialog);
        } else if (onOkClicked(Integer.toString(which))) {
            dialog.dismiss();
        }
    }
}