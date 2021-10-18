package com.noname81.lmt;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MultiSelectActivity extends Activity implements AdapterView.OnItemClickListener {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static int AppSelectMode = 0;
    public static final int REQUEST_CREATE_SHORTCUT = 1;
    public static final int SelectActivity = 2;
    public static final int SelectBlacklisted = 3;
    public static final int SelectBlacklistedPie = 4;
    public static final int SelectPackage = 0;
    public static final int SelectPackageActivity = 1;
    public static final int SelectShortcut = 5;
    public static final Comparator<PackageInfo> comparator = new Comparator<PackageInfo>() {
        /* class com.noname81.lmt.MultiSelectActivity.C05231 */

        public int compare(PackageInfo a, PackageInfo b) {
            return a.applicationInfo.name.compareToIgnoreCase(b.applicationInfo.name);
        }
    };
    List<ActivityInfo> mActivityInfos;
    PackageInfo mPackageInfo;
    List<PackageInfo> mPackageInfos;
    PackageManager mPackageManager;
    SettingsValues mSettings;
    List<ResolveInfo> mShortcuts;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mSettings = SettingsValues.getInstance(getApplicationContext());
        this.mPackageManager = getPackageManager();
        List<Map<String, ?>> normalCommands = new LinkedList<>();
        SeparatedListAdapter adapter = new SeparatedListAdapter(this);
        int i = AppSelectMode;
        if (i == 2) {
            try {
                this.mPackageInfo = this.mPackageManager.getPackageInfo(this.mSettings.getCurrentAction().getString(), PackageManager.GET_ACTIVITIES);
                this.mActivityInfos = new LinkedList();
                if (this.mPackageInfo.activities != null) {
                    for (int i2 = 0; i2 < this.mPackageInfo.activities.length; i2++) {
                        if (this.mPackageInfo.activities[i2].exported) {
                            this.mActivityInfos.add(this.mPackageInfo.activities[i2]);
                        }
                    }
                }
                String name = this.mPackageInfo.applicationInfo.loadLabel(this.mPackageManager).toString();
                for (int i3 = 0; i3 < this.mActivityInfos.size(); i3++) {
                    normalCommands.add(SeparatedListAdapter.createItem(name, this.mActivityInfos.get(i3).name));
                }
                adapter.addSection(getString(R.string.app_choose_an_activity), new AppSimpleAdapter(this, normalCommands));
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        } else if (i == 5) {
            try {
                this.mShortcuts = this.mPackageManager.queryIntentActivities(new Intent("android.intent.action.CREATE_SHORTCUT"), 0);
                for (int i4 = 0; i4 < this.mShortcuts.size(); i4++) {
                    normalCommands.add(SeparatedListAdapter.createItem(this.mShortcuts.get(i4).loadLabel(this.mPackageManager).toString(), this.mShortcuts.get(i4).activityInfo.packageName));
                }
                adapter.addSection(getString(R.string.app_choose_a_shortcut), new AppSimpleAdapter(this, normalCommands));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            this.mPackageInfos = this.mPackageManager.getInstalledPackages(PackageManager.GET_ACTIVITIES);
            int i5 = 0;
            while (i5 < this.mPackageInfos.size()) {
                PackageInfo p = this.mPackageInfos.get(i5);
                if (p.applicationInfo.uid >= 0) {
                    p.applicationInfo.name = p.applicationInfo.loadLabel(this.mPackageManager).toString();
                } else {
                    this.mPackageInfos.remove(i5);
                    i5--;
                }
                i5++;
            }
            Collections.sort(this.mPackageInfos, comparator);
            for (int i6 = 0; i6 < this.mPackageInfos.size(); i6++) {
                PackageInfo p2 = this.mPackageInfos.get(i6);
                normalCommands.add(SeparatedListAdapter.createItem(p2.applicationInfo.name, p2.applicationInfo.packageName));
            }
            adapter.addSection(getString(R.string.app_choose_an_app), new AppSimpleAdapter(this, normalCommands));
        }
        ListView list = new ListView(this);
        list.setAdapter((ListAdapter) adapter);
        list.setOnItemClickListener(this);
        list.setDividerHeight(0);
        setContentView(list);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1 && resultCode == -1) {
            String shortcutString = ((Intent) intent.getExtras().get("android.intent.extra.shortcut.INTENT")).toUri(0);
            Resources res = getResources();
            Drawable iconDrawable = null;
            Bitmap iconBitmap = (Bitmap) intent.getParcelableExtra("android.intent.extra.shortcut.ICON");
            if (iconBitmap != null) {
                iconDrawable = new BitmapDrawable(res, iconBitmap);
            } else {
                try {
                    Intent.ShortcutIconResource iconResource = (Intent.ShortcutIconResource) intent.getParcelableExtra("android.intent.extra.shortcut.ICON_RESOURCE");
                    Resources otherResources = getPackageManager().getResourcesForApplication(iconResource.packageName);
                    iconDrawable = otherResources.getDrawable(otherResources.getIdentifier(iconResource.resourceName, null, null));
                } catch (Exception e) {
                }
            }
            this.mSettings.setCurrentAction(this, new Action(33, shortcutString, iconDrawable));
        }
        super.onActivityResult(requestCode, resultCode, intent);
        finish();
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long arg3) {
        int i = AppSelectMode;
        if (i == 0) {
            this.mSettings.setCurrentAction(this, new Action(2, this.mPackageInfos.get(pos - 1).packageName));
            onBackPressed();
        } else if (i == 1) {
            this.mSettings.setCurrentAction(this, new Action(2, this.mPackageInfos.get(pos - 1).applicationInfo.packageName));
            AppSelectMode = 2;
            startActivityForResult(new Intent(view.getContext(), MultiSelectActivity.class), 0);
            finish();
        } else if (i == 3) {
            this.mSettings.setBlacklisted(this.mPackageInfos.get(pos - 1).packageName);
            onBackPressed();
        } else if (i == 4) {
            this.mSettings.setBlacklistedPie(this.mPackageInfos.get(pos - 1).packageName);
            onBackPressed();
        } else if (i == 5) {
            ActivityInfo info = this.mShortcuts.get(pos - 1).activityInfo;
            ComponentName componentName = new ComponentName(info.applicationInfo.packageName, info.name);
            Intent intent = new Intent();
            intent.setComponent(componentName);
            intent.setAction("android.intent.action.CREATE_SHORTCUT");
            startActivityForResult(intent, 1);
        } else {
            SettingsValues settingsValues = this.mSettings;
            settingsValues.setCurrentAction(this, new Action(27, this.mActivityInfos.get(pos - 1).packageName + "/" + this.mActivityInfos.get(pos - 1).name));
            onBackPressed();
        }
    }

    class AppSimpleAdapter extends SimpleAdapter {
        AppSimpleAdapter(Context context, List<? extends Map<String, ?>> data) {
            super(context, data, R.layout.listitem_icondescription, new String[]{"title", "caption"}, new int[]{R.id.listitem_icondescription_text, R.id.listitem_icondescription_caption});
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = super.getView(position, convertView, parent);
            ImageView icon = (ImageView) row.findViewById(R.id.listitem_icondescription_icon);
            IconUtils.setMaxSizeForImageView(MultiSelectActivity.this.getApplicationContext(), icon);
            new AsyncDrawableTask(icon, R.drawable.none) {
                /* class com.noname81.lmt.MultiSelectActivity.AppSimpleAdapter.AsyncTaskC05241 */

                /* access modifiers changed from: protected */
                @Override // com.noname81.lmt.AsyncDrawableTask
                public Drawable doInBackground(Void... params) {
                    if (MultiSelectActivity.AppSelectMode == 2) {
                        return MultiSelectActivity.this.mPackageInfo.applicationInfo.loadIcon(MultiSelectActivity.this.mPackageManager);
                    }
                    if (MultiSelectActivity.AppSelectMode == 5) {
                        return MultiSelectActivity.this.mShortcuts.get(position).loadIcon(MultiSelectActivity.this.mPackageManager);
                    }
                    return MultiSelectActivity.this.mPackageInfos.get(position).applicationInfo.loadIcon(MultiSelectActivity.this.mPackageManager);
                }
            }.execute(new Void[0]);
            return row;
        }
    }
}