package com.wan7451.core;

import com.google.gson.Gson;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class IPCInvocationHandler implements InvocationHandler {

    private Class<?> instance;
    private Class<? extends IPCService> service;
    private Gson gson = new Gson();

    public IPCInvocationHandler(Class<?> instance, Class<? extends IPCService> service) {
        this.instance = instance;
        this.service = service;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        IPCResponse response = IPCChannel.getInstance().send(
                IPCRequest.GET_METHOD,
                service,
                instance,
                method.getName(),
                args);
        if (response.isSuccess()) {
            Class<?> returnType = method.getReturnType();
            if (returnType != Void.class && returnType != void.class) {
                return gson.fromJson(response.getSouce(), returnType);
            }
        }
        return null;
    }
}
