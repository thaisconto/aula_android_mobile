package com.example.aula;

import static android.app.ProgressDialog.show;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buttonEnviar = findViewById(R.id.buttonEnviar);
        buttonEnviar.setOnClickListener(v-> {
            Toast.makeText(getApplicationContext(), "Botão pressionado!",
                    Toast.LENGTH_SHORT).show();
        });

        CheckBox checkBox = findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener((buttonView,
                                             isChecked) -> {
            if (isChecked) {
                Toast.makeText(getApplicationContext(), "Opção selecionada!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Opção desmarcada!", Toast.LENGTH_SHORT).show();
            }
        });

        EditText editText = findViewById(R.id.editTextNome);
        editText.setHint("Digite o seu Nome");

        //EditText editText = findViewById(R.id.editTextNome);
        Toast.makeText(this, "Seu nome é:" + editText.getText(),
                Toast.LENGTH_SHORT).show();

    }


    // Metodo selecionar radio button

    public void checkRadios (View view){
        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        int selectId = radioGroup.getCheckedRadioButtonId();

        if (selectId == -1){
            Toast.makeText(MainActivity.this, "Selecione uma opção", Toast.LENGTH_LONG).show();

        } else {
            RadioButton selectRadioButton = findViewById(selectId);
            String selectdText = selectRadioButton.getText().toString();

            Toast.makeText(MainActivity.this, "Selecionado:" + selectdText, Toast.LENGTH_LONG).show();
        }
    }

    public void eventoBotao(View view)
    {
        Toast.makeText(this, "O botão foi clicado", Toast.LENGTH_SHORT).show();
    }

    public void carregarActivityNova(View view)
    {
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent); //esse metodo abre uma nova activity
    }


}