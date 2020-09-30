package com.zhuj.code.libprocessor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.zhuj.code.libannotation.ImplClass;

import net.ltgt.gradle.incap.IncrementalAnnotationProcessor;
import net.ltgt.gradle.incap.IncrementalAnnotationProcessorType;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
@IncrementalAnnotationProcessor(IncrementalAnnotationProcessorType.ISOLATING)
public class AnnotationsProcessor extends AbstractProcessor {
    private Filer filer; //文件相关的辅助类
    private Elements elementUtils; //元素相关的辅助类
    private Messager messager; //日志相关的辅助类
    private boolean isFileCreated = false;
    private static final List<Class<? extends Annotation>> LISTENERS = Collections.singletonList(ImplClass.class);

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
        isFileCreated = false;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (isFileCreated) {
            return true;
        }
        isFileCreated = true;

        for (Element element : roundEnv.getElementsAnnotatedWith(ImplClass.class)) {
            if (element.getKind() != ElementKind.CLASS) {
                error(element, "Only classes can be annotated with @%s", ImplClass.class.getSimpleName());
                return true; // Exit processing
            }
            PackageElement packageElement = elementUtils.getPackageOf(element);

            MethodSpec main = MethodSpec.methodBuilder("main")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(void.class)
                    .addParameter(String[].class, "args")
                    .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                    .build();
            TypeSpec helloWorld = TypeSpec.classBuilder(element.getSimpleName() + "Impl")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(main)
                    .build();
            try {
                // build .java
                JavaFile javaFile = JavaFile.builder(packageElement.getQualifiedName().toString(), helloWorld)
                        .addFileComment(" This codes are generated automatically. Do not modify!")
                        .build();
                // write to file
                javaFile.writeTo(filer);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }

    // @Override
    // public Set<String> getSupportedOptions() {
    //     return Collections.singleton(IncrementalAnnotationProcessorType.ISOLATING.getProcessorOption());
    // }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(ImplClass.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    private void error(Element e, String msg, Object... args) {
        messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args), e);
    }

    private void info(Element e, String msg, Object... args) {
        messager.printMessage(Diagnostic.Kind.NOTE, String.format(msg, args), e);
    }
}