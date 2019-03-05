package com.wan7451.core;

public class AnnotationUtils {

    public static String getClassName(Class<?> clazz) {
        ClassId classId = clazz.getAnnotation(ClassId.class);
        String className;
        if (classId != null) {
            className = classId.value();
        } else {
            className = clazz.getName();
        }
        return className;
    }
}
