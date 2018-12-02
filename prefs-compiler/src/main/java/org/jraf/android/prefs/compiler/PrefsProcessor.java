/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 *
 * Copyright (C) 2015-present Benoit 'BoD' Lubek (BoD@JRAF.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jraf.android.prefs.compiler;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import org.apache.commons.io.IOUtils;
import org.jraf.android.prefs.DefaultInt;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.*;

@SupportedAnnotationTypes("org.jraf.android.prefs.Prefs")
public class PrefsProcessor extends AbstractProcessor {
    private static final String SUFFIX_PREF_WRAPPER = "Prefs";
    private static final String SUFFIX_EDITOR_WRAPPER = "EditorWrapper";
    private static final String SUFFIX_CONSTANTS = "Constants";

    private Configuration mFreemarkerConfiguration;

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private Configuration getFreemarkerConfiguration() {
        if (mFreemarkerConfiguration == null) {
            mFreemarkerConfiguration = new Configuration(new Version(2, 3, 26));
            mFreemarkerConfiguration.setClassForTemplateLoading(getClass(), "");
        }
        return mFreemarkerConfiguration;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            if (!annotation.getQualifiedName().contentEquals("org.jraf.android.prefs.Prefs")) {
                // Should never happen - but does with kapt :)
                continue;
            }
            for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                TypeElement classElement = (TypeElement) element;
                PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();

                String classComment = processingEnv.getElementUtils().getDocComment(classElement);

                List<Pref> prefList = new ArrayList<Pref>();
                // Iterate over the fields of the class
                for (VariableElement variableElement : ElementFilter.fieldsIn(classElement.getEnclosedElements())) {
                    if (variableElement.getModifiers().contains(Modifier.STATIC)) {
                        // Ignore constants
                        continue;
                    }

                    TypeMirror fieldType = variableElement.asType();
                    boolean isAllowedType = PrefType.isAllowedType(fieldType);
                    if (!isAllowedType) {
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                                fieldType + " is not allowed here, only these types are allowed: " + PrefType.getAllowedTypes(), variableElement);
                        // Problem detected: halt
                        return true;
                    }

                    String fieldName = variableElement.getSimpleName().toString();
                    org.jraf.android.prefs.Name fieldNameAnnot = variableElement.getAnnotation(org.jraf.android.prefs.Name.class);
                    String prefName = getPrefName(fieldName, fieldNameAnnot);

                    String prefDefaultValue = getDefaultValue(variableElement, fieldType);
                    if (prefDefaultValue == null) {
                        // Problem detected: halt
                        return true;
                    }

                    String fieldComment = StringUtil.cleanComment(processingEnv.getElementUtils().getDocComment(variableElement));
                    Pref pref = new Pref(fieldName, prefName, PrefType.from(fieldType), prefDefaultValue, fieldComment);
                    prefList.add(pref);
                }

                Map<String, Object> args = new HashMap<String, Object>();

                // File name (optional - also use 'value' for this)
                org.jraf.android.prefs.Prefs prefsAnnot = classElement.getAnnotation(org.jraf.android.prefs.Prefs.class);
                String fileName = prefsAnnot.value();
                if (fileName.isEmpty()) {
                    fileName = prefsAnnot.fileName();
                }
                if (!fileName.isEmpty()) args.put("fileName", fileName);

                // File mode (must only appear if fileName is defined)
                int fileMode = prefsAnnot.fileMode();
                if (fileMode != -1) {
                    if (fileName.isEmpty()) {
                        // File mode set, but not file name (which makes no sense)
                        processingEnv.getMessager()
                                .printMessage(Diagnostic.Kind.ERROR, "fileMode must only be set if fileName (or value) is also set", classElement);
                        // Problem detected: halt
                        return true;
                    }
                    args.put("fileMode", fileMode);
                }

                // Disable @Nullable generation
                args.put("disableNullable", prefsAnnot.disableNullable());

                // Generate observe methods
                args.put("generateGetLiveData", prefsAnnot.generateGetLiveData());

                JavaFileObject javaFileObject = null;
                try {
                    args.put("package", packageElement.getQualifiedName());
                    args.put("comment", StringUtil.cleanComment(classComment));
                    args.put("prefWrapperClassName", classElement.getSimpleName() + SUFFIX_PREF_WRAPPER);
                    args.put("editorWrapperClassName", classElement.getSimpleName() + SUFFIX_EDITOR_WRAPPER);
                    args.put("constantsClassName", classElement.getSimpleName() + SUFFIX_CONSTANTS);
                    args.put("prefList", prefList);

                    // SharedPreferencesWrapper
                    javaFileObject = processingEnv.getFiler().createSourceFile(classElement.getQualifiedName() + SUFFIX_PREF_WRAPPER);
                    Template template = getFreemarkerConfiguration().getTemplate("prefwrapper.ftl");
                    Writer writer = javaFileObject.openWriter();
                    template.process(args, writer);
                    IOUtils.closeQuietly(writer);

                    // EditorWrapper
                    javaFileObject = processingEnv.getFiler().createSourceFile(classElement.getQualifiedName() + SUFFIX_EDITOR_WRAPPER);
                    template = getFreemarkerConfiguration().getTemplate("editorwrapper.ftl");
                    writer = javaFileObject.openWriter();
                    template.process(args, writer);
                    IOUtils.closeQuietly(writer);

                    // Constants
                    javaFileObject = processingEnv.getFiler().createSourceFile(classElement.getQualifiedName() + SUFFIX_CONSTANTS);
                    template = getFreemarkerConfiguration().getTemplate("constants.ftl");
                    writer = javaFileObject.openWriter();
                    template.process(args, writer);
                    IOUtils.closeQuietly(writer);

                } catch (Exception e) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                            "En error occurred while generating Prefs code " + e.getClass() + e.getMessage(), element);
                    e.printStackTrace();
                    // Problem detected: halt
                    return true;
                }
            }
        }
        return true;
    }

    private String getDefaultValue(VariableElement variableElement, TypeMirror fieldType) {
        Class<? extends Annotation> annotClass = org.jraf.android.prefs.DefaultBoolean.class;
        PrefType compatiblePrefType = PrefType.BOOLEAN;
        org.jraf.android.prefs.DefaultBoolean defaultBooleanAnnot = (org.jraf.android.prefs.DefaultBoolean) variableElement.getAnnotation(annotClass);
        if (defaultBooleanAnnot != null) {
            if (!ensureCompatibleAnnotation(compatiblePrefType, fieldType, annotClass, variableElement)) return null;
            return String.valueOf(defaultBooleanAnnot.value());
        }

        annotClass = org.jraf.android.prefs.DefaultFloat.class;
        compatiblePrefType = PrefType.FLOAT;
        org.jraf.android.prefs.DefaultFloat defaultFloatAnnot = (org.jraf.android.prefs.DefaultFloat) variableElement.getAnnotation(annotClass);
        if (defaultFloatAnnot != null) {
            if (!ensureCompatibleAnnotation(compatiblePrefType, fieldType, annotClass, variableElement)) return null;
            return String.valueOf(defaultFloatAnnot.value()) + "f";
        }

        annotClass = DefaultInt.class;
        compatiblePrefType = PrefType.INTEGER;
        DefaultInt defaultIntAnnot = (DefaultInt) variableElement.getAnnotation(annotClass);
        if (defaultIntAnnot != null) {
            if (!ensureCompatibleAnnotation(compatiblePrefType, fieldType, annotClass, variableElement)) return null;
            return String.valueOf(defaultIntAnnot.value());
        }

        annotClass = org.jraf.android.prefs.DefaultLong.class;
        compatiblePrefType = PrefType.LONG;
        org.jraf.android.prefs.DefaultLong defaultLongAnnot = (org.jraf.android.prefs.DefaultLong) variableElement.getAnnotation(annotClass);
        if (defaultLongAnnot != null) {
            if (!ensureCompatibleAnnotation(compatiblePrefType, fieldType, annotClass, variableElement)) return null;
            return String.valueOf(defaultLongAnnot.value()) + "L";
        }

        annotClass = org.jraf.android.prefs.DefaultString.class;
        compatiblePrefType = PrefType.STRING;
        org.jraf.android.prefs.DefaultString defaultStringAnnot = (org.jraf.android.prefs.DefaultString) variableElement.getAnnotation(annotClass);
        if (defaultStringAnnot != null) {
            if (!ensureCompatibleAnnotation(compatiblePrefType, fieldType, annotClass, variableElement)) return null;
            return "\"" + unescapeString(defaultStringAnnot.value()) + "\"";
        }

        annotClass = org.jraf.android.prefs.DefaultStringSet.class;
        compatiblePrefType = PrefType.STRING_SET;
        org.jraf.android.prefs.DefaultStringSet defaultStringSetAnnot = (org.jraf.android.prefs.DefaultStringSet) variableElement.getAnnotation(annotClass);
        if (defaultStringSetAnnot != null) {
            if (!ensureCompatibleAnnotation(compatiblePrefType, fieldType, annotClass, variableElement)) return null;
            StringBuilder res = new StringBuilder("new HashSet<String>(Arrays.asList(");
            int i = 0;
            for (String s : defaultStringSetAnnot.value()) {
                if (i > 0) res.append(", ");
                res.append("\"");
                res.append(unescapeString(s));
                res.append("\"");
                i++;
            }
            res.append("))");
            return res.toString();
        }

        // Default default value :)
        return "null";
    }

    private boolean ensureCompatibleAnnotation(PrefType prefType, TypeMirror fieldType, Class<?> annotClass, VariableElement variableElement) {
        if (!prefType.isCompatible(fieldType)) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                    annotClass.getSimpleName() + " annotation is only allowed on " + prefType.getSimpleName() + " fields", variableElement);
            return false;
        }
        return true;
    }

    private static String getPrefName(String fieldName, org.jraf.android.prefs.Name fieldNameAnnot) {
        if (fieldNameAnnot != null) {
            return fieldNameAnnot.value();
        }
        return fieldName;
    }

    private String unescapeString(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, count = s.length(); i < count; i++)
            switch (s.charAt(i)){
                case '\t': sb.append("\\t"); break;
                case '\b': sb.append("\\b"); break;
                case '\n': sb.append("\\n"); break;
                case '\r': sb.append("\\r"); break;
                case '\f': sb.append("\\f"); break;
                case '\'': sb.append("\\'"); break;
                case '\"': sb.append("\\\""); break;
                case '\\': sb.append("\\\\"); break;
                default: sb.append(s.charAt(i));
            }
        return sb.toString();
    }
}
