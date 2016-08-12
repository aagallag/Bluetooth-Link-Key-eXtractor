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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BtConfParser {
    static String BTCONF = "/data/misc/bluedroid/bt_config.conf";

    static public List<BtDeviceRecord> getDeviceRecords(String rawBtConf) {
        List<String> records = splitDeviceRecords(rawBtConf);
        List<BtDeviceRecord> deviceRecords = new ArrayList<>();

        //Skip the first two records, they are general config information
        for (int i=2; i < records.size(); i++) {
            String rawRecord = records.get(i);
            BtDeviceRecord record = new BtDeviceRecord(rawRecord);
            deviceRecords.add(record);
        }

        return deviceRecords;
    }

    static private List<String> splitDeviceRecords(String rawBtConf) {
        List<String> rawRecordStr = new ArrayList<>();
        String[] lines = rawBtConf.split("\n");

        //Iterate through each line
        //Each device is separated by a blank line
        String record = "";
        for (String line: lines) {
            if (line.equals("")) {
                if (!record.equals("")) {
                    //Add the device record to the list and reset current record string
                    rawRecordStr.add(record.trim());
                    record = "";
                }
            }

            //Add this line to the current record
            record += "\n" + line;
        }

        //Add the last device record to the list and return
        rawRecordStr.add(record.trim());
        return rawRecordStr;
    }

    static public String readBtConfig() throws IOException, InterruptedException {
        // Execute root
        Process p = Runtime.getRuntime().exec("su");

        // Get the input/output for the shell
        DataOutputStream os = new DataOutputStream(p.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

        // Read the bt_config.conf
        os.writeBytes("cat " + BTCONF + '\n');

        // Exit the root shell, and wait...
        os.writeBytes("exit\n");
        os.flush();
        p.waitFor();

        // Read from the BufferedReader
        String btconfig = "";
        String line;
        while ((line = in.readLine()) != null) {
            btconfig = btconfig + '\n' + line;
        }

        return btconfig;
    }
}
