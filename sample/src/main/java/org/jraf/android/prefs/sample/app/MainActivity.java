/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 *
 * Copyright (C) 2015 Benoit 'BoD' Lubek (BoD@JRAF.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jraf.android.prefs.sample.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import org.jraf.android.prefs.sample.prefs.MainPrefs;
import org.jraf.android.prefs.sample.prefs.SettingsPrefs;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(org.jraf.android.prefs.sample.R.layout.main);

        MainPrefs mainPrefs = MainPrefs.get(this);
        mainPrefs.edit().putLogin("john").putPassword("p4Ssw0Rd").commit();
        mainPrefs.putAge(null);
        Log.d(TAG, "login=" + mainPrefs.getLogin());
        Log.d(TAG, "age=" + mainPrefs.getAge());
        Log.d(TAG, "premium=" + mainPrefs.isPremium());
        Log.d(TAG, "mainPrefs=" + mainPrefs.getAll());

        SettingsPrefs settingsPrefs = SettingsPrefs.get(this);
        settingsPrefs.putPreferredColor(0xFFFFFF);
        Log.d(TAG, "preferredColor=" + settingsPrefs.getPreferredColor());
        Log.d(TAG, "settingsPrefs=" + settingsPrefs.getAll());

    }
}
