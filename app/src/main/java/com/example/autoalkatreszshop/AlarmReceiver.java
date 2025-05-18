package com.example.autoalkatreszshop;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    String title = intent.getStringExtra("title");
    String message = intent.getStringExtra("message");

    String channelId = "alarm_channel";
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      NotificationChannel channel = new NotificationChannel(
        channelId,
        "Értesítések",
        NotificationManager.IMPORTANCE_DEFAULT
      );
      NotificationManager manager = context.getSystemService(NotificationManager.class);
      if (manager != null) manager.createNotificationChannel(channel);
    }

    if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS)
      != PackageManager.PERMISSION_GRANTED) {
      return;
    }
    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
      .setContentTitle(title)
      .setContentText(message)
      .setPriority(NotificationCompat.PRIORITY_DEFAULT);

    NotificationManagerCompat.from(context).notify(2, builder.build());
  }
}
