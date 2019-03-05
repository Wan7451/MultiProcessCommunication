package com.wan7451.app;

import com.wan7451.core.ClassId;

@ClassId("DataManager")
public class DataManager implements IDataManager {

    private static DataManager manager;

    public static DataManager getInstance() {
        if (manager == null) {
            manager = new DataManager();
        }
        return manager;
    }

    private String data;


    @Override
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String getData() {
        return data;
    }
}
