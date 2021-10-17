package com.example.lmt;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

class SeparatedListAdapter extends BaseAdapter {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final String ITEM_CAPTION = "caption";
    static final String ITEM_TITLE = "title";
    private static final int TYPE_SECTION_HEADER = 0;
    private final ArrayAdapter<String> headers;
    private final Map<String, Adapter> sections = new LinkedHashMap();

    static Map<String, ?> createItem(String title, String caption) {
        Map<String, String> item = new HashMap<>();
        item.put(ITEM_TITLE, title);
        item.put(ITEM_CAPTION, caption);
        return item;
    }

    SeparatedListAdapter(Context context) {
        this.headers = new ArrayAdapter<>(context, R.layout.listheader);
    }

    void addSection(String section, Adapter adapter) {
        this.headers.add(section);
        this.sections.put(section, adapter);
    }

    public Object getItem(int position) {
        for (Object section : this.sections.keySet()) {
            Adapter adapter = this.sections.get(section);
            int size = adapter.getCount() + 1;
            if (position == 0) {
                return section;
            }
            if (position < size) {
                return adapter.getItem(position - 1);
            }
            position -= size;
        }
        return null;
    }

    public int getCount() {
        int total = 0;
        for (Adapter adapter : this.sections.values()) {
            total += adapter.getCount() + 1;
        }
        return total;
    }

    public int getViewTypeCount() {
        int total = 1;
        for (Adapter adapter : this.sections.values()) {
            total += adapter.getViewTypeCount();
        }
        return total;
    }

    public int getItemViewType(int position) {
        int type = 1;
        for (Object section : this.sections.keySet()) {
            Adapter adapter = this.sections.get(section);
            int size = adapter.getCount() + 1;
            if (position == 0) {
                return 0;
            }
            if (position < size) {
                return adapter.getItemViewType(position - 1) + type;
            }
            position -= size;
            type += adapter.getViewTypeCount();
        }
        return -1;
    }

    public boolean areAllItemsSelectable() {
        return false;
    }

    public boolean isEnabled(int position) {
        return getItemViewType(position) != 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        int sectionnum = 0;
        for (Object section : this.sections.keySet()) {
            Adapter adapter = this.sections.get(section);
            int size = adapter.getCount() + 1;
            if (position == 0) {
                return this.headers.getView(sectionnum, convertView, parent);
            }
            if (position < size) {
                return adapter.getView(position - 1, convertView, parent);
            }
            position -= size;
            sectionnum++;
        }
        return null;
    }

    public long getItemId(int position) {
        return (long) position;
    }
}