package com.example.ac2;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class AppCanalComunicacao extends Application {
    public static final String ID_CANAL = "canal_remedios";

    @Override
    public void onCreate() {
        super.onCreate();
        criarCanalNotificacao();
    }

    private void criarCanalNotificacao() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel canal = new NotificationChannel(
                    ID_CANAL,
                    "Lembretes de Remédios",
                    NotificationManager.IMPORTANCE_DEFAULT);
            canal.setDescription("Canal para lembretes de medicamentos");

            NotificationManager gerenciador = getSystemService(NotificationManager.class);
            gerenciador.createNotificationChannel(canal);
        }
    }
}