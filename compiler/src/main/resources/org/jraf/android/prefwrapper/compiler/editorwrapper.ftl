package ${package};

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import android.content.SharedPreferences;

import org.jraf.android.prefwrapper.EditorWrapper;

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
        put${pref.type.methodName}("${pref.prefName}", ${pref.fieldName});
        return this;
    }
</#list>
}