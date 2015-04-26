package org.jraf.android.prefs.sample.prefs;

import org.jraf.android.prefs.DefaultBoolean;
import org.jraf.android.prefs.Name;
import org.jraf.android.prefs.Prefs;

@Prefs
public class Main {
    private static final String PREF_LOGIN = "PREF_LOGIN";
    private static final String PREF_AGE = "PREF_AGE";

    /**
     * User login.
     */
    @Name(PREF_LOGIN)
    String login;

    /**
     * User password.
     */
    String password;

    @DefaultBoolean(false)
    Boolean isPremium;

    @Name(PREF_AGE)
    Integer age;
}
