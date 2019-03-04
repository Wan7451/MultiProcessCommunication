package com.wan7451.core;

import android.os.Parcel;
import android.os.Parcelable;

public class IPCResponse implements Parcelable {
    /**
     * 返回结果
     */
    private String souce;
    /**
     * 描述信息
     */
    private String message;
    /**
     * 是否执行成功
     */
    private boolean isSuccess;

    public IPCResponse(String souce, String message, boolean isSuccess) {
        this.souce = souce;
        this.message = message;
        this.isSuccess = isSuccess;
    }

    public String getSouce() {
        return souce;
    }

    public void setSouce(String souce) {
        this.souce = souce;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.souce);
        dest.writeString(this.message);
        dest.writeByte(this.isSuccess ? (byte) 1 : (byte) 0);
    }

    protected IPCResponse(Parcel in) {
        this.souce = in.readString();
        this.message = in.readString();
        this.isSuccess = in.readByte() != 0;
    }

    public static final Parcelable.Creator<IPCResponse> CREATOR = new Parcelable.Creator<IPCResponse>() {
        @Override
        public IPCResponse createFromParcel(Parcel source) {
            return new IPCResponse(source);
        }

        @Override
        public IPCResponse[] newArray(int size) {
            return new IPCResponse[size];
        }
    };
}
