package com.noname81.lmt;

import android.app.PendingIntent;
import android.graphics.drawable.Drawable;
import java.util.ArrayList;

class NotificationDataHelper {
    private static NotificationDataHelper instance = null;
    private ArrayList<NotificationData> mNotificationList = new ArrayList<>();

    public class NotificationData {
        PendingIntent mDeleteIntent;
        Drawable mIcon;
        String mName;
        PendingIntent mStartIntent;
        String mText;
        long mTime;

        NotificationData(String name, long time, String text, PendingIntent startIntent, PendingIntent deleteIntent, Drawable icon) {
            this.mName = name;
            this.mTime = time;
            this.mText = text;
            this.mStartIntent = startIntent;
            this.mDeleteIntent = deleteIntent;
            this.mIcon = icon;
        }
    }

    private NotificationDataHelper() {
    }

    public static NotificationDataHelper getInstance() {
        if (instance == null) {
            instance = new NotificationDataHelper();
        }
        return instance;
    }

    void addNotificationData(String name, long time, String text, PendingIntent startIntent, PendingIntent deleteIntent, Drawable icon) {
        int i = 0;
        while (i < this.mNotificationList.size()) {
            if (this.mNotificationList.get(i) != null && this.mNotificationList.get(i).mName != null) {
                if (this.mNotificationList.get(i).mName.equals(name)) {
                    this.mNotificationList.remove(i);
                    if (i > 0) {
                        i--;
                    }
                }
            }
            i++;
        }
        this.mNotificationList.add(0, new NotificationData(name, time, text, startIntent, deleteIntent, icon));
        if (this.mNotificationList.size() > 5) {
            ArrayList<NotificationData> arrayList = this.mNotificationList;
            arrayList.remove(arrayList.size() - 1);
        }
    }

    int getNotificationDataSize() {
        return this.mNotificationList.size();
    }

    String getNotificationDataName(int index) {
        if (index < 0 || index >= this.mNotificationList.size()) {
            return BuildConfig.FLAVOR;
        }
        return this.mNotificationList.get(index).mName;
    }

    long getNotificationDataTime(int index) {
        if (index < 0 || index >= this.mNotificationList.size()) {
            return 0;
        }
        return this.mNotificationList.get(index).mTime;
    }

    PendingIntent getNotificationDataStartIntent(int index) {
        if (index < 0 || index >= this.mNotificationList.size()) {
            return null;
        }
        return this.mNotificationList.get(index).mStartIntent;
    }

    String getNotificationDataText(int index) {
        if (index < 0 || index >= this.mNotificationList.size()) {
            return BuildConfig.FLAVOR;
        }
        return this.mNotificationList.get(index).mText;
    }

    void removeNotificationData(int index) {
        if (index >= 0 && index < this.mNotificationList.size()) {
            try {
                if (this.mNotificationList.get(index).mDeleteIntent != null) {
                    this.mNotificationList.get(index).mDeleteIntent.send();
                }
            } catch (Exception e) {
            }
            this.mNotificationList.remove(index);
        }
    }
}