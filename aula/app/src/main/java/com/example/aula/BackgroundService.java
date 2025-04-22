package com.example.aula;

import static android.app.PendingIntent.FLAG_MUTABLE;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class BackgroundService extends Service {
    private static final int NOTIFICATION_ID_DELAYED = 1;
    private static final int NOTIFICATION_ID_PARABENS = 2; // ID específico para a notificação de parabéns

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Notificação original (com delay de 5s)
    private void showDelayedNotification() {
        try {
            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_MUTABLE);

            Notification notification = new NotificationCompat.Builder(this, "default")
                    .setContentTitle("Notificação de Evento")
                    .setContentText("Algo aconteceu!")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentIntent(pendingIntent)
                    .build();

            NotificationManager notificationManager = (NotificationManager)
                    getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFICATION_ID_DELAYED, notification);
        } catch (Exception e) {
            Log.e("Notificacao", "Erro na notificação delay", e);
        }
    }

    // Notificação de parabéns ao chegar no contador 5
    private void showParabensNotification() {
        try {
            Intent intent = new Intent(this, MainActivity9.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

            Notification notification = new NotificationCompat.Builder(this, "default")
                    .setContentTitle("Parabéns!")
                    .setContentText("Você clicou 5 vezes!")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .build();

            NotificationManager manager = (NotificationManager)
                    getSystemService(Context.NOTIFICATION_SERVICE);

            manager.notify(NOTIFICATION_ID_PARABENS, notification);
            Log.d("Notificacao", "Notificação de parabéns disparada");
        } catch (Exception e) {
            Log.e("Notificacao", "Erro na notificação de parabéns", e);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getBooleanExtra("is_contador", false)) {
            showParabensNotification();
            stopSelf();
            return START_NOT_STICKY;
        } else {
            new Thread(() -> {
                try {
                    Thread.sleep(5000);
                    showDelayedNotification();
                } catch (InterruptedException e) {
                    Log.e("Notificacao", "Thread interrompida", e);
                    Thread.currentThread().interrupt();
                }
            }).start();
            return START_STICKY;
        }
    }
}