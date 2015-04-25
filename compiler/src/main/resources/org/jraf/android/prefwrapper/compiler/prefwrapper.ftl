package ${package};

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.jraf.android.prefwrapper.SharedPreferencesWrapper;

public class ${prefWrapperClassName} extends SharedPreferencesWrapper {
    private static MainPreferencesWrapper sInstance;

    public static MainPreferencesWrapper get(Context context) {
        if (sInstance == null) {
<#if fileName??>
            SharedPreferences wrapped = context.getSharedPreferences("${fileName}", ${fileMode});
<#else>
            SharedPreferences wrapped = PreferenceManager.getDefaultSharedPreferences(context);
</#if>
            sInstance = new MainPreferencesWrapper(wrapped);
        }
        return sInstance;
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
</#list>
}