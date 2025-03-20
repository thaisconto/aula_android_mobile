package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity5 extends AppCompatActivity {

    private EditText editTextNome;
    private Button btnGerar;
    private LinearLayout layoutCheckBoxes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main5);

        // Ajustes
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializando os componentes
        editTextNome = findViewById(R.id.editTextNome);
        btnGerar = findViewById(R.id.btnGerar);
        layoutCheckBoxes = findViewById(R.id.layoutCheckBoxes);

        // Definir ação do botão
        btnGerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gerarCheckBoxes();
            }
        });
    }

    // Metodo separado para gerar os checkboxes dinamicamente
    private void gerarCheckBoxes() {
        layoutCheckBoxes.removeAllViews(); // Limpa os checkboxes anteriores

        String nome = editTextNome.getText().toString().trim();

        for (char letra : nome.toCharArray()) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(String.valueOf(letra));
            layoutCheckBoxes.addView(checkBox);
        }
    }

    // Metodo para mudar de tela
    public void carregarMenuInicial(View view) {
        Intent intent = new Intent(MainActivity5.this, MainActivity.class);
        startActivity(intent);
    }
}
