/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 *
 * Copyright (C) 2015-present Benoit 'BoD' Lubek (BoD@JRAF.org)
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
package org.jraf.android.prefs.sample.prefs

import org.jraf.android.prefs.DefaultBoolean
import org.jraf.android.prefs.Name
import org.jraf.android.prefs.Prefs

/**
 * The main preferences of the app.
 */
@Prefs(
    generateGetLiveData = true,
    useAndroidX = true
)
class Main {

    /**
     * User login.
     *
     * This is the main way to identify the user.
     */
    @Name(PREF_LOGIN)
    var login: String? = null

    /**
     * User password.
     */
    var password: String? = null

    @DefaultBoolean(false)
    var premium: Boolean? = null

    @Name(PREF_AGE)
    var age: Int? = null


    companion object {
        private const val PREF_LOGIN = "PREF_LOGIN"
        private const val PREF_AGE = "PREF_AGE"
    }
}
