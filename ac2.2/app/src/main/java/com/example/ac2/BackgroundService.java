package com.example.ac2;

import static android.app.PendingIntent.FLAG_IMMUTABLE;
import static android.app.PendingIntent.FLAG_MUTABLE;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class BackgroundService extends Service {
    private static final int ID_NOTIFICACAO = 1;
    private Handler manipulador;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manipulador = new Handler(getMainLooper());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        manipulador.postDelayed(() -> {
            mostrarNotificacaoRemedio();
            stopSelf();
        }, 2000); // Atraso de 2 segundos

        return START_NOT_STICKY;
    }

    private void mostrarNotificacaoRemedio() {
        try {
            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    this,
                    0,
                    intent,
                    FLAG_IMMUTABLE);

            Notification notificacao = new NotificationCompat.Builder(this, AppCanalComunicacao.ID_CANAL)
                    .setContentTitle("Hora do remédio")
                    .setContentText("Confira seus remédios, está na hora de tomar!")
                    .setSmallIcon(R.drawable.alert)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();

            NotificationManager gerenciador =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            gerenciador.notify(ID_NOTIFICACAO, notificacao);
        } catch (Exception e) {
            Log.e("Notificacao", "Erro ao mostrar notificação", e);
        }
    }

}
