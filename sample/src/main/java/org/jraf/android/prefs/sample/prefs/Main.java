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
package org.jraf.android.prefs.sample.prefs;

import org.jraf.android.prefs.DefaultBoolean;
import org.jraf.android.prefs.Name;
import org.jraf.android.prefs.Prefs;

/**
 * The main preferences of the app.
 */
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
