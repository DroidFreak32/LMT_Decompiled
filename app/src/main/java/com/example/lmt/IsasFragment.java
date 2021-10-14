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

public class IsasFragment extends Fragment implements AdapterView.OnItemClickListener {
    public static final int offsetIsasBottom = 0;
    public static final int offsetIsasLeft = 6;
    private static final int offsetIsasRight = 9;
    public static final int offsetIsasTop = 3;
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
        List<Map<String, ?>> isasBottom = new LinkedList<>();
        isasBottom.add(SeparatedListAdapter.createItem("Isa 1", getString(R.string.isas_isa_bottom_left)));
        isasBottom.add(SeparatedListAdapter.createItem("Isa 2", getString(R.string.isas_isa_bottom_center)));
        isasBottom.add(SeparatedListAdapter.createItem("Isa 3", getString(R.string.isas_isa_bottom_right)));
        List<Map<String, ?>> isasTop = new LinkedList<>();
        isasTop.add(SeparatedListAdapter.createItem("Isa 4", getString(R.string.isas_isa_top_left)));
        isasTop.add(SeparatedListAdapter.createItem("Isa 5", getString(R.string.isas_isa_top_center)));
        isasTop.add(SeparatedListAdapter.createItem("Isa 6", getString(R.string.isas_isa_top_right)));
        List<Map<String, ?>> isasLeft = new LinkedList<>();
        isasLeft.add(SeparatedListAdapter.createItem("Isa 7", getString(R.string.isas_isa_left_top)));
        isasLeft.add(SeparatedListAdapter.createItem("Isa 8", getString(R.string.isas_isa_left_center)));
        isasLeft.add(SeparatedListAdapter.createItem("Isa 9", getString(R.string.isas_isa_left_bottom)));
        List<Map<String, ?>> isasRight = new LinkedList<>();
        isasRight.add(SeparatedListAdapter.createItem("Isa 10", getString(R.string.isas_isa_right_top)));
        isasRight.add(SeparatedListAdapter.createItem("Isa 11", getString(R.string.isas_isa_right_center)));
        isasRight.add(SeparatedListAdapter.createItem("Isa 12", getString(R.string.isas_isa_right_bottom)));
        this.mAdapter = new SeparatedListAdapter(getActivity());
        this.mAdapter.addSection(getString(R.string.isas_isa_areas_bottom), new CommandSimpleAdapterIsas(getActivity(), isasBottom, 0));
        this.mAdapter.addSection(getString(R.string.isas_isa_areas_top), new CommandSimpleAdapterIsas(getActivity(), isasTop, 3));
        this.mAdapter.addSection(getString(R.string.isas_isa_areas_left), new CommandSimpleAdapterIsas(getActivity(), isasLeft, 6));
        this.mAdapter.addSection(getString(R.string.isas_isa_areas_right), new CommandSimpleAdapterIsas(getActivity(), isasRight, 9));
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
        if (realPos >= 6) {
            realPos--;
        }
        if (realPos >= 9) {
            realPos--;
        }
        SettingsValues.getInstance(getActivity()).setCurrentIsa(realPos);
        startActivityForResult(new Intent(view.getContext(), CommandSelectActivity.class), 0);
    }

    /* access modifiers changed from: package-private */
    public class CommandSimpleAdapterIsas extends SimpleAdapter {
        private int mOffset;

        CommandSimpleAdapterIsas(Context context, List<? extends Map<String, ?>> data, int offset) {
            super(context, data, R.layout.listitem_icondescriptionicon, new String[]{"title", "caption"}, new int[]{R.id.listitem_icondescriptionicon_text, R.id.listitem_icondescriptionicon_caption});
            this.mOffset = offset;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = super.getView(position, convertView, parent);
            ImageView icon = (ImageView) row.findViewById(R.id.listitem_icondescriptionicon_icon);
            ImageView icon2 = (ImageView) row.findViewById(R.id.listitem_icondescriptionicon_icon2);
            IconUtils.setMaxSizeForImageView(IsasFragment.this.getActivity(), icon);
            new AsyncDrawableTask(icon, R.drawable.none) {
                /* class com.noname81.lmt.IsasFragment.CommandSimpleAdapterIsas.AsyncTaskC05191 */

                /* access modifiers changed from: protected */
                @Override // com.noname81.lmt.AsyncDrawableTask
                public Drawable doInBackground(Void... params) {
                    return IconUtils.getIconForISA(IsasFragment.this.getActivity());
                }
            }.execute(new Void[0]);
            IconUtils.setMaxSizeForImageView(IsasFragment.this.getActivity(), icon2);
            new AsyncDrawableTask(icon2, R.drawable.none) {
                /* class com.noname81.lmt.IsasFragment.CommandSimpleAdapterIsas.AsyncTaskC05202 */

                /* access modifiers changed from: protected */
                @Override // com.noname81.lmt.AsyncDrawableTask
                public Drawable doInBackground(Void... params) {
                    return IconUtils.getIconForAction(IsasFragment.this.getActivity(), SettingsValues.getInstance(IsasFragment.this.getActivity()).getIsaAction(CommandSimpleAdapterIsas.this.mOffset + position), null);
                }
            }.execute(new Void[0]);
            return row;
        }
    }
}