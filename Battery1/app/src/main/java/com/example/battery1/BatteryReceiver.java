package com.example.battery1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BatteryReceiver extends BroadcastReceiver {
    private TextView textView;
    private ProgressBar progressBar;

    public BatteryReceiver(TextView textView, ProgressBar progressBar) {
        this.textView = textView;
        this.progressBar = progressBar;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        Log.d("BatteryReceiver", "Nivel de batería: " + batteryLevel + "%");

        // Actualiza el texto con el nivel de batería
        textView.setText(batteryLevel + "%");

        // Establece el progreso de la barra de progreso
        progressBar.setProgress(batteryLevel);
    }
}
