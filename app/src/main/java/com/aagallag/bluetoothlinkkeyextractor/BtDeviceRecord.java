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

public class BtDeviceRecord {
    private String BTCONF_NAME = "Name = ";
    private String BTCONF_LINKKEY = "LinkKey = ";

    public String mBdAddr;
    public String mName;
    public String mLinkKey;

    public BtDeviceRecord(String rawDeviceRecord) {
        String []lines = rawDeviceRecord.split("\n");
        for (String line: lines) {
            // [00:11:22:aa:bb:cc]
            if (line.startsWith("[")) {
                mBdAddr = line.replace("[", "").replace("]", "");;
            }

            // Name = devicename
            else if (line.contains(BTCONF_NAME)) {
                mName = line.replace(BTCONF_NAME, "");
            }

            // LinkeKey = 123456
            else if (line.contains(BTCONF_LINKKEY)) {
                mLinkKey = line.replace(BTCONF_LINKKEY, "");
            }
        }
    }

    public String toString() {
        return String.format("Name: %s\nBluetooth Device Address: %s\nLinkKey: %s",
                mName, mBdAddr, mLinkKey);
    }
}
