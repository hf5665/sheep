package com.example.hfchuang.wifi_onoff;

import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {
    private WifiManager wifiManager;
    private Button turnOnWifiButn;
    private Button turnOffWifiButn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wifiManager =(WifiManager)this.getSystemService(WIFI_SERVICE);
        turnOnWifiButn=(Button)findViewById(R.id.button);
        turnOffWifiButn=(Button)findViewById(R.id.button2);

        //enable wifi
        turnOnWifiButn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(true);
                }
            }
        });

        //disable wifi
        turnOffWifiButn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(false);
                }
            }
        });
        }

    }


