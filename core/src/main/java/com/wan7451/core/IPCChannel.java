package com.wan7451.core;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.HashMap;

public class IPCChannel {
    private static final IPCChannel ourInstance = new IPCChannel();

    public static IPCChannel getInstance() {
        return ourInstance;
    }

    private IPCChannel() {
    }

    private Gson gson = new Gson();
    private HashMap<Class<? extends IPCService>, Boolean> mBounds = new HashMap<>();

    private HashMap<Class<? extends IPCService>, IIPCService> mIPCService = new HashMap<>();

    /**
     * @param context
     * @param packageName 同一个应用null
     * @param service
     */
    public void bind(Context context, String packageName, Class<? extends IPCService> service) {
        Boolean isBound = mBounds.get(service);
        if (isBound != null && isBound) {
            return;
        }
        Intent intent;
        if (TextUtils.isEmpty(packageName)) {
            intent = new Intent(context, service);
        } else {
            intent = new Intent();
            intent.setComponent(new ComponentName(packageName, service.getName()));
        }
        IPCServiceConnection conn = new IPCServiceConnection(service);
        context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }


    public IPCResponse send(int type, Class<? extends IPCService> service, Class<?> instanceClass, String methodName, Object... parameters) {
        Boolean isBound = mBounds.get(service);
        if (isBound == null || !isBound) {
            return new IPCResponse("", "服务未连接", false);
        }

        ClassId classId = instanceClass.getAnnotation(ClassId.class);
        String className;
        if (classId != null) {
            className = classId.value();
        } else {
            className = instanceClass.getName();
        }

        IPCRequest request = new IPCRequest(type, className, methodName, getParameters(parameters));
        IIPCService iipcService = mIPCService.get(service);
        try {
            if (iipcService != null) {
                return iipcService.send(request);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }


    private IPCParameter[] getParameters(Object[] parameters) {
        IPCParameter[] p;
        if (null != parameters) {
            p = new IPCParameter[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                Object object = parameters[0];
                p[i] = new IPCParameter(object.getClass().getName(), gson.toJson(object));
            }
        } else {
            p = new IPCParameter[0];
        }
        return p;
    }

    class IPCServiceConnection implements ServiceConnection {

        private Class<? extends IPCService> mService;

        public IPCServiceConnection(Class<? extends IPCService> mService) {
            this.mService = mService;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IIPCService iipcService = IIPCService.Stub.asInterface(service);
            mIPCService.put(this.mService, iipcService);
            mBounds.put(this.mService, true);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBounds.remove(mService);
            mIPCService.remove(mService);
        }
    }
}
