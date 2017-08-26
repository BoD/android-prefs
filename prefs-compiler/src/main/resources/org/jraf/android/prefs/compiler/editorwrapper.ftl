package ${package};

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import android.content.SharedPreferences;

import org.jraf.android.prefs.EditorWrapper;

<#if comment??>
/**
 * ${comment?trim}
 */
</#if>
public class ${editorWrapperClassName} extends EditorWrapper {
    public ${editorWrapperClassName}(SharedPreferences.Editor wrapped) {
        super(wrapped);
    }
<#list prefList as pref>


    //================================================================================
    // region ${pref.fieldName?cap_first}
    //================================================================================

    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if><#t>
    public ${editorWrapperClassName} put${pref.fieldName?cap_first}(${pref.type.simpleName} ${pref.fieldName}) {
        if (${pref.fieldName} == null) {
            remove(${constantsClassName}.KEY_${pref.fieldNameUpperCase});
        } else {
            put${pref.type.methodName}(${constantsClassName}.KEY_${pref.fieldNameUpperCase}, ${pref.fieldName});
        }
        return this;
    }

    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if><#t>
    public ${editorWrapperClassName} set${pref.fieldName?cap_first}(${pref.type.simpleName} ${pref.fieldName}) {
        return put${pref.fieldName?cap_first}(${pref.fieldName});
    }

    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if><#t>
    public ${editorWrapperClassName} remove${pref.fieldName?cap_first}() {
        remove(${constantsClassName}.KEY_${pref.fieldNameUpperCase});
        return this;
    }

    // endregion
</#list>
}