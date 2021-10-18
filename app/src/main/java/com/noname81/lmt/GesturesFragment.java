package com.noname81.lmt;

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
            this.mListView.setAdapter(this.mAdapter);
        }
        super.onResume();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        List<Map<String, ?>> singleTouchGestures = new LinkedList<>();
        List<Map<String, ?>> swipeGestures = new LinkedList<>();
        List<Map<String, ?>> dualTouchGestures = new LinkedList<>();

        singleTouchGestures.add(SeparatedListAdapter.createItem(TouchServiceResult.names[TouchServiceResult.Square], TouchServiceResult.captions[TouchServiceResult.Square]));
        singleTouchGestures.add(SeparatedListAdapter.createItem(TouchServiceResult.names[TouchServiceResult.Diamond], TouchServiceResult.captions[TouchServiceResult.Diamond]));
        singleTouchGestures.add(SeparatedListAdapter.createItem(TouchServiceResult.names[TouchServiceResult.Delete], TouchServiceResult.captions[TouchServiceResult.Delete]));
        swipeGestures.add(SeparatedListAdapter.createItem(TouchServiceResult.names[TouchServiceResult.SwipeRightDouble], TouchServiceResult.captions[TouchServiceResult.SwipeRightDouble]));
        swipeGestures.add(SeparatedListAdapter.createItem(TouchServiceResult.names[TouchServiceResult.SwipeLeftDouble], TouchServiceResult.captions[TouchServiceResult.SwipeLeftDouble]));
        swipeGestures.add(SeparatedListAdapter.createItem(TouchServiceResult.names[TouchServiceResult.SwipeUpDouble], TouchServiceResult.captions[TouchServiceResult.SwipeUpDouble]));
        swipeGestures.add(SeparatedListAdapter.createItem(TouchServiceResult.names[TouchServiceResult.SwipeDownDouble], TouchServiceResult.captions[TouchServiceResult.SwipeDownDouble]));
        swipeGestures.add(SeparatedListAdapter.createItem(TouchServiceResult.names[TouchServiceResult.SwipeRightLeftDouble], TouchServiceResult.captions[TouchServiceResult.SwipeRightLeftDouble]));
        swipeGestures.add(SeparatedListAdapter.createItem(TouchServiceResult.names[TouchServiceResult.SwipeLeftRightDouble], TouchServiceResult.captions[TouchServiceResult.SwipeLeftRightDouble]));
        swipeGestures.add(SeparatedListAdapter.createItem(TouchServiceResult.names[TouchServiceResult.SwipeUpDownDouble], TouchServiceResult.captions[TouchServiceResult.SwipeUpDownDouble]));
        swipeGestures.add(SeparatedListAdapter.createItem(TouchServiceResult.names[TouchServiceResult.SwipeDownUpDouble], TouchServiceResult.captions[TouchServiceResult.SwipeDownUpDouble]));
        dualTouchGestures.add(SeparatedListAdapter.createItem(TouchServiceResult.names[TouchServiceResult.QuestionmarkDouble], TouchServiceResult.captions[TouchServiceResult.QuestionmarkDouble]));
        dualTouchGestures.add(SeparatedListAdapter.createItem(TouchServiceResult.names[TouchServiceResult.SquareDouble], TouchServiceResult.captions[TouchServiceResult.SquareDouble]));
        dualTouchGestures.add(SeparatedListAdapter.createItem(TouchServiceResult.names[TouchServiceResult.DiamondDouble], TouchServiceResult.captions[TouchServiceResult.DiamondDouble]));

        this.mAdapter = new SeparatedListAdapter(getActivity());
        this.mAdapter.addSection(getString(R.string.gestures_single_touch_gestures), new CommandSimpleAdapterGestures(getActivity(), singleTouchGestures, offsetSingleTouchGestures));
        this.mAdapter.addSection(getString(R.string.gestures_swipe_gestures), new CommandSimpleAdapterGestures(getActivity(), swipeGestures, offsetSwipeGestures));
        this.mAdapter.addSection(getString(R.string.gestures_dual_touch_gestures), new CommandSimpleAdapterGestures(getActivity(), dualTouchGestures, offsetDualTouchGestures));
        this.mListView = new ListView(getActivity());
        this.mListView.setAdapter(this.mAdapter);
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

    class CommandSimpleAdapterGestures extends SimpleAdapter {
        private int mOffset;

        CommandSimpleAdapterGestures(Context context, List<? extends Map<String, ?>> data, int offset) {
            super(context, data, R.layout.listitem_icondescriptionicon, new String[]{"title", "caption"}, new int[]{R.id.listitem_icondescriptionicon_text, R.id.listitem_icondescriptionicon_caption});
            this.mOffset = offset;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = super.getView(position, convertView, parent);
            ImageView icon = row.findViewById(R.id.listitem_icondescriptionicon_icon);
            ImageView icon2 = row.findViewById(R.id.listitem_icondescriptionicon_icon2);
            IconUtils.setMaxSizeForImageView(GesturesFragment.this.getActivity(), icon);
            new AsyncDrawableTask(icon, R.drawable.none) {
                /* class com.noname81.lmt.GesturesFragment.CommandSimpleAdapterGestures.AsyncTaskC05161 */

                /* access modifiers changed from: protected */
                @Override // com.noname81.lmt.AsyncDrawableTask
                public Drawable doInBackground(Void... params) {
                    return IconUtils.getIconForGesture(GesturesFragment.this.getActivity(), TouchServiceResult.names[CommandSimpleAdapterGestures.this.mOffset + position].toLowerCase());
                }
            }.execute();
            IconUtils.setMaxSizeForImageView(GesturesFragment.this.getActivity(), icon2);
            new AsyncDrawableTask(icon2, R.drawable.none) {
                /* class com.noname81.lmt.GesturesFragment.CommandSimpleAdapterGestures.AsyncTaskC05172 */

                /* access modifiers changed from: protected */
                @Override // com.noname81.lmt.AsyncDrawableTask
                public Drawable doInBackground(Void... params) {
                    return IconUtils.getIconForAction(GesturesFragment.this.getActivity(), SettingsValues.getInstance(GesturesFragment.this.getActivity()).getGestureAction(CommandSimpleAdapterGestures.this.mOffset + position), null);
                }
            }.execute();
            return row;
        }
    }
}