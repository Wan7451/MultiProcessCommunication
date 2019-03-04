package com.wan7451.core;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class IPCService extends Service {

    private IPCCacheCenter cacheCenter = IPCCacheCenter.getInstance();
    private Gson gson = new Gson();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IIPCService.Stub() {
            @Override
            public IPCResponse send(IPCRequest request) throws RemoteException {
                switch (request.getType()) {
                    case IPCRequest.GET_INSTANCE: {
                        String className = request.getClassName();
                        String methodName = request.getMethodName();
                        IPCParameter[] parameters = request.getParameters();

                        Class<?> clazz = cacheCenter.getClassType(className);
                        Method method = cacheCenter.getMethod(clazz, methodName);
                        try {
                            Object instanceObj = method.invoke(null, restoreParameters(parameters));
                            cacheCenter.putObject(className, instanceObj);
                            return new IPCResponse(null, null, true);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return new IPCResponse(null, null, false);
                        }
                    }
                    case IPCRequest.GET_METHOD: {
                        String className = request.getClassName();
                        String methodName = request.getMethodName();
                        IPCParameter[] parameters = request.getParameters();

                        Class<?> clazz = cacheCenter.getClassType(className);
                        Method method = cacheCenter.getMethod(clazz, methodName);
                        Object instance = cacheCenter.getObject(className);
                        try {
                            Object returnObj = method.invoke(instance, restoreParameters(parameters));

                            return new IPCResponse(gson.toJson(returnObj), null, true);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return new IPCResponse(null, null, false);
                        }
                    }
                    default:
                }
                return null;
            }
        };
    }


    private Object[] restoreParameters(IPCParameter[] parameters) {
        Object[] objects;
        if (null != parameters) {
            objects = new Object[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                IPCParameter parameter = parameters[i];
                objects[i] = gson.fromJson(parameter.getValue(), cacheCenter.getClassType(parameter.getType()));
            }
        } else {
            objects = new Object[0];
        }
        return objects;
    }
}
