package org.jraf.android.prefwrapper.sample.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import org.jraf.android.prefwrapper.sample.MainPreferencesWrapper;
import org.jraf.android.prefwrapper.sample.R;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        MainPreferencesWrapper mainPrefs = MainPreferencesWrapper.get(this);
        mainPrefs.edit().putLogin("john").putPassword("p4Ssw0Rd").commit();
        Log.d(TAG, "login=" + mainPrefs.getLogin());
    }
}
