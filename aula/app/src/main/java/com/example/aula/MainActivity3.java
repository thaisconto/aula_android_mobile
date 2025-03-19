package com.example.aula;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity3 extends AppCompatActivity {

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

        final Button button = (Button) findViewById(R.id.botaoOk);
        button.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                EditText campoEndereco = (EditText)
                        findViewById(R.id.campoEndereco);
                String endereco = campoEndereco.getText().toString();
                Uri uri = Uri.parse(endereco);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }

        });

        final Button button2 = (Button) findViewById(R.id.botao2);
        button.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v) {
                EditText campoTelefone = (EditText) findViewById(R.id.
                        campoTelefone);
                String telefone = campoTelefone.getText().toString();
                Uri uri = Uri.parse("tel:"+telefone);
                Intent intent = new Intent(Intent.ACTION_CALL, uri);
                startActivity(intent);
            }
        });

    }
}