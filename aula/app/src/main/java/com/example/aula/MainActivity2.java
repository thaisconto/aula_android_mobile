package com.example.aula;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private List<CheckBox> checkBoxList = new ArrayList<>();

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

        //metodo chack box dinamico

        LinearLayout checkBoxContainer = findViewById(R.id.checkBoxContainer);
        Button btnCheck = findViewById(R.id.btnCheck);
        String[] opcoes = {"Opção 1", "Opção 2", "Opção 3", "Opção 4", "Opção 5"};

        for (String opcao : opcoes)
        {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(opcao);
            checkBoxContainer.addView(checkBox);
            checkBoxList.add(checkBox);
        }
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder selecionados = new StringBuilder("Selecionado: ");
                for (CheckBox checkBox : checkBoxList) {
                    if (checkBox.isChecked()) {
                        selecionados.append(checkBox.getText()).append(", ");
                    }
                }
                if (selecionados.toString().equals("Selecionado: ")) {
                    selecionados = new StringBuilder("Nenhuma opção selecionada!");
                } else {
                    selecionados.setLength(selecionados.length() - 2);
                }
                Toast.makeText(getApplicationContext(), selecionados.toString(), Toast.LENGTH_SHORT).show();
            }


        });


    }



    public void carregarActivity1(View view)
    {
        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
        startActivity(intent); //esse metodo abre uma nova activity
    }

}