package ${package};

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import android.content.SharedPreferences;

import org.jraf.android.prefs.EditorWrapper;

public class ${editorWrapperClassName} extends EditorWrapper {
    public ${editorWrapperClassName}(SharedPreferences.Editor wrapped) {
        super(wrapped);
    }
<#list prefList as pref>

    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if>
    public ${editorWrapperClassName} put${pref.fieldName?cap_first}(${pref.type.simpleName} ${pref.fieldName}) {
        if (${pref.fieldName} == null) {
            remove("${pref.prefName}");
        } else {
            put${pref.type.methodName}("${pref.prefName}", ${pref.fieldName});
        }
        return this;
    }

    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if>
    public ${editorWrapperClassName} remove${pref.fieldName?cap_first}() {
        remove("${pref.prefName}");
        return this;
    }
</#list>
}