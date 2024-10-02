package com.example.battery1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private BatteryReceiver batteryReceiver;
    private TextView textView;
    private Button updateButton;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        updateButton = findViewById(R.id.updateButton);
        progressBar = findViewById(R.id.progressBar);

        batteryReceiver = new BatteryReceiver(textView,progressBar);
        updateButton.setOnClickListener(v -> updateBatteryStatus());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Registrar el BroadcastReceiver
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, filter);
        Log.d("MainActivity", "BroadcastReceiver registrado satisfactoriamente");
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Desregistrar el BroadcastReceiver
        unregisterReceiver(batteryReceiver);
        Log.d("MainActivity", "BroadcastReceiver desregistrado satisfactoriamente");
    }

    private void updateBatteryStatus() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);

        if (batteryStatus != null) {
            int batteryLevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            batteryReceiver.onReceive(this, batteryStatus);
            Log.d("MainActivity", "Nivel de batería actualizado: " + batteryLevel + "%");
        }
    }
}
