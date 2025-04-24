package com.example.ac2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


public class MainActivity extends AppCompatActivity {

    private ListView listViewRemedios;
    private BancoHelper bancoHelper;
    private RemedioAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Iniciar serviço de notificação
        Intent intentServico = new Intent(this, BackgroundService.class);
        startService(intentServico);

        bancoHelper = new BancoHelper(this);
        listViewRemedios = findViewById(R.id.listViewRemedios);

        // Botao para adicionar novo remédio
        findViewById(R.id.btnAdicionar).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditActivity.class);
            startActivityForResult(intent, 1);
        });

        carregarRemedios();
    }

    private void carregarRemedios() {
        Cursor cursor = bancoHelper.listarRemedios();
        adapter = new RemedioAdapter(this, cursor);
        listViewRemedios.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            carregarRemedios(); // Atualiza a lista após edição/adição
        }
    }

    private class RemedioAdapter extends CursorAdapter {
        RemedioAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.activity_lista_remedios, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // Obter dados do cursor
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(BancoHelper.COLUMN_ID));
            String nome = cursor.getString(cursor.getColumnIndexOrThrow(BancoHelper.COLUMN_NOME));
            String horario = cursor.getString(cursor.getColumnIndexOrThrow(BancoHelper.COLUMN_HORARIO));
            String descricao = cursor.getString(cursor.getColumnIndexOrThrow(BancoHelper.COLUMN_DESCRICAO));
            int tomado = cursor.getInt(cursor.getColumnIndexOrThrow(BancoHelper.COLUMN_TOMADO));

            // Referências aos views
            ImageView imgStatus = view.findViewById(R.id.imgStatus);
            TextView txtNome = view.findViewById(R.id.txtNome);
            TextView txtHorario = view.findViewById(R.id.txtHorario);
            TextView txtDescricao = view.findViewById(R.id.txtDescricao);
            ImageButton btnEditar = view.findViewById(R.id.btnEditar);
            ImageButton btnExcluir = view.findViewById(R.id.btnExcluir);

            // Preencher dados
            txtNome.setText(nome);
            txtHorario.setText(horario);
            txtDescricao.setText(descricao);

            // Status (tomado/não tomado)
            imgStatus.setImageResource(R.drawable.check);
            if (tomado == 1) {
                imgStatus.setColorFilter(ContextCompat.getColor(context, android.R.color.holo_green_dark));
            } else {
                imgStatus.setColorFilter(ContextCompat.getColor(context, android.R.color.holo_red_dark));
            }

            // Botão editar
            btnEditar.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("id", id);
                startActivityForResult(intent, 1);
            });

            // Botao excluir
            btnExcluir.setOnClickListener(v -> new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Confirmar exclusão")
                    .setMessage("Deseja realmente excluir este remédio?")
                    .setPositiveButton("Excluir", (dialog, which) -> {
                        bancoHelper.excluirRemedio(id);
                        carregarRemedios();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show());

            // Clique no item marca como tomado/não tomado
            view.setOnClickListener(v -> {
                int novoStatus = tomado == 1 ? 0 : 1;
                bancoHelper.atualizarRemedio(id, nome, horario, descricao, novoStatus);
                carregarRemedios();
            });
        }
    }

    @Override
    protected void onDestroy() {
        bancoHelper.close();
        super.onDestroy();
    }
}