package com.wan7451.multiprocesscommunication;

import com.wan7451.core.ClassId;

@ClassId("DataManager")
public interface DataManager {
    void setData(String data);
    String getData();
}
