package com.example.lmt;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
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

public class PieFragment extends Fragment implements AdapterView.OnItemClickListener {
    public static final int offsetLevel1 = 0;
    public static final int offsetLevel2 = 10;
    private SeparatedListAdapter mAdapter;
    private int mCurrentPos;
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
        List<Map<String, ?>> level1 = new LinkedList<>();
        level1.add(SeparatedListAdapter.createItem("Pie item 1", getString(R.string.pie_level_1_item_1)));
        level1.add(SeparatedListAdapter.createItem("Pie item 1 longpress", getString(R.string.pie_level_1_item_1_longpress)));
        level1.add(SeparatedListAdapter.createItem("Pie item 2", getString(R.string.pie_level_1_item_2)));
        level1.add(SeparatedListAdapter.createItem("Pie item 2 longpress", getString(R.string.pie_level_1_item_2_longpress)));
        level1.add(SeparatedListAdapter.createItem("Pie item 3", getString(R.string.pie_level_1_item_3)));
        level1.add(SeparatedListAdapter.createItem("Pie item 3 longpress", getString(R.string.pie_level_1_item_3_longpress)));
        level1.add(SeparatedListAdapter.createItem("Pie item 4", getString(R.string.pie_level_1_item_4)));
        level1.add(SeparatedListAdapter.createItem("Pie item 4 longpress", getString(R.string.pie_level_1_item_4_longpress)));
        level1.add(SeparatedListAdapter.createItem("Pie item 5", getString(R.string.pie_level_1_item_5)));
        level1.add(SeparatedListAdapter.createItem("Pie item 5 longpress", getString(R.string.pie_level_1_item_5_longpress)));
        List<Map<String, ?>> level2 = new LinkedList<>();
        level2.add(SeparatedListAdapter.createItem("Pie item 1", getString(R.string.pie_level_2_item_1)));
        level2.add(SeparatedListAdapter.createItem("Pie item 1 longpress", getString(R.string.pie_level_2_item_1_longpress)));
        level2.add(SeparatedListAdapter.createItem("Pie item 2", getString(R.string.pie_level_2_item_2)));
        level2.add(SeparatedListAdapter.createItem("Pie item 2 longpress", getString(R.string.pie_level_2_item_2_longpress)));
        level2.add(SeparatedListAdapter.createItem("Pie item 3", getString(R.string.pie_level_2_item_3)));
        level2.add(SeparatedListAdapter.createItem("Pie item 3 longpress", getString(R.string.pie_level_2_item_3_longpress)));
        level2.add(SeparatedListAdapter.createItem("Pie item 4", getString(R.string.pie_level_2_item_4)));
        level2.add(SeparatedListAdapter.createItem("Pie item 4 longpress", getString(R.string.pie_level_2_item_4_longpress)));
        level2.add(SeparatedListAdapter.createItem("Pie item 5", getString(R.string.pie_level_2_item_5)));
        level2.add(SeparatedListAdapter.createItem("Pie item 5 longpress", getString(R.string.pie_level_2_item_5_longpress)));
        level2.add(SeparatedListAdapter.createItem("Pie item 6", getString(R.string.pie_level_2_item_6)));
        level2.add(SeparatedListAdapter.createItem("Pie item 6 longpress", getString(R.string.pie_level_2_item_6_longpress)));
        level2.add(SeparatedListAdapter.createItem("Pie item 7", getString(R.string.pie_level_2_item_7)));
        level2.add(SeparatedListAdapter.createItem("Pie item 7 longpress", getString(R.string.pie_level_2_item_7_longpress)));
        this.mAdapter = new SeparatedListAdapter(getActivity());
        this.mAdapter.addSection(getString(R.string.pie_level_1), new CommandSimpleAdapter(getActivity(), level1, 0));
        this.mAdapter.addSection(getString(R.string.pie_level_2), new CommandSimpleAdapter(getActivity(), level2, 10));
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
        if (realPos >= 10) {
            realPos--;
        }
        SettingsValues.getInstance(getActivity()).setCurrentPie(realPos);
        startActivityForResult(new Intent(view.getContext(), CommandSelectActivity.class), 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (requestCode == 1 && resultCode == -1 && PermissionChecker.getInstance().checkAndRequestExternalStorageWritePermission(getActivity(), true)) {
            IconUtils.saveImageToFile(IconUtils.getScaledBitmapFromUri(getActivity(), imageReturnedIntent.getData(), 150), SettingsValues.getInstance(getActivity()).loadResourceSearchPath(), IconUtils.getNamePie(this.mCurrentPos));
            SettingsValues.getInstance(getActivity()).restartServiceIfRequired();
        }
    }

    /* access modifiers changed from: package-private */
    public class CommandSimpleAdapter extends SimpleAdapter {
        private int mOffset;

        CommandSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int offset) {
            super(context, data, R.layout.listitem_icondescriptionicon, new String[]{"title", "caption"}, new int[]{R.id.listitem_icondescriptionicon_text, R.id.listitem_icondescriptionicon_caption});
            this.mOffset = offset;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = super.getView(position, convertView, parent);
            ImageView icon = (ImageView) row.findViewById(R.id.listitem_icondescriptionicon_icon);
            ImageView icon2 = (ImageView) row.findViewById(R.id.listitem_icondescriptionicon_icon2);
            IconUtils.setMaxSizeForImageView(PieFragment.this.getActivity(), icon);
            new AsyncDrawableTask(icon, R.drawable.none) {
                /* class com.noname81.lmt.PieFragment.CommandSimpleAdapter.AsyncTaskC05251 */

                /* access modifiers changed from: protected */
                @Override // com.noname81.lmt.AsyncDrawableTask
                public Drawable doInBackground(Void... params) {
                    if (position % 2 > 0) {
                        return IconUtils.getIconForPieLongPress(PieFragment.this.getActivity());
                    }
                    return IconUtils.getIconForPieShortPress(PieFragment.this.getActivity());
                }
            }.execute(new Void[0]);
            IconUtils.setMaxSizeForImageView(PieFragment.this.getActivity(), icon2);
            new AsyncDrawableTask(icon2, R.drawable.none) {
                /* class com.noname81.lmt.PieFragment.CommandSimpleAdapter.AsyncTaskC05282 */

                /* access modifiers changed from: protected */
                @Override // com.noname81.lmt.AsyncDrawableTask
                public Drawable doInBackground(Void... params) {
                    return IconUtils.getIconForAction(PieFragment.this.getActivity(), SettingsValues.getInstance(PieFragment.this.getActivity()).getPieAction(CommandSimpleAdapter.this.mOffset + position), IconUtils.getNamePie(CommandSimpleAdapter.this.mOffset + position));
                }
            }.execute(new Void[0]);
//TODO            icon2.setOnClickListener(new View.OnClickListener(this.mOffset + position) {
            icon2.setOnClickListener(new View.OnClickListener() {
                /* class com.noname81.lmt.PieFragment.CommandSimpleAdapter.AnonymousClass1OnClickListenerWithPosition */
                private int mPosition;

                {
                    this.mPosition = position;
                }

                public void onClick(View arg0) {
                    PieFragment.this.mCurrentPos = this.mPosition;
                    final String path = SettingsValues.getInstance(PieFragment.this.getActivity()).loadResourceSearchPath();
                    final String fileName = IconUtils.getNamePie(PieFragment.this.mCurrentPos);
                    if (FileUtils.isFileAvailable(path, fileName)) {
                        new AlertDialog.Builder(PieFragment.this.getActivity()).setTitle("Icon selection").setMessage("Do you want to delete the current icon or set a new one?").setPositiveButton("New", new DialogInterface.OnClickListener() {
                            /* class com.noname81.lmt.PieFragment.CommandSimpleAdapter.AnonymousClass1OnClickListenerWithPosition.DialogInterface$OnClickListenerC05272 */

                            public void onClick(DialogInterface dialog, int which) {
                                PieFragment.this.startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 1);
                            }
                        }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            /* class com.noname81.lmt.PieFragment.CommandSimpleAdapter.AnonymousClass1OnClickListenerWithPosition.DialogInterface$OnClickListenerC05261 */

                            public void onClick(DialogInterface dialog, int which) {
                                FileUtils.deleteFile(path, fileName);
                                PieFragment.this.mAdapter.notifyDataSetChanged();
                                SettingsValues.getInstance(PieFragment.this.getActivity()).restartServiceIfRequired();
                            }
                        }).show();
                        return;
                    }
                    PieFragment.this.startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 1);
                }
            });
            return row;
        }
    }
}