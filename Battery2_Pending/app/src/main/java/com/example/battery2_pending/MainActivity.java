package com.example.battery2_pending;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.battery2_pending.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements BatteryReceiver.BatteryLevelListener {

    private BatteryReceiver batteryReceiver;
    private BatteryViewModel viewModel;
    private ActivityMainBinding binding;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;

    // Referencias para el TextView y ProgressBar
    private TextView batteryLevelText;
    private ProgressBar batteryProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(BatteryViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        batteryLevelText = binding.batteryLevelText;
        batteryProgressBar = binding.batteryProgressBar;

        batteryReceiver = new BatteryReceiver(this);

        Intent intent = new Intent(this, BatteryReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        binding.updateButton.setOnClickListener(v -> sendPendingIntent());
    }

    private void sendPendingIntent() {
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
        Log.d("MainActivity", "PendingIntent enviado para actualizar la batería");
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, filter);
        Log.d("MainActivity", "BroadcastReceiver registrado");
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(batteryReceiver);
        Log.d("MainActivity", "BroadcastReceiver desregistrado");
    }

    @Override
    public void onBatteryLevelChanged(int level) {
        viewModel.setBatteryLevel(level); // Actualizar el ViewModel
        batteryLevelText.setText(level + "%");
        batteryProgressBar.setProgress(level); // Actualizar el ProgressBar
    }
}
