package com.example.battery2_pending;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

public class BatteryReceiver extends BroadcastReceiver {

    private BatteryLevelListener listener;

    // Interfaz para escuchar cambios en el nivel de batería
    public interface BatteryLevelListener {
        void onBatteryLevelChanged(int level);
    }

    public BatteryReceiver(BatteryLevelListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int battery = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        Log.d("BatteryReceiver", "Nivel de batería: " + battery + "%");

        // Notificar al listener
        if (listener != null) {
            listener.onBatteryLevelChanged(battery);
        }
    }
}
