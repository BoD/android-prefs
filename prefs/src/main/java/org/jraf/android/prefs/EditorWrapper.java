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
package org.jraf.android.prefs;

import java.util.Set;

import android.content.SharedPreferences;

public class EditorWrapper implements SharedPreferences.Editor {
    private final SharedPreferences.Editor mWrapped;

    public EditorWrapper(SharedPreferences.Editor wrapped) {
        mWrapped = wrapped;
    }

    public EditorWrapper putString(String key, String value) {
        mWrapped.putString(key, value);
        return this;
    }

    public EditorWrapper putStringSet(String key, Set<String> values) {
        mWrapped.putStringSet(key, values);
        return this;
    }

    public EditorWrapper putInt(String key, int value) {
        mWrapped.putInt(key, value);
        return this;
    }

    public EditorWrapper putLong(String key, long value) {
        mWrapped.putLong(key, value);
        return this;
    }

    public EditorWrapper putFloat(String key, float value) {
        mWrapped.putFloat(key, value);
        return this;
    }

    public EditorWrapper putBoolean(String key, boolean value) {
        mWrapped.putBoolean(key, value);
        return this;
    }

    public EditorWrapper remove(String key) {
        mWrapped.remove(key);
        return this;
    }

    public EditorWrapper clear() {
        mWrapped.clear();
        return this;
    }

    public boolean commit() {
        return mWrapped.commit();
    }

    public void apply() {
        mWrapped.apply();
    }
}
