package com.wan7451.core;


import android.os.Parcel;
import android.os.Parcelable;

public class IPCRequest implements Parcelable {
    public static final int GET_INSTANCE = 0;
    public static final int GET_METHOD = 1;
    /**
     * 请求类型
     */
    private int type;
    /**
     * 类名称
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 方法参数
     */
    private IPCParameter[] parameters;

    public IPCRequest(int type, String className, String methodName, IPCParameter[] parameters) {
        this.type = type;
        this.className = className;
        this.methodName = methodName;
        this.parameters = parameters;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public IPCParameter[] getParameters() {
        return parameters;
    }

    public void setParameters(IPCParameter[] parameters) {
        this.parameters = parameters;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeString(this.className);
        dest.writeString(this.methodName);
        dest.writeTypedArray(this.parameters, flags);
    }

    protected IPCRequest(Parcel in) {
        this.type = in.readInt();
        this.className = in.readString();
        this.methodName = in.readString();
        this.parameters = in.createTypedArray(IPCParameter.CREATOR);
    }

    public static final Parcelable.Creator<IPCRequest> CREATOR = new Parcelable.Creator<IPCRequest>() {
        @Override
        public IPCRequest createFromParcel(Parcel source) {
            return new IPCRequest(source);
        }

        @Override
        public IPCRequest[] newArray(int size) {
            return new IPCRequest[size];
        }
    };
}
