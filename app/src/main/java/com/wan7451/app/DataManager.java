package com.wan7451.app;

import com.wan7451.core.ClassId;

@ClassId("DataManager")
public class DataManager implements IDataManager {

    private static DataManager manager;

    /**
     * 单例模式
     * 客户端通过反射持有该对象
     * @return
     */
    public static DataManager getInstance() {
        if (manager == null) {
            manager = new DataManager();
        }
        return manager;
    }

    private String data;

    /**
     * 客户端通过反射调用方法
     * @param data
     */
    @Override
    public void setData(String data) {
        this.data = data;
    }

    /**
     * 客户端通过反射调用方法
     */
    @Override
    public String getData() {
        return data;
    }
}
