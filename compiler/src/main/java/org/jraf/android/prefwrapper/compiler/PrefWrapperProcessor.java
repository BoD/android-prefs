package org.jraf.android.prefwrapper.compiler;

import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import org.apache.commons.io.IOUtils;
import org.jraf.android.prefwrapper.DefaultBoolean;
import org.jraf.android.prefwrapper.DefaultFloat;
import org.jraf.android.prefwrapper.DefaultInt;
import org.jraf.android.prefwrapper.DefaultLong;
import org.jraf.android.prefwrapper.DefaultString;
import org.jraf.android.prefwrapper.DefaultStringSet;
import org.jraf.android.prefwrapper.Name;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

@SupportedAnnotationTypes("org.jraf.android.prefwrapper.PrefWrapper")
public class PrefWrapperProcessor extends AbstractProcessor {
    private Configuration mFreemarkerConfiguration;

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private Configuration getFreemarkerConfiguration() {
        if (mFreemarkerConfiguration == null) {
            mFreemarkerConfiguration = new Configuration(new Version(2, 3, 22));
            mFreemarkerConfiguration.setClassForTemplateLoading(getClass(), "");
        }
        return mFreemarkerConfiguration;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement te : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(te)) {
                TypeElement classElement = (TypeElement) element;
                PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();

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
                    Name nameAnnotation = variableElement.getAnnotation(Name.class);
                    String prefName = getPrefName(fieldName, nameAnnotation);

                    String prefDefaultValue = getDefaultValue(variableElement, fieldType);
                    if (prefDefaultValue == null) {
                        // Problem detected: halt
                        return true;
                    }

                    String fieldComment = processingEnv.getElementUtils().getDocComment(variableElement);
                    Pref pref = new Pref(fieldName, prefName, PrefType.from(fieldType), prefDefaultValue, fieldComment);
                    prefList.add(pref);
                }

                JavaFileObject javaFileObject = null;
                try {
                    // SharedPreferencesWrapper
                    javaFileObject = processingEnv.getFiler().createSourceFile(classElement.getQualifiedName() + "Wrapper");
                    Template template = getFreemarkerConfiguration().getTemplate("prefwrapper.ftl");
                    Map<String, Object> args = new HashMap<String, Object>();
                    args.put("package", packageElement.getQualifiedName());
                    args.put("prefWrapperClassName", classElement.getSimpleName() + "Wrapper");
                    args.put("editorWrapperClassName", classElement.getSimpleName() + "EditorWrapper");
                    args.put("prefList", prefList);
                    Writer writer = javaFileObject.openWriter();
                    template.process(args, writer);
                    IOUtils.closeQuietly(writer);

                    // EditorWrapper
                    javaFileObject = processingEnv.getFiler().createSourceFile(classElement.getQualifiedName() + "EditorWrapper");
                    template = getFreemarkerConfiguration().getTemplate("editorwrapper.ftl");
                    writer = javaFileObject.openWriter();
                    template.process(args, writer);
                    IOUtils.closeQuietly(writer);

                } catch (Exception e) {
                    // TODO better error management
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    private String getDefaultValue(VariableElement variableElement, TypeMirror fieldType) {
        Class<? extends Annotation> annotClass = DefaultBoolean.class;
        PrefType compatiblePrefType = PrefType.BOOLEAN;
        DefaultBoolean defaultBooleanAnnot = (DefaultBoolean) variableElement.getAnnotation(annotClass);
        if (defaultBooleanAnnot != null) {
            if (!ensureCompatibleAnnotation(compatiblePrefType, fieldType, annotClass, variableElement)) return null;
            return String.valueOf(defaultBooleanAnnot.value());
        }

        annotClass = DefaultFloat.class;
        compatiblePrefType = PrefType.FLOAT;
        DefaultFloat defaultFloatAnnot = (DefaultFloat) variableElement.getAnnotation(annotClass);
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

        annotClass = DefaultLong.class;
        compatiblePrefType = PrefType.LONG;
        DefaultLong defaultLongAnnot = (DefaultLong) variableElement.getAnnotation(annotClass);
        if (defaultLongAnnot != null) {
            if (!ensureCompatibleAnnotation(compatiblePrefType, fieldType, annotClass, variableElement)) return null;
            return String.valueOf(defaultLongAnnot.value()) + "L";
        }

        annotClass = DefaultString.class;
        compatiblePrefType = PrefType.STRING;
        DefaultString defaultStringAnnot = (DefaultString) variableElement.getAnnotation(annotClass);
        if (defaultStringAnnot != null) {
            if (!ensureCompatibleAnnotation(compatiblePrefType, fieldType, annotClass, variableElement)) return null;
            return "\"" + defaultStringAnnot.value() + "\"";
        }

        annotClass = DefaultStringSet.class;
        compatiblePrefType = PrefType.STRING_SET;
        DefaultStringSet defaultStringSetAnnot = (DefaultStringSet) variableElement.getAnnotation(annotClass);
        if (defaultStringSetAnnot != null) {
            if (!ensureCompatibleAnnotation(compatiblePrefType, fieldType, annotClass, variableElement)) return null;
            StringBuilder res = new StringBuilder("new HashSet<String>(Arrays.asList(");
            int i = 0;
            for (String s : defaultStringSetAnnot.value()) {
                if (i > 0) res.append(", ");
                res.append("\"");
                res.append(s);
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

    private static String getPrefName(String fieldName, Name nameAnnotation) {
        if (nameAnnotation != null) {
            return nameAnnotation.value();
        }
        return fieldName;
    }
}
