package com.example.autoalkatreszshop;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import androidx.core.app.NotificationCompat;

public class MyJobService extends JobService {

  @Override
  public boolean onStartJob(JobParameters params) {
    Log.d("JobService", "Job elindult!");

    // Notification
    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    String channelId = "job_channel";
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
      NotificationChannel channel = new NotificationChannel(channelId, "JobScheduler Értesítések", NotificationManager.IMPORTANCE_DEFAULT);
      manager.createNotificationChannel(channel);
    }

    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
      .setContentTitle("JobScheduler")
      .setContentText("A háttérfeladat sikeresen elindult.")
      .setPriority(NotificationCompat.PRIORITY_DEFAULT);

    manager.notify(3, builder.build());

    // Itt fejezzük be a munkát
    jobFinished(params, false);
    return true;
  }

  @Override
  public boolean onStopJob(JobParameters params) {
    Log.d("JobService", "Job megszakadt.");
    return true; // Újrapróbálható legyen
  }
}
