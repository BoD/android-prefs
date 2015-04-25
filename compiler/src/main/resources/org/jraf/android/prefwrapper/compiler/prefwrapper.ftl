package ${package};

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import android.content.SharedPreferences;

import org.jraf.android.prefwrapper.SharedPreferencesWrapper;

public class ${className} extends SharedPreferencesWrapper {
    public ${className}(SharedPreferences sharedPreferences) {
        super(sharedPreferences);
    }

<#list prefList as pref>
    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if>
    public ${pref.type.simpleName} get${pref.fieldName?cap_first}() {
        if (!contains("${pref.prefName}")) return ${pref.defaultValue};
        return ${pref.type.getterName}("${pref.prefName}", ${pref.type.defaultValue});
    }

</#list>
}