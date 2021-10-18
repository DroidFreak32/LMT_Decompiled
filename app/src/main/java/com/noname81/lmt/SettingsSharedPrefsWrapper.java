package com.noname81.lmt;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;

class SettingsSharedPrefsWrapper {
    protected Context mContext;
    private SharedPreferences mSharedPreferences = null;

    SettingsSharedPrefsWrapper(Context context) {
        this.mContext = context;
        if (Settings.System.getInt(this.mContext.getContentResolver(), "LMTExternalConfig", 0) == 0) {
            this.mSharedPreferences = this.mContext.getSharedPreferences("LMT", 0);
        }
    }

    String loadString(String key, String defaultValue) {
        SharedPreferences sharedPreferences = this.mSharedPreferences;
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, defaultValue);
        }
        String value = Settings.System.getString(this.mContext.getContentResolver(), key);
        return value == null ? defaultValue : value;
    }

    void saveString(String key, String value, SharedPreferences.Editor editor) {
        SharedPreferences sharedPreferences = this.mSharedPreferences;
        if (sharedPreferences == null) {
            Settings.System.putString(this.mContext.getContentResolver(), key, value);
        } else if (editor == null) {
            SharedPreferences.Editor editor2 = sharedPreferences.edit();
            editor2.putString(key, value);
            editor2.apply();
        } else {
            editor.putString(key, value);
        }
    }

    int loadInt(String key, int defaultValue) {
        SharedPreferences sharedPreferences = this.mSharedPreferences;
        if (sharedPreferences != null) {
            return sharedPreferences.getInt(key, defaultValue);
        }
        try {
            return Settings.System.getInt(this.mContext.getContentResolver(), key);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    void saveInt(String key, int value, SharedPreferences.Editor editor) {
        SharedPreferences sharedPreferences = this.mSharedPreferences;
        if (sharedPreferences == null) {
            Settings.System.putInt(this.mContext.getContentResolver(), key, value);
        } else if (editor == null) {
            SharedPreferences.Editor editor2 = sharedPreferences.edit();
            editor2.putInt(key, value);
            editor2.apply();
        } else {
            editor.putInt(key, value);
        }
    }

    SharedPreferences.Editor createAndReturnSharedPreferencesEditor() {
        SharedPreferences sharedPreferences = this.mSharedPreferences;
        if (sharedPreferences != null) {
            return sharedPreferences.edit();
        }
        return null;
    }
}