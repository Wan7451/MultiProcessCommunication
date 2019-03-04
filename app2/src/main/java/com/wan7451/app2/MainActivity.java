package com.wan7451.app2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wan7451.core.IPCLib;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IPCLib.getDefault().register(IDataManager.class);

        IDataManager instance = IPCLib.getDefault().getInstance(IDataManager.class);
        instance.setData("");
        instance.getData();
    }
}
