/**
 * LKX - Bluetooth Link-Key eXtractor
 *
 * Copyright (C) 2016 Aaron Gallagher <aaron.b.gallagher@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.aagallag.bluetoothlinkkeyextractor;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class BtDeviceRecordListAdapter extends BaseExpandableListAdapter {
    static private int CHILD_POS_BD_ADDR = 0;
    static private int CHILD_POS_LINKKEY = 1;
    static private int CHILD_COUNT = 2;

    private Activity mContext;
    private List<BtDeviceRecord> mBtDeviceRecordList;

    public BtDeviceRecordListAdapter(Activity context, List<BtDeviceRecord> btDeviceRecordList) {
        mContext = context;
        mBtDeviceRecordList = btDeviceRecordList;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        BtDeviceRecord record = mBtDeviceRecordList.get(groupPosition);
        String child;
        if (childPosition == CHILD_POS_BD_ADDR) {
            child = record.mBdAddr;
        } else if (childPosition == CHILD_POS_LINKKEY) {
            child = record.mLinkKey;
        } else {
            child = null;
        }
        return child;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View listView, ViewGroup parent) {
        final String childText = getChildPrettyTxt(groupPosition, childPosition);

        LayoutInflater inflater = mContext.getLayoutInflater();

        if (listView == null) {
            listView = inflater.inflate(R.layout.child_list_item, null);
        }

        TextView item = (TextView) listView.findViewById(R.id.textView1);

        item.setText(childText);
        return listView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return CHILD_COUNT;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mBtDeviceRecordList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return mBtDeviceRecordList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View listView, ViewGroup parent) {
        BtDeviceRecord groupRecord = (BtDeviceRecord) getGroup(groupPosition);
        String groupText = groupRecord.mName;

        if (listView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listView = inflater.inflate(R.layout.parent_list_item, null);
        }
        TextView item = (TextView) listView.findViewById(R.id.textView1);
        item.setText(groupText);
        return listView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private String getChildPrettyTxt(final int groupPosition, final int childPosition) {
        final String childText = (String) getChild(groupPosition, childPosition);
        String prettyChildText;
        if (childPosition == CHILD_POS_BD_ADDR) {
            prettyChildText = String.format("BD Addr: [%s]", childText);
        } else if (childPosition == CHILD_POS_LINKKEY) {
            prettyChildText = String.format("Link Key: [%s]", childText);
        } else {
            prettyChildText = "Error";
        }

        return prettyChildText;
    }
}