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

public class MainActivity3 extends AppCompatActivity {

    private EditText editValor1, editValor2;
    private TextView textResultado;
    private Button btnSomar, btnSubtrair, btnMultiplicar, btnDividir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Referenciando os componentes
        editValor1 = findViewById(R.id.editValor1);
        editValor2 = findViewById(R.id.editValor2);
        textResultado = findViewById(R.id.textResultado);

        btnSomar = findViewById(R.id.btnSomar);
        btnSubtrair = findViewById(R.id.btnSubtrair);
        btnMultiplicar = findViewById(R.id.btnMultiplicar);
        btnDividir = findViewById(R.id.btnDividir);

        // Adicionando os eventos de clique para cada botão
        btnSomar.setOnClickListener(v -> calcular('+'));
        btnSubtrair.setOnClickListener(v -> calcular('-'));
        btnMultiplicar.setOnClickListener(v -> calcular('*'));
        btnDividir.setOnClickListener(v -> calcular('/'));
    }

    private void calcular(char operacao) {
        String valor1Str = editValor1.getText().toString();
        String valor2Str = editValor2.getText().toString();

        if (valor1Str.isEmpty() || valor2Str.isEmpty()) {
            textResultado.setText("Preencha os dois números!");
            return;
        }

        double num1 = Double.parseDouble(valor1Str);
        double num2 = Double.parseDouble(valor2Str);
        double resultado = 0;

        switch (operacao) {
            case '+':
                resultado = num1 + num2;
                break;
            case '-':
                resultado = num1 - num2;
                break;
            case '*':
                resultado = num1 * num2;
                break;
            case '/':
                if (num2 == 0) {
                    textResultado.setText("Erro: Divisão por zero!");
                    return;
                }
                resultado = num1 / num2;
                break;
        }

        textResultado.setText("Resultado: " + resultado);
    }

    public void carregarMenuInicial(View view)
    {
        Intent intent = new Intent(MainActivity3.this, MainActivity.class);
        startActivity(intent); //esse metodo abre uma nova activity
    }

}