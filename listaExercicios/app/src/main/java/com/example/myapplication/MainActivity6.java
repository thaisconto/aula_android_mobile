package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity6 extends AppCompatActivity {

    private CheckBox checkBoxNotificacoes, checkBoxModoEscuro, checkBoxLembrarLogin;
    private Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main6);

        // Ajuste de tela
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Referências dos componentes
        checkBoxNotificacoes = findViewById(R.id.checkBoxNotificacoes);
        checkBoxModoEscuro = findViewById(R.id.checkBoxModoEscuro);
        checkBoxLembrarLogin = findViewById(R.id.checkBoxLembrarLogin);
        btnSalvar = findViewById(R.id.btnSalvar);

        // Listener do botão
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarPreferencias();
            }
        });
    }

    // Metodo salvar as preferencias
    private void salvarPreferencias() {
        StringBuilder preferencias = new StringBuilder();

        if (checkBoxNotificacoes.isChecked()) {
            preferencias.append("Receber notificações\n");
        }
        if (checkBoxModoEscuro.isChecked()) {
            preferencias.append("Modo escuro\n");
        }
        if (checkBoxLembrarLogin.isChecked()) {
            preferencias.append("Lembrar login\n");
        }

        // Verifica se alguma opção foi selecionada
        String mensagem = preferencias.length() > 0 ? preferencias.toString() : "Nenhuma preferência foi escolhida";

        // Exibe um Toast com as preferências escolhidas
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
    }

    // Metodo para carregar a Activity do menu inicial
    public void carregarMenuInicial(View view) {
        Intent intent = new Intent(MainActivity6.this, MainActivity.class);
        startActivity(intent);
    }
}
