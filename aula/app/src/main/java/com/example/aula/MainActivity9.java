package com.example.aula;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity9 extends AppCompatActivity {
    private int contador = 0;
    private TextView textoContador;
    private Button botaoContador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);

        textoContador = findViewById(R.id.textoContador);
        botaoContador = findViewById(R.id.botaoContador);

        botaoContador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contador++;
                textoContador.setText("Contador: " + contador);

                if (contador == 5) {
                    // Configuração para disparar a notificação
                    Intent serviceIntent = new Intent(MainActivity9.this, BackgroundService.class);
                    serviceIntent.putExtra("is_contador", true); // ESSE PARÂMETRO É ESSENCIAL
                    startService(serviceIntent);

                    contador = 0; // Reinicia o contador após 5 cliques
                    textoContador.setText("Contador: " + contador);
                }
            }
        });
    }
}