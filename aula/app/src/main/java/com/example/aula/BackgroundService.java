package com.example.aula;

import static android.app.PendingIntent.FLAG_MUTABLE;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class BackgroundService extends Service {
    private static final int NOTIFICATION_ID_IMMEDIATE = 1;
    private static final int NOTIFICATION_ID_DELAYED = 2;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Notificação original (com delay de 5s) na activity 8
    private void showDelayedNotification() {
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
    }

    // Notificação de parabéns (imediata) com 5 cliques na activity 9
    private void showCongratulationsNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, "default")
                .setContentTitle("Parabéns!")
                .setContentText("Você clicou 5 vezes!")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID_IMMEDIATE, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Verifica se é para mostrar a notificação imediata (contador)
        boolean showCongratulations = intent.getBooleanExtra("show_congrats", false);

        if (showCongratulations) {
            showCongratulationsNotification();
            stopSelf(); // Encerra após mostrar a notificação
            return START_NOT_STICKY;
        }
        else {
            // Mantém o comportamento original com delay de 5s
            new Thread(() -> {
                try {
                    Thread.sleep(5000);
                    showDelayedNotification();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            return START_STICKY;
        }
    }
}