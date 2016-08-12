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

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick_readBtLinkKeys(View view) {
        String rawRecordTxt = null;
        try {
            rawRecordTxt = BtConfParser.readBtConfig();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (rawRecordTxt == null) {
            return;
        }

        List<BtDeviceRecord> btDeviceRecordList = BtConfParser.getDeviceRecords(rawRecordTxt);
        populateUiWithBtDevices(btDeviceRecordList);
    }

    public void onClick_about(View view) {
        Intent aboutIntent = new Intent(this, AboutActivity.class);
        startActivity(aboutIntent);
    }

    private void populateUiWithBtDevices(List<BtDeviceRecord> btDeviceRecordList) {
        ExpandableListView expandablelistView =
                (ExpandableListView) findViewById(R.id.expandableListView1);
        final ExpandableListAdapter expListAdapter = new BtDeviceRecordListAdapter(this, btDeviceRecordList);
        expandablelistView.setAdapter(expListAdapter);

        expandablelistView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                final String selected = (String) expListAdapter.getChild(
                        groupPosition, childPosition);

                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("BtDeviceRecord", selected);
                clipboard.setPrimaryClip(clip);

                final String toastMsg = "Copied to clipboard: " + selected;
                Toast.makeText(getBaseContext(), toastMsg, Toast.LENGTH_LONG)
                        .show();

                return true;
            }
        });
    }
}