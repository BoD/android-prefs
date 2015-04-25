package org.jraf.android.prefs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Prefs {
    /**
     * Desired preferences file name.<br/>
     * {@link #value()} and {@link #fileName()} are synonyms.
     *
     * @see android.content.Context#getSharedPreferences(java.lang.String, int)
     */
    String value() default "";

    /**
     * Desired preferences file name.<br/>
     * {@link #value()} and {@link #fileName()} are synonyms.
     *
     * @see android.content.Context#getSharedPreferences(java.lang.String, int)
     */
    String fileName() default "";

    /**
     * Operating mode (should be {@link android.content.Context#MODE_PRIVATE},
     * {@link android.content.Context#MODE_WORLD_READABLE},
     * {@link android.content.Context#MODE_WORLD_WRITEABLE},
     * or {@link android.content.Context#MODE_MULTI_PROCESS}.<br/>
     * <br/>
     * <strong>This must only be set if {@link #fileName()} (or {@link #value()}) is also set</strong>
     *
     * @see android.content.Context#getSharedPreferences(java.lang.String, int)
     */
    int fileMode() default -1;
}
