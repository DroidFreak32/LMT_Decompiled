package com.noname81.lmt;

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
        LinkedList<Map<String, ?>> normalCommands = new LinkedList<>();
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[1], Action.captions[1]));
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[2], Action.captions[2]));
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[3], Action.captions[3]));
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[4], Action.captions[4]));
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[5], Action.captions[5]));
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[6], Action.captions[6]));
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[7], Action.captions[7]));
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[8], Action.captions[8]));
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[9], Action.captions[9]));
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[10], Action.captions[10]));
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[11], Action.captions[11]));
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[12], Action.captions[12]));
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[13], Action.captions[13]));
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[14], Action.captions[14]));
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[15], Action.captions[15]));
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[16], Action.captions[16]));
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[17], Action.captions[17]));
        normalCommands.add(SeparatedListAdapter.createItem(Action.names[18], Action.captions[18]));
        LinkedList<Map<String, ?>> toggleCommands = new LinkedList<>();
        toggleCommands.add(SeparatedListAdapter.createItem(Action.names[20], Action.captions[20]));
        toggleCommands.add(SeparatedListAdapter.createItem(Action.names[21], Action.captions[21]));
        toggleCommands.add(SeparatedListAdapter.createItem(Action.names[22], Action.captions[22]));
        toggleCommands.add(SeparatedListAdapter.createItem(Action.names[23], Action.captions[23]));
        toggleCommands.add(SeparatedListAdapter.createItem(Action.names[24], Action.captions[24]));
        LinkedList<Map<String, ?>> advancedCommands = new LinkedList<>();
        advancedCommands.add(SeparatedListAdapter.createItem(Action.names[26], Action.captions[26]));
        advancedCommands.add(SeparatedListAdapter.createItem(Action.names[27], Action.captions[27]));
        advancedCommands.add(SeparatedListAdapter.createItem(Action.names[28], Action.captions[28]));
        advancedCommands.add(SeparatedListAdapter.createItem(Action.names[29], Action.captions[29]));
        advancedCommands.add(SeparatedListAdapter.createItem(Action.names[30], Action.captions[30]));
        advancedCommands.add(SeparatedListAdapter.createItem(Action.names[31], Action.captions[31]));
        advancedCommands.add(SeparatedListAdapter.createItem(Action.names[32], Action.captions[32]));
        advancedCommands.add(SeparatedListAdapter.createItem(Action.names[33], Action.captions[33]));
        advancedCommands.add(SeparatedListAdapter.createItem(Action.names[34], Action.captions[34]));
        advancedCommands.add(SeparatedListAdapter.createItem(Action.names[35], Action.captions[35]));
        advancedCommands.add(SeparatedListAdapter.createItem(Action.names[36], Action.captions[36]));
        advancedCommands.add(SeparatedListAdapter.createItem(Action.names[37], Action.captions[37]));
        advancedCommands.add(SeparatedListAdapter.createItem(Action.names[38], Action.captions[38]));
        LinkedList<Map<String, ?>> appDrawerCommands = new LinkedList<>();
        appDrawerCommands.add(SeparatedListAdapter.createItem(Action.names[40], Action.captions[40]));
        appDrawerCommands.add(SeparatedListAdapter.createItem(Action.names[41], Action.captions[41]));
        appDrawerCommands.add(SeparatedListAdapter.createItem(Action.names[42], Action.captions[42]));
        LinkedList<Map<String, ?>> pieActionsCommands = new LinkedList<>();
        pieActionsCommands.add(SeparatedListAdapter.createItem(Action.names[44], Action.captions[44]));
        pieActionsCommands.add(SeparatedListAdapter.createItem(Action.names[45], Action.captions[45]));

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
            MultiSelectActivity.selectMode = MultiSelectActivity.SelectPackage;
            startActivityForResult(new Intent(view.getContext(), MultiSelectActivity.class), 0);
            finish();
        } else if (pos == 27) {
            MultiSelectActivity.selectMode = MultiSelectActivity.SelectPackageActivity;
            startActivityForResult(new Intent(view.getContext(), MultiSelectActivity.class), 0);
            finish();
        } else if (pos == 33) {
            MultiSelectActivity.selectMode = MultiSelectActivity.SelectShortcut;
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