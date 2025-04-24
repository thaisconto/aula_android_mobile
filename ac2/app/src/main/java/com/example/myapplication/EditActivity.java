package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    private EditText editNome, editHorario, editDescricao;
    private CheckBox checkTomado;
    private BancoHelper bancoHelper;
    private int remedioId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        bancoHelper = new BancoHelper(this);

        // Inicializar views
        editNome = findViewById(R.id.editNome);
        editHorario = findViewById(R.id.editHorario);
        editDescricao = findViewById(R.id.editDescricao);
        checkTomado = findViewById(R.id.checkTomado);

        // Verificar se é edição (recebe ID do remédio)
        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            remedioId = intent.getIntExtra("id", -1);
            carregarRemedio(remedioId);
        }

        // Configurar botão salvar
        findViewById(R.id.btnSalvar).setOnClickListener(v -> salvarRemedio());
    }

    private void carregarRemedio(int id) {
        Cursor cursor = bancoHelper.getRemedioById(id);
        if (cursor.moveToFirst()) {
            editNome.setText(cursor.getString(cursor.getColumnIndexOrThrow(BancoHelper.COLUMN_NOME)));
            editHorario.setText(cursor.getString(cursor.getColumnIndexOrThrow(BancoHelper.COLUMN_HORARIO)));
            editDescricao.setText(cursor.getString(cursor.getColumnIndexOrThrow(BancoHelper.COLUMN_DESCRICAO)));
            checkTomado.setChecked(cursor.getInt(cursor.getColumnIndexOrThrow(BancoHelper.COLUMN_TOMADO)) == 1);
        }
        cursor.close();
    }

    private void salvarRemedio() {
        String nome = editNome.getText().toString().trim();
        String horario = editHorario.getText().toString().trim();
        String descricao = editDescricao.getText().toString().trim();
        int tomado = checkTomado.isChecked() ? 1 : 0;

        // Validação simples
        if (nome.isEmpty()) {
            editNome.setError("Informe o nome do remédio");
            return;
        }

        if (horario.isEmpty()) {
            editHorario.setError("Informe o horário");
            return;
        }

        // Salvar no banco de dados
        if (remedioId == -1) {
            // Novo remédio
            bancoHelper.inserirRemedio(nome, horario, descricao, tomado);
        } else {
            // Atualizar remédio existente
            bancoHelper.atualizarRemedio(remedioId, nome, horario, descricao, tomado);
        }

        // Retornar para MainActivity com resultado OK
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onDestroy() {
        bancoHelper.close();
        super.onDestroy();
    }
}