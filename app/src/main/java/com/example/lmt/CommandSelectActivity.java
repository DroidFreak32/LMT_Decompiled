package com.example.lmt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

public class CommandSelectActivity extends Activity implements AdapterView.OnItemClickListener {
    private int mPos = 0;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        List<Map<String, ?>> normalCommands = new LinkedList<>();
        int i = 0 + 1;
        int i2 = i + 1;
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[i], Action.captions[i]));
        int i3 = i2 + 1;
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[i2], Action.captions[i2]));
        int i4 = i3 + 1;
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[i3], Action.captions[i3]));
        int i5 = i4 + 1;
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[i4], Action.captions[i4]));
        int i6 = i5 + 1;
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[i5], Action.captions[i5]));
        int i7 = i6 + 1;
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[i6], Action.captions[i6]));
        int i8 = i7 + 1;
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[i7], Action.captions[i7]));
        int i9 = i8 + 1;
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[i8], Action.captions[i8]));
        int i10 = i9 + 1;
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[i9], Action.captions[i9]));
        int i11 = i10 + 1;
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[i10], Action.captions[i10]));
        int i12 = i11 + 1;
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[i11], Action.captions[i11]));
        int i13 = i12 + 1;
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[i12], Action.captions[i12]));
        int i14 = i13 + 1;
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[i13], Action.captions[i13]));
        int i15 = i14 + 1;
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[i14], Action.captions[i14]));
        int i16 = i15 + 1;
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[i15], Action.captions[i15]));
        int i17 = i16 + 1;
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[i16], Action.captions[i16]));
        int i18 = i17 + 1;
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[i17], Action.captions[i17]));
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[i18], Action.captions[i18]));
        List<Map<String, ?>> toggleCommands = new LinkedList<>();
        int i19 = i18 + 1 + 1;
        int i20 = i19 + 1;
        toggleCommands.add(SeparatedListAdapter.createItem(Action.names[i19], Action.captions[i19]));
        int i21 = i20 + 1;
        toggleCommands.add(SeparatedListAdapter.createItem(Action.names[i20], Action.captions[i20]));
        int i22 = i21 + 1;
        toggleCommands.add(SeparatedListAdapter.createItem(Action.names[i21], Action.captions[i21]));
        int i23 = i22 + 1;
        toggleCommands.add(SeparatedListAdapter.createItem(Action.names[i22], Action.captions[i22]));
        toggleCommands.add(SeparatedListAdapter.createItem(Action.names[i23], Action.captions[i23]));
        List<Map<String, ?>> advancedCommands = new LinkedList<>();
        int i24 = i23 + 1 + 1;
        int i25 = i24 + 1;
        advancedCommands.add(SeparatedListAdapter.createItem(Action.names[i24], Action.captions[i24]));
        int i26 = i25 + 1;
        advancedCommands.add(SeparatedListAdapter.createItem(Action.names[i25], Action.captions[i25]));
        int i27 = i26 + 1;
        advancedCommands.add(SeparatedListAdapter.createItem(Action.names[i26], Action.captions[i26]));
        int i28 = i27 + 1;
        advancedCommands.add(SeparatedListAdapter.createItem(Action.names[i27], Action.captions[i27]));
        int i29 = i28 + 1;
        advancedCommands.add(SeparatedListAdapter.createItem(Action.names[i28], Action.captions[i28]));
        int i30 = i29 + 1;
        advancedCommands.add(SeparatedListAdapter.createItem(Action.names[i29], Action.captions[i29]));
        int i31 = i30 + 1;
        advancedCommands.add(SeparatedListAdapter.createItem(Action.names[i30], Action.captions[i30]));
        int i32 = i31 + 1;
        advancedCommands.add(SeparatedListAdapter.createItem(Action.names[i31], Action.captions[i31]));
        int i33 = i32 + 1;
        advancedCommands.add(SeparatedListAdapter.createItem(Action.names[i32], Action.captions[i32]));
        int i34 = i33 + 1;
        advancedCommands.add(SeparatedListAdapter.createItem(Action.names[i33], Action.captions[i33]));
        int i35 = i34 + 1;
        advancedCommands.add(SeparatedListAdapter.createItem(Action.names[i34], Action.captions[i34]));
        int i36 = i35 + 1;
        advancedCommands.add(SeparatedListAdapter.createItem(Action.names[i35], Action.captions[i35]));
        advancedCommands.add(SeparatedListAdapter.createItem(Action.names[i36], Action.captions[i36]));
        List<Map<String, ?>> appDrawerCommands = new LinkedList<>();
        int i37 = i36 + 1 + 1;
        int i38 = i37 + 1;
        appDrawerCommands.add(SeparatedListAdapter.createItem(Action.names[i37], Action.captions[i37]));
        int i39 = i38 + 1;
        appDrawerCommands.add(SeparatedListAdapter.createItem(Action.names[i38], Action.captions[i38]));
        appDrawerCommands.add(SeparatedListAdapter.createItem(Action.names[i39], Action.captions[i39]));
        List<Map<String, ?>> pieActionsCommands = new LinkedList<>();
        int i40 = i39 + 1 + 1;
        int i41 = i40 + 1;
        pieActionsCommands.add(SeparatedListAdapter.createItem(Action.names[i40], Action.captions[i40]));
        pieActionsCommands.add(SeparatedListAdapter.createItem(Action.names[i41], Action.captions[i41]));
        SeparatedListAdapter adapter = new SeparatedListAdapter(this);
        adapter.addSection(getString(R.string.commands_normal_commands), new CommandSelectSimpleAdapter(this, normalCommands, 0));
        adapter.addSection(getString(R.string.commands_toogle_commands), new CommandSelectSimpleAdapter(this, toggleCommands, 19));
        adapter.addSection(getString(R.string.commands_advanced_commands), new CommandSelectSimpleAdapter(this, advancedCommands, 25));
        adapter.addSection(getString(R.string.commands_app_drawer_commands), new CommandSelectSimpleAdapter(this, appDrawerCommands, 39));
        adapter.addSection(getString(R.string.commands_pie_action_commands), new CommandSelectSimpleAdapter(this, pieActionsCommands, 43));
        ListView list = new ListView(this);
        list.setAdapter((ListAdapter) adapter);
        list.setOnItemClickListener(this);
        list.setDividerHeight(0);
        setContentView(list);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long arg3) {
        this.mPos = pos;
        if (pos == 2) {
            MultiSelectActivity.AppSelectMode = 0;
            startActivityForResult(new Intent(view.getContext(), MultiSelectActivity.class), 0);
            finish();
        } else if (pos == 27) {
            MultiSelectActivity.AppSelectMode = 1;
            startActivityForResult(new Intent(view.getContext(), MultiSelectActivity.class), 0);
            finish();
        } else if (pos == 33) {
            MultiSelectActivity.AppSelectMode = 5;
            startActivityForResult(new Intent(view.getContext(), MultiSelectActivity.class), 0);
            finish();
        } else if ((pos <= 25 || pos >= 30) && pos != 32) {
            SettingsValues.getInstance(getApplicationContext()).setCurrentAction(this, new Action(this.mPos));
            onBackPressed();
        } else {
            String text = BuildConfig.FLAVOR;
            Action action = SettingsValues.getInstance(getApplicationContext()).getCurrentAction();
            if (action.getType() == this.mPos) {
                text = action.getString();
            }
            String string = getString(R.string.dialog_input);
            new InputDialog(this, string, getString(R.string.dialog_define_the) + " " + Action.names[this.mPos] + " " + getString(R.string.dialog_to_be_executed), text, false) {
                /* class com.noname81.lmt.CommandSelectActivity.AlertDialog$BuilderC05131 */

                @Override // com.noname81.lmt.InputDialog
                public boolean onOkClicked(String input) {
                    SettingsValues.getInstance(CommandSelectActivity.this.getApplicationContext()).setCurrentAction(CommandSelectActivity.this.getParent(), new Action(CommandSelectActivity.this.mPos, input));
                    CommandSelectActivity.this.onBackPressed();
                    return true;
                }
            }.show();
        }
    }

    class CommandSelectSimpleAdapter extends SimpleAdapter {
        private int mOffset;

        CommandSelectSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int offset) {
            super(context, data, R.layout.listitem_icondescriptionicon, new String[]{"title", "caption"}, new int[]{R.id.listitem_icondescriptionicon_text, R.id.listitem_icondescriptionicon_caption});
            this.mOffset = offset;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = super.getView(position, convertView, parent);
            ImageView icon = (ImageView) row.findViewById(R.id.listitem_icondescriptionicon_icon);
            ImageView icon2 = (ImageView) row.findViewById(R.id.listitem_icondescriptionicon_icon2);
            IconUtils.setMaxSizeForImageView(CommandSelectActivity.this.getApplicationContext(), icon);
            new AsyncDrawableTask(icon, R.drawable.none) {
                /* class com.noname81.lmt.CommandSelectActivity.CommandSelectSimpleAdapter.AsyncTaskC05141 */

                /* access modifiers changed from: protected */
                @Override // com.noname81.lmt.AsyncDrawableTask
                public Drawable doInBackground(Void... params) {
                    return IconUtils.getIconForAction(
                            CommandSelectActivity.this.getApplicationContext(),new Action(
                                    CommandSelectSimpleAdapter.this.mOffset + position + 1), null);
                }
            }.execute(new Void[0]);
            if (this.mOffset + position + 1 == SettingsValues.getInstance(CommandSelectActivity.this.getApplicationContext()).getCurrentAction().getType()) {
                icon2.setImageDrawable(IconUtils.getIconForOK(CommandSelectActivity.this.getApplicationContext()));
            } else {
                icon2.setImageDrawable(null);
            }
            return row;
        }
    }
}