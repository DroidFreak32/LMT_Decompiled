package com.noname81.lmt;

import android.app.ActionBar;
import android.app.Activity;
//import android.support.v7.app.ActionBar;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
//import androidx.viewpager.widget.ViewPager;
//import android.support.v13.FragmentStatePagerAdapter;;
import android.util.Log;

//import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class LMT extends Activity {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String TAG = "LMT::LMT";
    TabsAdapter mTabsAdapter;
    ViewPager mViewPager;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Starting GUI");
        this.mViewPager = new ViewPager(this);
        this.mViewPager.setId(R.id.pager);
        setContentView(this.mViewPager);
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        createTabs();
        if (savedInstanceState != null) {
            actionBar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
        }
        if (Debug.isDebuggerConnected()) {
            DebugHelper.getInstance().showDebugMenu(this);
        }
        checkAndRequestPermissions();
    }

    private void createTabs() {
        ActionBar actionBar = getActionBar();
        this.mTabsAdapter = new TabsAdapter(this, this.mViewPager);
        int featureSet = SettingsValues.getInstance(getApplicationContext()).loadTouchServiceMode();
        this.mTabsAdapter.addTab(actionBar.newTab().setText(R.string.navigation_settings), SettingsViewFragment.class);
        this.mTabsAdapter.addTab(actionBar.newTab().setText(R.string.navigation_info), InfoFragment.class);
        if (featureSet < 2) {
            this.mTabsAdapter.addTab(actionBar.newTab().setText(R.string.navigation_gestures), GesturesFragment.class);
            this.mTabsAdapter.addTab(actionBar.newTab().setText(R.string.navigation_isas), IsasFragment.class);
        }
        if (featureSet > 0) {
            this.mTabsAdapter.addTab(actionBar.newTab().setText(R.string.navigation_pie), PieFragment.class);
        }
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }

    private void checkAndRequestPermissions() {
        PermissionChecker permissionChecker = PermissionChecker.getInstance();
        permissionChecker.checkAndRequestExternalStorageReadPermission(this, true);
        permissionChecker.checkAndRequestDrawOverAppsPermission(this, true);
        permissionChecker.hasExternalStorageWritePermission(this, true);
        permissionChecker.hasPhoneCallPermission(this, true);
        permissionChecker.hasUsageStatsPermission(this, true);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PermissionChecker.DRAW_OVER_APPS_REQUEST_CODE) {
            PermissionChecker.getInstance().hasDrawOverAppsPermission(this, true);
        }
        if (requestCode == PermissionChecker.USAGE_STATS_REQUEST_CODE) {
            PermissionChecker.getInstance().hasUsageStatsPermission(this, true);
        }
    }

    public static class TabsAdapter extends FragmentStatePagerAdapter implements ActionBar.TabListener, ViewPager.OnPageChangeListener {
        private final ActionBar mActionBar;
        private final Context mContext;
        private final ArrayList<TabInfo> mTabs = new ArrayList<>();
        private final ViewPager mViewPager;

        static final class TabInfo {
            private final Bundle args;
            private final Class<?> clss;

            TabInfo(Class<?> _class, Bundle _args) {
                this.clss = _class;
                this.args = _args;
            }
        }

        TabsAdapter(Activity activity, ViewPager pager) {
            super(activity.getFragmentManager());
            this.mContext = activity;
            this.mActionBar = activity.getActionBar();
            this.mViewPager = pager;
            this.mViewPager.setAdapter(this);
            this.mViewPager.setOnPageChangeListener(this);
        }

        void addTab(ActionBar.Tab tab, Class<?> clss) {
            TabInfo info = new TabInfo(clss, null);
            tab.setTag(info);
            tab.setTabListener(this);
            this.mTabs.add(info);
            this.mActionBar.addTab(tab);
            notifyDataSetChanged();
        }

        @Override // android.support.v4.view.PagerAdapter
        public int getCount() {
            return this.mTabs.size();
        }

        @Override // android.support.v13.app.FragmentStatePagerAdapter
        public Fragment getItem(int position) {
            TabInfo info = this.mTabs.get(position);
            return Fragment.instantiate(this.mContext, info.clss.getName(), info.args);
        }

        @Override // android.support.v4.view.ViewPager.OnPageChangeListener
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override // android.support.v4.view.ViewPager.OnPageChangeListener
        public void onPageSelected(int position) {
            this.mActionBar.setSelectedNavigationItem(position);
        }

        @Override // android.support.v4.view.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int state) {
        }

        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            Object tag = tab.getTag();
            for (int i = 0; i < this.mTabs.size(); i++) {
                if (this.mTabs.get(i) == tag) {
                    this.mViewPager.setCurrentItem(i);
                }
            }
        }

        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        }

        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        }
    }
}