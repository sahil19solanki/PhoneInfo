package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
BroadcastReceiver batteryBroadcast ;
    IntentFilter intentFilter;

    float totalMemory;
    ListView listView;
    TextView level;
    ArrayList<String> values = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        level = findViewById(R.id.level);

        listView = findViewById(R.id.ListView);
        totalMem();
        inival();


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,values);
        listView.setAdapter(adapter);
        batterylevel();

    }
private  void batterylevel(){
        intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        batteryBroadcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())){
                   level.setText(String.valueOf(intent.getIntExtra("level",0))+"% Battery");
                }
            }
        };
}
    private void inival() {
        values.add("MANUFACTURER :"+Build.MANUFACTURER);
        values.add("MODEL :"+Build.MODEL);
        values.add("Ram :" + totalMemory + "MB");
        values.add("Android version :"+Build.VERSION.RELEASE);

    }

    private void totalMem() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        totalMemory = (memoryInfo.totalMem / (1024 * 1024));
    }



    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(batteryBroadcast, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(batteryBroadcast);
    }
}
