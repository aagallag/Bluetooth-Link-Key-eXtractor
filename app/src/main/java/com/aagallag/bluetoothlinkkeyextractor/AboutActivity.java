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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView aboutTextView = (TextView) findViewById(R.id.aboutText);
        aboutTextView.setText(buildAboutText());
    }

    private String buildAboutText() {
        String aboutMsg = "";
        aboutMsg += String.format("Author: %s\n\n", this.getString(R.string.author_name));
        aboutMsg += String.format("Author Email: \n%s\n\n", this.getString(R.string.author_email));
        aboutMsg += String.format("Github URL: \n%s\n\n", this.getString(R.string.github_url));
        aboutMsg += String.format("Issues URL: \n%s\n\n", this.getString(R.string.issues_url));
        aboutMsg += String.format("License: \n%s\n\n", this.getString(R.string.license));
        return aboutMsg;
    }
}
