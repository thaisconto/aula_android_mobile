package com.example.aula;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity9 extends AppCompatActivity {
    private int contador = 0;
    private TextView textoContador;
    private Button botaoContador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main9);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // pegando o texto e botão do xml
        textoContador = findViewById(R.id.textoContador);
        botaoContador = findViewById(R.id.botaoContador);

        //acao de somar no contador ao clicar no botao
        botaoContador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contador++;
                textoContador.setText("Contador: " + contador);

                if (contador == 5) {
                    // Dispara a notificação quando chegar a 5 cliques
                    Intent serviceIntent = new Intent(MainActivity9.this, BackgroundService.class);
                    startService(serviceIntent);
                }
            }
        });
    }
}