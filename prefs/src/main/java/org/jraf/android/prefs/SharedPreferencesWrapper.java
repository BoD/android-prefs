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
package org.jraf.android.prefs;

import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

/**
 * Wrapper for {@link SharedPreferences}.
 */
public class SharedPreferencesWrapper implements SharedPreferences {
    private final SharedPreferences mWrapped;

    public SharedPreferencesWrapper(SharedPreferences wrapped) {
        mWrapped = wrapped;
    }

    public Map<String, ?> getAll() {
        return mWrapped.getAll();
    }

    public String getString(String key, String defValue) {
        return mWrapped.getString(key, defValue);
    }

    public Set<String> getStringSet(String key, Set<String> defValues) {
        return mWrapped.getStringSet(key, defValues);
    }

    public int getInt(String key, int defValue) {
        return mWrapped.getInt(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return mWrapped.getLong(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return mWrapped.getFloat(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mWrapped.getBoolean(key, defValue);
    }

    public boolean contains(String key) {
        return mWrapped.contains(key);
    }

    public SharedPreferences.Editor edit() {
        return mWrapped.edit();
    }

    public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        mWrapped.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        mWrapped.unregisterOnSharedPreferenceChangeListener(listener);
    }

    /**
     * Remove <em>all</em> values from the preferences.
     */
    public void clear() {
        edit().clear().apply();
    }
}
