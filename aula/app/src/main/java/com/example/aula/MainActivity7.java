package com.example.aula;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity7 extends AppCompatActivity {

    private EditText editTextFileSize;
    private Button btnDownload;
    private TextView txtStatus;
    private ProgressBar progressBar;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        // Inicializa os componentes
        editTextFileSize = findViewById(R.id.editTextFileSize);
        btnDownload = findViewById(R.id.btnDownload);
        txtStatus = findViewById(R.id.txtStatus);
        progressBar = findViewById(R.id.progressBar);

        // Configura o listener do botão
        btnDownload.setOnClickListener(v -> startDownloadSimulation());
    }

    private void startDownloadSimulation() {
        // Obtem o tamanho do arquivo digitado
        String fileSizeStr = editTextFileSize.getText().toString();

        if (fileSizeStr.isEmpty()) {
            txtStatus.setText("Por favor, digite o tamanho do arquivo");
            return;
        }

        // Desabilita o botao durante o download
        btnDownload.setEnabled(false);
        txtStatus.setText("Download iniciado...");
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(0);

        // Simula o download em uma thread separada
        new Thread(() -> {
            try {
                int fileSize = Integer.parseInt(fileSizeStr);
                int progress = 0;

                // Simula progresso do download
                while (progress < 100) {
                    progress += 5; // Incrementa o progresso
                    int finalProgress = progress;

                    // Atualiza a UI na thread principal
                    handler.post(() -> {
                        progressBar.setProgress(finalProgress);
                        txtStatus.setText("Download em progresso: " + finalProgress + "%");
                    });

                    Thread.sleep(200); // Simula tempo de download
                }

                // Download completo
                handler.post(() -> {
                    progressBar.setVisibility(View.INVISIBLE);
                    txtStatus.setText("Download completo! Arquivo de " + fileSize + "MB baixado.");
                    btnDownload.setEnabled(true);
                });

            } catch (NumberFormatException e) {
                handler.post(() -> {
                    txtStatus.setText("Tamanho inválido. Use números apenas.");
                    btnDownload.setEnabled(true);
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}