# MultiProcessCommunication
进程间通信组件

简单的进程间通信框架



服务端操作

1. 注册服务
```
<service
    android:name="com.wan7451.core.IPCService"
    android:exported="true" />
```
2. 提供客户端调用接口，对象，并通过单例保证2端对象一致,添加注解
```
public interface IDataManager {
    void setData(String data);
    String getData();
}

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
```
3. 服务端注册
```
IPCLib.getDefault().register(DataManager.class);

findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        DataManager.getInstance().setData("AAAAAA");
    }
});
findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String data = DataManager.getInstance().getData();
        Toast.makeText(v.getContext(), data, Toast.LENGTH_SHORT).show();
    }
});
```

客户端操作
1. 声明与服务端通信的接口，统一注解
```
@ClassId("DataManager")
public interface IDataManager {
    void setData(String data);
    String getData();
}
```
2. 与服务端连接
```
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
```