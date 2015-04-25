package org.jraf.android.prefwrapper.sample;

import org.jraf.android.prefwrapper.DefaultBoolean;
import org.jraf.android.prefwrapper.Name;
import org.jraf.android.prefwrapper.PrefWrapper;

@PrefWrapper
public class MainPreferences {
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
    Long age;
}
