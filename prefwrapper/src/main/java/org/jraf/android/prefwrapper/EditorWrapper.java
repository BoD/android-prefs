package org.jraf.android.prefwrapper;

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
