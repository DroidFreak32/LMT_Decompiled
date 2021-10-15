package com.example.lmt;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GesturesFragment extends Fragment implements AdapterView.OnItemClickListener {
    public static final int offsetDualTouchGestures = 11;
    public static final int offsetSingleTouchGestures = 0;
    public static final int offsetSwipeGestures = 3;
    private SeparatedListAdapter mAdapter;
    private ListView mListView;

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }

    public void onResume() {
        SeparatedListAdapter separatedListAdapter;
        if (!(this.mListView == null || (separatedListAdapter = this.mAdapter) == null)) {
            separatedListAdapter.notifyDataSetChanged();
            this.mListView.setAdapter((ListAdapter) this.mAdapter);
        }
        super.onResume();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        List<Map<String, ?>> singleTouchGestures = new LinkedList<>();
        int i = 0 + 1;
        int j = 0 + 1;
        singleTouchGestures.add(SeparatedListAdapter.createItem(TouchServiceResult.names[0], TouchServiceResult.captions[0]));
        int i2 = i + 1;
        String str = TouchServiceResult.names[i];
        int j2 = j + 1;
        singleTouchGestures.add(SeparatedListAdapter.createItem(str, TouchServiceResult.captions[j]));
        int i3 = i2 + 1;
        int j3 = j2 + 1;
        singleTouchGestures.add(SeparatedListAdapter.createItem(TouchServiceResult.names[i2], TouchServiceResult.captions[j2]));
        List<Map<String, ?>> swipeGestures = new LinkedList<>();
        int i4 = i3 + 1;
        int j4 = j3 + 1;
        swipeGestures.add(SeparatedListAdapter.createItem(TouchServiceResult.names[i3], TouchServiceResult.captions[j3]));
        int i5 = i4 + 1;
        int j5 = j4 + 1;
        swipeGestures.add(SeparatedListAdapter.createItem(TouchServiceResult.names[i4], TouchServiceResult.captions[j4]));
        int i6 = i5 + 1;
        int j6 = j5 + 1;
        swipeGestures.add(SeparatedListAdapter.createItem(TouchServiceResult.names[i5], TouchServiceResult.captions[j5]));
        int i7 = i6 + 1;
        int j7 = j6 + 1;
        swipeGestures.add(SeparatedListAdapter.createItem(TouchServiceResult.names[i6], TouchServiceResult.captions[j6]));
        int i8 = i7 + 1;
        int j8 = j7 + 1;
        swipeGestures.add(SeparatedListAdapter.createItem(TouchServiceResult.names[i7], TouchServiceResult.captions[j7]));
        int i9 = i8 + 1;
        int j9 = j8 + 1;
        swipeGestures.add(SeparatedListAdapter.createItem(TouchServiceResult.names[i8], TouchServiceResult.captions[j8]));
        int i10 = i9 + 1;
        int j10 = j9 + 1;
        swipeGestures.add(SeparatedListAdapter.createItem(TouchServiceResult.names[i9], TouchServiceResult.captions[j9]));
        int i11 = i10 + 1;
        int j11 = j10 + 1;
        swipeGestures.add(SeparatedListAdapter.createItem(TouchServiceResult.names[i10], TouchServiceResult.captions[j10]));
        List<Map<String, ?>> dualTouchGestures = new LinkedList<>();
        int i12 = i11 + 1;
        int j12 = j11 + 1;
        dualTouchGestures.add(SeparatedListAdapter.createItem(TouchServiceResult.names[i11], TouchServiceResult.captions[j11]));
        dualTouchGestures.add(SeparatedListAdapter.createItem(TouchServiceResult.names[i12], TouchServiceResult.captions[j12]));
        dualTouchGestures.add(SeparatedListAdapter.createItem(TouchServiceResult.names[i12 + 1], TouchServiceResult.captions[j12 + 1]));
        this.mAdapter = new SeparatedListAdapter(getActivity());
        this.mAdapter.addSection(getString(R.string.gestures_single_touch_gestures), new CommandSimpleAdapterGestures(getActivity(), singleTouchGestures, 0));
        this.mAdapter.addSection(getString(R.string.gestures_swipe_gestures), new CommandSimpleAdapterGestures(getActivity(), swipeGestures, 3));
        this.mAdapter.addSection(getString(R.string.gestures_dual_touch_gestures), new CommandSimpleAdapterGestures(getActivity(), dualTouchGestures, 11));
        this.mListView = new ListView(getActivity());
        this.mListView.setAdapter((ListAdapter) this.mAdapter);
        this.mListView.setOnItemClickListener(this);
        this.mListView.setDividerHeight(0);
        return this.mListView;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long arg3) {
        int realPos = pos;
        if (realPos >= 0) {
            realPos--;
        }
        if (realPos >= 3) {
            realPos--;
        }
        if (realPos >= 11) {
            realPos--;
        }
        SettingsValues.getInstance(getActivity()).setCurrentGesture(realPos);
        startActivityForResult(new Intent(view.getContext(), CommandSelectActivity.class), 0);
    }

    /* access modifiers changed from: package-private */
    public class CommandSimpleAdapterGestures extends SimpleAdapter {
        private int mOffset;

        CommandSimpleAdapterGestures(Context context, List<? extends Map<String, ?>> data, int offset) {
            super(context, data, R.layout.listitem_icondescriptionicon, new String[]{"title", "caption"}, new int[]{R.id.listitem_icondescriptionicon_text, R.id.listitem_icondescriptionicon_caption});
            this.mOffset = offset;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = super.getView(position, convertView, parent);
            ImageView icon = (ImageView) row.findViewById(R.id.listitem_icondescriptionicon_icon);
            ImageView icon2 = (ImageView) row.findViewById(R.id.listitem_icondescriptionicon_icon2);
            IconUtils.setMaxSizeForImageView(GesturesFragment.this.getActivity(), icon);
            new AsyncDrawableTask(icon, R.drawable.none) {
                /* class com.noname81.lmt.GesturesFragment.CommandSimpleAdapterGestures.AsyncTaskC05161 */

                /* access modifiers changed from: protected */
                @Override // com.noname81.lmt.AsyncDrawableTask
                public Drawable doInBackground(Void... params) {
                    return IconUtils.getIconForGesture(GesturesFragment.this.getActivity(), TouchServiceResult.names[CommandSimpleAdapterGestures.this.mOffset + position].toLowerCase());
                }
            }.execute(new Void[0]);
            IconUtils.setMaxSizeForImageView(GesturesFragment.this.getActivity(), icon2);
            new AsyncDrawableTask(icon2, R.drawable.none) {
                /* class com.noname81.lmt.GesturesFragment.CommandSimpleAdapterGestures.AsyncTaskC05172 */

                /* access modifiers changed from: protected */
                @Override // com.noname81.lmt.AsyncDrawableTask
                public Drawable doInBackground(Void... params) {
                    return IconUtils.getIconForAction(GesturesFragment.this.getActivity(), SettingsValues.getInstance(GesturesFragment.this.getActivity()).getGestureAction(CommandSimpleAdapterGestures.this.mOffset + position), null);
                }
            }.execute(new Void[0]);
            return row;
        }
    }
}