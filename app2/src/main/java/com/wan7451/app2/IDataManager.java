package com.wan7451.app2;

import com.wan7451.core.ClassId;

@ClassId("DataManager")
public interface IDataManager {
    void setData(String data);
    String getData();
}
