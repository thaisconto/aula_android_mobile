package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    private EditText editNome, editIdade;
    private Button btnVerificar;
    private TextView textResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        // Configurar insets de tela
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar os componentes
        editNome = findViewById(R.id.editNome);
        editIdade = findViewById(R.id.editIdade);
        btnVerificar = findViewById(R.id.btnVerificar);
        textResultado = findViewById(R.id.textResultado);

        // Configurar botão de verificação
        btnVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarIdade();
            }
        });
    }

    // Metodo para verificar a idade
    private void verificarIdade() {
        String nome = editNome.getText().toString();
        String idadeStr = editIdade.getText().toString();

        if (nome.isEmpty() || idadeStr.isEmpty()) {
            textResultado.setText("Por favor, preencha todos os campos!");
            return;
        }

        int idade = Integer.parseInt(idadeStr);
        String mensagem = idade >= 18 ? nome + ", você é maior de idade!" : nome + ", você é menor de idade!";
        textResultado.setText(mensagem);
    }


    public void carregarMenuInicial(View view)
    {
        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
        startActivity(intent); //esse metodo abre uma nova activity
    }
}
