package org.jraf.android.prefwrapper;

import java.util.Map;
import java.util.Set;

import android.content.SharedPreferences;

/**
 * Wrapper for {@link SharedPreferences}.
 */
public class SharedPreferencesWrapper {
    private final SharedPreferences mWrapped;

    public SharedPreferencesWrapper(SharedPreferences sharedPreferences) {
        mWrapped = sharedPreferences;
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
}
