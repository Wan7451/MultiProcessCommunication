package com.wan7451.core;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * Description:参数类型
 * Blog: www.jianshu.com/u/1f69adf1da84
 * @Data: 2019/3/4-10:34 PM
 * @Author: wan7451
 */

public class IPCParameter implements Parcelable {
    private String type;
    private String value;

    public IPCParameter(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.value);
    }

    protected IPCParameter(Parcel in) {
        this.type = in.readString();
        this.value = in.readString();
    }

    public static final Parcelable.Creator<IPCParameter> CREATOR = new Parcelable.Creator<IPCParameter>() {
        @Override
        public IPCParameter createFromParcel(Parcel source) {
            return new IPCParameter(source);
        }

        @Override
        public IPCParameter[] newArray(int size) {
            return new IPCParameter[size];
        }
    };
}
