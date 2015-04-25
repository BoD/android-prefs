package org.jraf.android.prefs.sample.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import org.jraf.android.prefs.sample.MainPrefs;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(org.jraf.android.prefs.sample.R.layout.main);

        MainPrefs mainPrefs = MainPrefs.get(this);
        mainPrefs.edit().putLogin("john").putPassword("p4Ssw0Rd").commit();
        Log.d(TAG, "login=" + mainPrefs.getLogin());
    }
}
