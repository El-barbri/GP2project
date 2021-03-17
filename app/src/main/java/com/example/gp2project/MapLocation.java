package com.example.gp2project;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moko.support.MokoConstants;
import com.moko.support.MokoSupport;
import com.moko.support.callback.MokoScanDeviceCallback;
import com.moko.support.entity.DeviceInfo;
import com.moko.support.handler.BaseMessageHandler;

import java.util.ArrayList;
import java.util.List;

public class MapLocation extends AppCompatActivity implements MokoScanDeviceCallback {

    public static ArrayList<DeviceData> devList = new ArrayList<>();
    public static ArrayList<DeviceData> devListfilter = new ArrayList<>();
    public static AdapterLocation adapterHproducts;
    List<String> mac = new ArrayList();
    String Mac_filter;
    RecyclerView rec;
    private Handler mHandler;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                    int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                    switch (blueState) {
                        case BluetoothAdapter.STATE_TURNING_OFF:
                            mHandler.removeMessages(0);
                            MokoSupport.getInstance().stopScanDevice();
                            onStopScan();

                            break;

                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_location);


        rec = findViewById(R.id.rec);

        // init


        mHandler = new MapLocation.CunstomHandler(this);

//        EventBus.getDefault().register(this);
        // 注册广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver, filter);
        if (!MokoSupport.getInstance().isBluetoothOpen()) {
            MokoSupport.getInstance().enableBluetooth();
            // startScan();
        } else {
            startScan();
        }


        Intent intent = getIntent();
        if (intent.hasExtra("Mac")) {
            Mac_filter = intent.getStringExtra("Mac");
            Toast.makeText(this, Mac_filter, Toast.LENGTH_SHORT).show();
        }

    }

    private void startScan() {
        if (!MokoSupport.getInstance().isBluetoothOpen()) {
            // 蓝牙未打开，开启蓝牙
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, MokoConstants.REQUEST_CODE_ENABLE_BT);
            return;
        }

        /*
        if (!isLocationPermissionOpen()) {
            return;
        }

      */

        MokoSupport.getInstance().startScanDevice(this);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MokoSupport.getInstance().stopScanDevice();
            }
        }, 10000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case MokoConstants.REQUEST_CODE_ENABLE_BT:
                    startScan();
                    break;
            }
        } else {
            switch (requestCode) {
                case MokoConstants.REQUEST_CODE_ENABLE_BT:
                    // 未打开蓝牙
                    MapLocation.this.finish();
                    break;
                case BeaconConstants.REQUEST_CODE_DEVICE_INFO:

                    startScan();

                    break;
            }
        }
    }

    @Override
    public void onStartScan() {
        Toast.makeText(this, "Start Scan", Toast.LENGTH_SHORT).show();
      /*  beaconInfoParseable = new BeaconInfoParseableImpl();
        if (!isLocationPermissionOpen()) {
            return;
        }
       */
    }

    @Override
    public void onStopScan() {

        Toast.makeText(this, "stop Scan", Toast.LENGTH_SHORT).show();
        devListfilter.clear();

        Init();

      /*  for (DeviceData dev : devList)
        {
            if (devListfilter.isEmpty())
            {
                devListfilter.add(dev);
            }else {
                for (int x =0; x < devListfilter.size(); x++)
                {
                    if (!devListfilter.get(x).getMac().equals(dev.getMac()))
                    {
                        devListfilter.add(dev);
                        startActivity(new Intent(this,ScanResult.class));
                    }else {
                     //   Toast.makeText(this, "dd", Toast.LENGTH_SHORT).show();
                        //
                        startActivity(new Intent(this,ScanResult.class));
                    }
                }
            }
        }

       */
        // Toast.makeText(this, devListfilter.size()+"", Toast.LENGTH_SHORT).show();
    }


    public void Init() {
        adapterHproducts = new AdapterLocation(this);
        rec.setLayoutManager(new LinearLayoutManager(this));
        rec.setAdapter(adapterHproducts);
        //  recycle_animation(rec);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        devList.clear();
    }

    @Override
    public void onScanDevice(DeviceInfo device) {

        // Toast.makeText(this, beaconInfo.distance, Toast.LENGTH_SHORT).show();
        BeaconXInfo beaconInfo = new BeaconXInfoParseableImpl().parseDeviceInfo(device);

        // Toast.makeText(this, "Name is : "+device.name, Toast.LENGTH_SHORT).show();
        // Toast.makeText(this, "Mac is : "+device.mac, Toast.LENGTH_SHORT).show();


        if (mac.contains(device.mac)) {

        } else {
            mac.add(device.mac);
            devList.add(new DeviceData(device.name, device.mac, device.rssi, String.valueOf(getDistance(device.rssi, -80))));
            if (device.name != null) {
                devList.add(new DeviceData(device.name, device.mac, device.rssi, String.valueOf(getDistance(device.rssi, -80))));
            }
        }



    /*    if (!devList.isEmpty()) {

            for (int x = 0; x < devList.size(); x++) {
                if (devList.get(x).getMac().equals(device.mac)) {
                    devList.add(new DeviceData(device.name,device.mac,device.rssi));
                    Toast.makeText(this, "Mac is : "+device.mac, Toast.LENGTH_SHORT).show();
                }
            }
        }else {
            devList.add(new DeviceData(device.name,device.mac,device.rssi));
        }

     */

    }

    double getDistance(int rssi, int txPower) {
        /*
         * RSSI = TxPower - 10 * n * lg(d)
         * n = 2 (in free space)
         *
         * d = 10 ^ ((TxPower - RSSI) / (10 * n))
         */

        return Math.pow(10d, ((double) txPower - rssi) / (10 * 2));
    }

    public class CunstomHandler extends BaseMessageHandler<MapLocation> {

        public CunstomHandler(MapLocation activity) {
            super(activity);
        }

        @Override
        protected void handleMessage(MapLocation home, Message msg) {

        }


    }
}