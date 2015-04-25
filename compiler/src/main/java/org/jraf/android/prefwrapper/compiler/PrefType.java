package org.jraf.android.prefwrapper.compiler;

import java.util.ArrayList;

import javax.lang.model.type.TypeMirror;

import org.apache.commons.lang3.StringUtils;

public enum PrefType {
    BOOLEAN(Boolean.class.getName(), Boolean.class.getSimpleName(), "getBoolean", "false"),
    FLOAT(Float.class.getName(), Float.class.getSimpleName(), "getFloat", "0f"),
    INTEGER(Integer.class.getName(), Integer.class.getSimpleName(), "getInt", "0"),
    LONG(Long.class.getName(), Long.class.getSimpleName(), "getLong", "0L"),
    STRING(String.class.getName(), String.class.getSimpleName(), "getString", "null"),
    STRING_SET("java.util.Set<java.lang.String>", "Set<String>", "getStringSet", "null"),;

    private final String mFullName;
    private final String mSimpleName;
    private final String mGetterName;
    private final String mDefaultValue;

    PrefType(String fullName, String simpleName, String getterName, String defaultValue) {
        mFullName = fullName;
        mSimpleName = simpleName;
        mGetterName = getterName;
        mDefaultValue = defaultValue;
    }

    public String getFullName() {
        return mFullName;
    }

    public String getSimpleName() {
        return mSimpleName;
    }

    public String getGetterName() {
        return mGetterName;
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
