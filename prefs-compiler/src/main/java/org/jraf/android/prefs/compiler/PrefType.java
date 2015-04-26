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
package org.jraf.android.prefs.compiler;

import java.util.ArrayList;

import javax.lang.model.type.TypeMirror;

import org.apache.commons.lang3.StringUtils;

public enum PrefType {
    BOOLEAN(Boolean.class.getName(), Boolean.class.getSimpleName(), "Boolean", "false"),
    FLOAT(Float.class.getName(), Float.class.getSimpleName(), "Float", "0f"),
    INTEGER(Integer.class.getName(), Integer.class.getSimpleName(), "Int", "0"),
    LONG(Long.class.getName(), Long.class.getSimpleName(), "Long", "0L"),
    STRING(String.class.getName(), String.class.getSimpleName(), "String", "null"),
    STRING_SET("java.util.Set<java.lang.String>", "Set<String>", "StringSet", "null"),;

    private final String mFullName;
    private final String mSimpleName;
    private final String mMethodName;
    private final String mDefaultValue;

    PrefType(String fullName, String simpleName, String methodName, String defaultValue) {
        mFullName = fullName;
        mSimpleName = simpleName;
        mMethodName = methodName;
        mDefaultValue = defaultValue;
    }

    public String getFullName() {
        return mFullName;
    }

    public String getSimpleName() {
        return mSimpleName;
    }

    public String getMethodName() {
        return mMethodName;
    }

    public String getDefaultValue() {
        return mDefaultValue;
    }

    public boolean isCompatible(TypeMirror type) {
        return getFullName().equals(type.toString());
    }

    public static PrefType from(TypeMirror fieldType) {
        String fullName = fieldType.toString();
        for (PrefType prefType : values()) {
            if (prefType.getFullName().equals(fullName)) return prefType;
        }
        throw new IllegalArgumentException("Unsupported type: " + fullName);
    }

    public static boolean isAllowedType(TypeMirror fieldType) {
        String fullName = fieldType.toString();
        boolean found = false;
        for (PrefType prefType : values()) {
            if (prefType.getFullName().equals(fullName)) {
                found = true;
                break;
            }
        }
        return found;
    }

    public static String getAllowedTypes() {
        ArrayList<String> allowedTypes = new ArrayList<String>(values().length);
        for (PrefType prefType : values()) {
            allowedTypes.add(prefType.getFullName());
        }
        return StringUtils.join(allowedTypes, ", ");
    }
}
