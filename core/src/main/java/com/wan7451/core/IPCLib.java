package com.wan7451.core;

import android.content.Context;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class IPCLib {
    private static IPCLib instance;

    public static synchronized IPCLib getDefault() {
        if (instance == null) {
            instance = new IPCLib();
        }
        return instance;
    }

    private IPCLib() {

    }

    /**
     * 提供服务端接口
     */
    public void register(Class<?> clazz) {
        IPCCacheCenter.getInstance().register(clazz);
    }

    /**
     * 客户端连接
     */
    public void connect(Context context, String packageName) {
        IPCChannel.getInstance().bind(context, packageName, IPCService.class);
    }

    public <T> T getInstance(Class<?> instanceClass, Object... parameters) {
        IPCResponse response = IPCChannel.getInstance().send(
                IPCRequest.GET_INSTANCE,
                IPCService.class,
                instanceClass,
                "getInstance",
                parameters
        );
        if(response.isSuccess()){
            return (T)Proxy.newProxyInstance(instanceClass.getClassLoader(),
                    new Class[]{instanceClass}, new IPCInvocationHandler(instanceClass,IPCService.class));
        }
        return null;
    }
}
