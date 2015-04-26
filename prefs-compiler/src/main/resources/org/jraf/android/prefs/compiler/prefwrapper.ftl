package ${package};

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.jraf.android.prefs.SharedPreferencesWrapper;

public class ${prefWrapperClassName} extends SharedPreferencesWrapper {
    private static ${prefWrapperClassName} sInstance;

    public static ${prefWrapperClassName} get(Context context) {
        if (sInstance == null) {
            SharedPreferences wrapped = getWrapped(context);
            sInstance = new ${prefWrapperClassName}(wrapped);
        }
        return sInstance;
    }

    protected static SharedPreferences getWrapped(Context context) {
<#if fileName??>
        return context.getSharedPreferences("${fileName}", ${fileMode});
<#else>
        return PreferenceManager.getDefaultSharedPreferences(context);
</#if>
    }

    public ${prefWrapperClassName}(SharedPreferences wrapped) {
        super(wrapped);
    }

    public ${editorWrapperClassName} edit() {
        return new ${editorWrapperClassName}(super.edit());
    }
<#list prefList as pref>

    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if>
    public ${pref.type.simpleName} get${pref.fieldName?cap_first}() {
        if (!contains("${pref.prefName}")) return ${pref.defaultValue};
        return get${pref.type.methodName}("${pref.prefName}", ${pref.type.defaultValue});
    }

    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if>
    public boolean contains${pref.fieldName?cap_first}() {
        return contains("${pref.prefName}");
    }

    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if>
    public ${prefWrapperClassName} put${pref.fieldName?cap_first}(${pref.type.simpleName} ${pref.fieldName}) {
        edit().put${pref.fieldName?cap_first}(${pref.fieldName}).apply();
        return this;
    }

    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if>
    public ${prefWrapperClassName} remove${pref.fieldName?cap_first}() {
        edit().remove("${pref.prefName}").apply();
        return this;
    }
</#list>
}