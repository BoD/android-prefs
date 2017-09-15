package ${package};

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

<#if comment??>
/**
 * ${comment?trim}
 */
</#if>
public class ${constantsClassName} {
<#list prefList as pref>


    //================================================================================
    // region ${pref.fieldName?cap_first}
    //================================================================================

    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if><#t>
    public static final String KEY_${pref.fieldNameUpperCase} = "${pref.prefName}";

    public static final ${pref.type.simpleName} DEFAULT_${pref.fieldNameUpperCase} = ${pref.defaultValue};

    // endregion
</#list>
}