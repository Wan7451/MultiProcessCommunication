package com.wan7451.app2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wan7451.core.IPCLib;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IPCLib.getDefault().connect(this,"com.wan7451.app");

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IDataManager instance = IPCLib.getDefault().getInstance(IDataManager.class);
                instance.setData("BBBB");
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IDataManager instance = IPCLib.getDefault().getInstance(IDataManager.class);
                String data = instance.getData();
                Toast.makeText(v.getContext(), data, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
