package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        EditText editTextNome = findViewById(R.id.editTextText);

        EditText editTextIdade = findViewById(R.id.editTextText2);
        String idadeTexto = editTextIdade.getText().toString().trim();

        if (!idadeTexto.isEmpty()) {
            try {
                int number = Integer.parseInt(idadeTexto);

                if (number >= 18) {
                    Toast.makeText(this, editTextNome.getText() + " é maior ou igual a 18", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, editTextNome.getText() + " é menor que 18", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Digite uma idade válida", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Por favor, digite sua idade", Toast.LENGTH_SHORT).show();
        }
    }




    public void carregarMenuInicial(View view)
    {
        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
        startActivity(intent); //esse metodo abre uma nova activity
    }
}