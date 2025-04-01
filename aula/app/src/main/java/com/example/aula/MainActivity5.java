package com.example.aula;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity5 extends AppCompatActivity {
    EditText edtNome, edtEmail;
    Button btnSalvar;
    ListView listViewUsuarios;
    BancoHelper databaseHelper;
    ArrayAdapter<String> adapter;
    ArrayList<String> listaUsuarios;
    ArrayList<Integer> listaIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main5);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Carregar usuários ao abrir a tela
        carregarUsuarios();

        btnSalvar.setOnClickListener(v -> {
            String nome = edtNome.getText().toString();
            String email = edtEmail.getText().toString();
            if (!nome.isEmpty() && !email.isEmpty()) {
                long resultado = databaseHelper.inserirUsuario(nome, email);
                if (resultado != -1) {
                    Toast.makeText(this, "Usuário salvo!", Toast.LENGTH_SHORT).show();
                    edtNome.setText("");
                    edtEmail.setText("");
                    carregarUsuarios();
                } else {
                    Toast.makeText(this, "Erro ao salvar!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            }
        });

        listViewUsuarios.setOnItemClickListener((parent, view, position, id) -> {
            int userId = listaIds.get(position);
            String nome = listaUsuarios.get(position).split(" - ")[1];
            String email = listaUsuarios.get(position).split(" - ")[2];
            edtNome.setText(nome);
            edtEmail.setText(email);
            btnSalvar.setText("Atualizar");

            btnSalvar.setOnClickListener(v -> {
                String novoNome = edtNome.getText().toString();
                String novoEmail = edtEmail.getText().toString();
                if (!novoNome.isEmpty() && !novoEmail.isEmpty()) {
                    int resultado = databaseHelper.atualizarUsuario(userId, novoNome, novoEmail);
                    if (resultado > 0) {
                        Toast.makeText(this, "Usuário atualizado!", Toast.LENGTH_SHORT).show();
                        carregarUsuarios();
                        edtNome.setText("");
                        edtEmail.setText("");
                        btnSalvar.setText("Salvar");
                    } else {
                        Toast.makeText(this, "Erro ao atualizar!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            listViewUsuarios.setOnItemLongClickListener((adapterView, view1, pos, l) -> {
                int idUsuario = listaIds.get(pos);
                int deletado = databaseHelper.excluirUsuario(idUsuario);
                if (deletado > 0) {
                    Toast.makeText(this, "Usuário excluído!", Toast.LENGTH_SHORT).show();
                    carregarUsuarios();
                }
                return true;
            });
        });
    }

    // Metodo corrigido fora do onCreate()
    private void carregarUsuarios() {
        Cursor cursor = databaseHelper.listarUsuarios();
        listaUsuarios = new ArrayList<>();
        listaIds = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String nome = cursor.getString(1);
                String email = cursor.getString(2);
                listaUsuarios.add(id + " - " + nome + " - " + email);
                listaIds.add(id);
            } while (cursor.moveToNext());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaUsuarios);
        listViewUsuarios.setAdapter(adapter);
    }
}
