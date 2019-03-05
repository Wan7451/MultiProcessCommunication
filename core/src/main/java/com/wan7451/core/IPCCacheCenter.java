package com.wan7451.core;

import android.text.TextUtils;

import java.lang.reflect.Method;
import java.util.HashMap;

public class IPCCacheCenter {
    private static IPCCacheCenter instance;

    private IPCCacheCenter() {
    }

    public static synchronized IPCCacheCenter getInstance() {
        if (instance == null) {
            instance = new IPCCacheCenter();
        }
        return instance;
    }

    private HashMap<String, Class<?>> mClasses = new HashMap<>();
    private HashMap<Class<?>, HashMap<String, Method>> mMethods = new HashMap<>();
    private HashMap<String, Object> mInstances = new HashMap<>();

    public void register(Class<?> clazz) {
        String className = AnnotationUtils.getClassName(clazz);
        mClasses.put(className, clazz);
        HashMap<String, Method> methods = new HashMap<>();
        for (Method method : clazz.getMethods()) {
            methods.put(method.getName(), method);
        }
        mMethods.put(clazz, methods);
    }

    public void unregister(Class<?> clazz) {
        String className = AnnotationUtils.getClassName(clazz);
        Class<?> cls = mClasses.get(className);
        if (cls != null) {
            mClasses.remove(className);
            mMethods.remove(cls);
        }
    }


    public Class<?> getClassType(String className) {
        if (TextUtils.isEmpty(className)) {
            return null;
        }
        Class<?> clazz = mClasses.get(className);
        if (clazz == null) {
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return clazz;
    }

    public Method getMethod(Class<?> clazz, String methodName) {
        if (TextUtils.isEmpty(methodName) || clazz == null) {
            return null;
        }
        HashMap<String, Method> methods = mMethods.get(clazz);
        return methods.get(methodName);
    }

    public void putObject(String classType, Object instance) {
        mInstances.put(classType, instance);
    }

    public Object getObject(String classType) {
        return mInstances.get(classType);
    }


}