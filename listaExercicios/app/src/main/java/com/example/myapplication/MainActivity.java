package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
    }

    public void carregarExercicio1(View view)
    {
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent); //esse metodo abre uma nova activity
    }

    public void carregarExercicio2(View view)
    {
        Intent intent = new Intent(MainActivity.this, MainActivity3.class);
        startActivity(intent); //esse metodo abre uma nova activity
    }

    public void carregarExercicio3(View view)
    {
        Intent intent = new Intent(MainActivity.this, MainActivity4.class);
        startActivity(intent); //esse metodo abre uma nova activity
    }

    public void carregarExercicio4(View view)
    {
        Intent intent = new Intent(MainActivity.this, MainActivity5.class);
        startActivity(intent); //esse metodo abre uma nova activity
    }

    public void carregarExercicio5(View view)
    {
        Intent intent = new Intent(MainActivity.this, MainActivity6.class);
        startActivity(intent); //esse metodo abre uma nova activity
    }

    public void carregarExercicio6(View view)
    {
        Intent intent = new Intent(MainActivity.this, MainActivity7.class);
        startActivity(intent); //esse metodo abre uma nova activity
    }
}