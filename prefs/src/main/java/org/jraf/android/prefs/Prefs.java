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
package org.jraf.android.prefs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Prefs {
    /**
     * Desired preferences file name.<br>
     * {@link #value()} and {@link #fileName()} are synonyms.
     *
     * @see android.content.Context#getSharedPreferences(java.lang.String, int)
     */
    String value() default "";

    /**
     * Desired preferences file name.<br>
     * {@link #value()} and {@link #fileName()} are synonyms.
     *
     * @see android.content.Context#getSharedPreferences(java.lang.String, int)
     */
    String fileName() default "";

    /**
     * Operating mode (should be {@link android.content.Context#MODE_PRIVATE},
     * {@link android.content.Context#MODE_WORLD_READABLE},
     * {@link android.content.Context#MODE_WORLD_WRITEABLE},
     * or {@link android.content.Context#MODE_MULTI_PROCESS}.<br>
     * <br>
     * <strong>This must only be set if {@link #fileName()} (or {@link #value()}) is also set</strong>
     *
     * @see android.content.Context#getSharedPreferences(java.lang.String, int)
     */
    int fileMode() default -1;

    /**
     * Normally, {@code @Nullable} annotations are included in the generated code where appropriate.
     * This behavior can be disabled by setting this to {@code true}.
     * This is useful if your project does not use the support library.
     */
    boolean disableNullable() default false;

    /**
     * Desired generated class name prefix.<br>
     * If empty string is specified, uses target class name.
     */
    String classNamePrefix() default "";
}
