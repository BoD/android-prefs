package org.jraf.android.prefwrapper.sample.app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import org.jraf.android.prefwrapper.sample.MainPreferencesWrapper;
import org.jraf.android.prefwrapper.sample.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        MainPreferencesWrapper wrapper = new MainPreferencesWrapper(sharedPreferences);
        System.out.println(wrapper.getClass());
    }
}
