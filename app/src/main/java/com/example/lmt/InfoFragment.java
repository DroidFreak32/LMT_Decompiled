package com.example.lmt;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class InfoFragment extends Fragment {
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Spanned result;
        super.onCreateView(inflater, container, savedInstanceState);
        TextView textView = new TextView(getActivity());
        textView.setGravity(17);
        textView.setLinksClickable(true);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        String info = "LMT " + SettingsValues.getInstance(getActivity()).getVersion() + " by noname81<br><br>This version of LMT will expire in " + SettingsValues.getInstance(getActivity()).getDays() + " days<br><br><a href='http://forum.xda-developers.com/showthread.php?t=1330150'>Visit the thread at XDA developers!</a><br><br><a href='https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=9C5JKBHDM6QSE'>If you like my work, please consider a donation!</a><br><br>Runtime Permissions:<br>Root: " + RootContext.getInstance(getActivity()).isRootAvailable(false) + "<br>Accessibility: " + AccessibilityHandler.isAccessibilityAvailable(getActivity(), false) + "<br>DrawOverApps: " + PermissionChecker.getInstance().hasDrawOverAppsPermission(getActivity(), false) + "<br>ExternalStorageRead: " + PermissionChecker.getInstance().hasExternalStorageReadPermission(getActivity(), false) + "<br>ExternalStorageWrite: " + PermissionChecker.getInstance().hasExternalStorageWritePermission(getActivity(), false) + "<br>PhoneCalls: " + PermissionChecker.getInstance().hasPhoneCallPermission(getActivity(), false) + "<br>UsageStats: " + PermissionChecker.getInstance().hasUsageStatsPermission(getActivity(), false) + "<br><br>";
        if (Build.VERSION.SDK_INT >= 24) {
            result = Html.fromHtml(info, 0);
        } else {
            result = Html.fromHtml(info);
        }
        textView.setText(result);
        addGestureListener(textView);
        FrameLayout frameLayout = new FrameLayout(getActivity());
        frameLayout.addView(textView);
        return frameLayout;
    }

    private void addGestureListener(View view) {
        final GestureDetector gd = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
            /* class com.noname81.lmt.InfoFragment.AnonymousClass1GestureListener */
            private int count = 0;

            public boolean onDoubleTap(MotionEvent e) {
                this.count++;
                if (this.count < 5) {
                    return false;
                }
                DebugHelper.getInstance().showDebugMenu(InfoFragment.this.getActivity());
                return false;
            }
        });
        view.setOnTouchListener(new View.OnTouchListener() {
            /* class com.noname81.lmt.InfoFragment.View$OnTouchListenerC05181 */

            public boolean onTouch(View view, MotionEvent motionEvent) {
                gd.onTouchEvent(motionEvent);
                return false;
            }
        });
    }
}