package org.jraf.android.prefs.sample;

import java.util.Set;

import android.content.Context;

import org.jraf.android.prefs.DefaultInt;
import org.jraf.android.prefs.DefaultStringSet;
import org.jraf.android.prefs.Prefs;

@Prefs(fileName = "settings", fileMode = Context.MODE_PRIVATE)
public class Settings {
    @DefaultInt(0xFFBB00DD)
    Integer preferredColor;

    /**
     * The week days that the user prefers.
     */
    @DefaultStringSet({"Friday", "Saturday"})
    Set<String> weekDays;

}
