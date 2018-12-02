package ${package};

import java.util.Set;

import android.annotation.SuppressLint;
<#if generateGetLiveData>
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
</#if><#t>
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
<#if !disableNullable>
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
</#if><#t>

import org.jraf.android.prefs.SharedPreferencesWrapper;

<#if comment??>
/**
 * ${comment?trim}
 */
</#if>
public class ${prefWrapperClassName} extends SharedPreferencesWrapper {
    private static ${prefWrapperClassName} sInstance;

    <#if !disableNullable>@NonNull</#if>
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

    <#if !disableNullable>@NonNull</#if>
    @SuppressLint("CommitPrefEdits")
    public ${editorWrapperClassName} edit() {
        return new ${editorWrapperClassName}(super.edit());
    }
<#list prefList as pref>


    //================================================================================
    // region ${pref.fieldName?cap_first}
    //================================================================================

    <#if pref.type == "BOOLEAN">
    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if><#t>
    <#if !disableNullable && pref.defaultValue == "null">
    @Nullable
    </#if><#t>
    public ${pref.type.simpleName} get${pref.fieldName?cap_first}() {
        return is${pref.fieldName?cap_first}();
    }
    </#if>

    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if><#t>
    <#if !disableNullable && pref.defaultValue == "null">
    @Nullable
    </#if><#t>
    public ${pref.type.simpleName} <#if pref.type == "BOOLEAN">is<#else>get</#if>${pref.fieldName?cap_first}() {
        if (!contains(${constantsClassName}.KEY_${pref.fieldNameUpperCase})) return ${constantsClassName}.DEFAULT_${pref.fieldNameUpperCase};
        return get${pref.type.methodName}(${constantsClassName}.KEY_${pref.fieldNameUpperCase}, ${pref.type.defaultValue});
    }

    <#if generateGetLiveData>
    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if><#t>
    public LiveData<${pref.type.simpleName}> get${pref.fieldName?cap_first}LiveData() {
        return new PreferenceLiveData<${pref.type.simpleName}>(${constantsClassName}.KEY_${pref.fieldNameUpperCase}) {
            @Override
            public ${pref.type.simpleName} getPreferenceValue() {
                return get${pref.fieldName?cap_first}();
            }
        };
    }
    </#if>

    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if><#t>
    public boolean contains${pref.fieldName?cap_first}() {
        return contains(${constantsClassName}.KEY_${pref.fieldNameUpperCase});
    }

    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if><#t>
    public ${prefWrapperClassName} put${pref.fieldName?cap_first}(${pref.type.simpleName} ${pref.fieldName}) {
        edit().put${pref.fieldName?cap_first}(${pref.fieldName}).apply();
        return this;
    }

    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if><#t>
    public ${prefWrapperClassName} set${pref.fieldName?cap_first}(${pref.type.simpleName} ${pref.fieldName}) {
        return put${pref.fieldName?cap_first}(${pref.fieldName});
    }

    <#if pref.comment??>
    /**
     * ${pref.comment?trim}
     */
    </#if><#t>
    public ${prefWrapperClassName} remove${pref.fieldName?cap_first}() {
        edit().remove(${constantsClassName}.KEY_${pref.fieldNameUpperCase}).apply();
        return this;
    }

    // endregion
</#list>

    <#if generateGetLiveData>

    //================================================================================
    // region PreferenceLiveData
    //================================================================================

    private abstract class PreferenceLiveData<T> extends MutableLiveData<T> {
        private final String mKey;

        public PreferenceLiveData(String key) {
            mKey = key;
        }

        private final OnSharedPreferenceChangeListener mListener = new OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (mKey.equals(key)) setValue(getPreferenceValue());
            }
        };

        @Override
        protected void onActive() {
            getWrapped().registerOnSharedPreferenceChangeListener(mListener);
            if (!getPreferenceValue().equals(getValue())) setValue(getPreferenceValue());
        }

        @Override
        protected void onInactive() {
            getWrapped().unregisterOnSharedPreferenceChangeListener(mListener);
        }

        public abstract T getPreferenceValue();
    }

    // endregion
    </#if><#t>
}