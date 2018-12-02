/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 *
 * Copyright (C) 2015-present Benoit 'BoD' Lubek (BoD@JRAF.org)
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
package org.jraf.android.prefs.sample.app

import android.app.Activity
import android.os.Bundle
import android.util.Log
import org.jraf.android.prefs.sample.R
import org.jraf.android.prefs.sample.prefs.MainPrefs
import org.jraf.android.prefs.sample.prefs.SettingsPrefs

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        val mainPrefs = MainPrefs.get(this)
        mainPrefs.edit().use {
            it.setLogin("john")
            it.setPassword("p4Ssw0Rd")
        }

        mainPrefs.age = null

        with(mainPrefs) {
            Log.d(TAG, "login=$login")
            Log.d(TAG, "age=$age")
            Log.d(TAG, "premium=$premium")
            Log.d(TAG, "mainPrefs=$all")
        }

        val settingsPrefs = SettingsPrefs.get(this)
        settingsPrefs.preferredColor = 0xFFFFFF
        with(settingsPrefs) {
            Log.d(TAG, "preferredColor=$preferredColor")
            Log.d(TAG, "settingsPrefs=$all")
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.name
    }
}
