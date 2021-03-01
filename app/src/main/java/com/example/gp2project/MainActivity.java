package com.example.gp2project;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moko.support.MokoConstants;
import com.moko.support.MokoSupport;
import com.moko.support.callback.MokoScanDeviceCallback;
import com.moko.support.entity.DeviceInfo;
import com.moko.support.handler.BaseMessageHandler;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements MokoScanDeviceCallback {

    private Handler mHandler;
    public static ArrayList<DeviceData> devList = new ArrayList<>();
    public static ArrayList<DeviceData> devListfilter = new ArrayList<>();
    List<String>  mac = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new com.example.gp2project.ItemFragment()).commit();

        mHandler = new CunstomHandler(this);

//        EventBus.getDefault().register(this);
        // 注册广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver, filter);
        if (!MokoSupport.getInstance().isBluetoothOpen()) {
            MokoSupport.getInstance().enableBluetooth();
        } else {
            startScan();
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
                    MainActivity.this.finish();
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
    public void onStopScan() {

        Toast.makeText(this, "stop Scan", Toast.LENGTH_SHORT).show();
        devListfilter.clear();


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

    private final BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;
            switch (item.getItemId()) {

                case R.id.item:
                    selectedFragment = new com.example.gp2project.ItemFragment();
                    break;

                case R.id.location:
                    selectedFragment= new com.example.gp2project.LocationFragment();
                    break;

                case R.id.group:
                    selectedFragment= new com.example.gp2project.GroupFragment();
                    break;

                case R.id.profile:
                    selectedFragment = new com.example.gp2project.ProfileFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };


    public class CunstomHandler extends BaseMessageHandler<MainActivity> {

        public CunstomHandler(MainActivity activity) {
            super(activity);
        }

        @Override
        protected void handleMessage(MainActivity home, Message msg) {

        }


    }

    @Override
    public void onScanDevice(DeviceInfo device) {

        // Toast.makeText(this, beaconInfo.distance, Toast.LENGTH_SHORT).show();
        // BeaconXInfo beaconInfo = new BeaconXInfoParseableImpl().parseDeviceInfo(device);

        // Toast.makeText(this, "Name is : "+device.name, Toast.LENGTH_SHORT).show();
        // Toast.makeText(this, "Mac is : "+device.mac, Toast.LENGTH_SHORT).show();

        if (mac.contains(device.mac)) {

        } else {
            mac.add(device.mac);
            devList.add(new DeviceData(device.name, device.mac, device.rssi));
            /*if (device.name != null)
            {
                devList.add(new DeviceData(device.name,device.mac,device.rssi));
            }

             */
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
}