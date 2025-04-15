package com.example.aula;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity6 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main6);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        //SERVICES
        Button btnIniciar = findViewById(R.id.button1);
        btnIniciar.setOnClickListener(v -> {
            Intent intent = new Intent(this, MeuServico.class);
            startService(intent);
        });

        //THREAD
        minhaThread t = new minhaThread();
        t.start(); // o start chama o run()
    }

    class minhaThread extends Thread{
        public void run(){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            TextView t = (TextView) findViewById(R.id.textView1);
            t.setText("Thread Iniciada");
        }

    }

    //deixar ela static ou tornar uma classe externa
    public static class MeuServico extends Service {
        private Handler handler = new Handler();
        private int segundos = 0;

        @Override
        public void onCreate() {
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            segundos = 0;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.i("Service", "run...");
                    segundos++;
                    if (segundos < 10) {
                        handler.postDelayed(this, 1000);
                    } else {
                        Log.i("Service", "10 segundos depois...");
                        Toast.makeText(getApplicationContext(), "10 segundos se passaram!", Toast.LENGTH_LONG).show();
                        stopSelf();
                    }
                }
            }, 1000);
            return Service.START_NOT_STICKY;
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

    }

    }