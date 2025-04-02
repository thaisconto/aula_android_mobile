package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edtTitulo, edtAutor;
    Spinner spnCategoria;
    CheckBox chkLido;
    Button btnSalvar;
    ListView listViewLivros;
    BancoHelper databaseHelper;
    ArrayAdapter<String> adapter;
    ArrayList<String> listaLivros;
    ArrayList<Integer> listaIds;
    int livroIdSelecionado = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            // Vincular componentes
            edtTitulo = findViewById(R.id.titleEditText);
            edtAutor = findViewById(R.id.authorEditText);
            spnCategoria = findViewById(R.id.categorySpinner);
            chkLido = findViewById(R.id.readCheckBox);
            btnSalvar = findViewById(R.id.saveButton);
            listViewLivros = findViewById(R.id.booksListView);

            databaseHelper = new BancoHelper(this);

            // Configurar botão salvar
            btnSalvar.setOnClickListener(v -> salvarLivro());

            // Configurar listeners da lista
            configurarListListeners();

            // Carregar dados iniciais
            carregarLivros();
        } catch (Exception e) {
            Toast.makeText(this, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void salvarLivro() {
        String titulo = edtTitulo.getText().toString();
        String autor = edtAutor.getText().toString();
        String categoria = spnCategoria.getSelectedItem().toString();
        int lido = chkLido.isChecked() ? 1 : 0;

        if (titulo.isEmpty() || autor.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (livroIdSelecionado == -1) {
            // Inserir novo livro
            long resultado = databaseHelper.inserirLivro(titulo, autor, categoria);
            if (resultado != -1) {
                Toast.makeText(this, "Livro salvo!", Toast.LENGTH_SHORT).show();
                limparCampos();
                carregarLivros();
            } else {
                Toast.makeText(this, "Erro ao salvar!", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Atualizar livro existente
            int resultado = databaseHelper.atualizarLivro(livroIdSelecionado, titulo, autor, categoria);
            if (resultado > 0) {
                Toast.makeText(this, "Livro atualizado!", Toast.LENGTH_SHORT).show();
                limparCampos();
                carregarLivros();
                btnSalvar.setText("Salvar");
                livroIdSelecionado = -1;
            } else {
                Toast.makeText(this, "Erro ao atualizar!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void configurarListListeners() {
        // Clique para editar
        listViewLivros.setOnItemClickListener((parent, view, position, id) -> {
            livroIdSelecionado = listaIds.get(position);
            String[] livro = listaLivros.get(position).split(" - ");

            edtTitulo.setText(livro[1]); // Título
            edtAutor.setText(livro[2]);  // Autor

            // Definir categoria no Spinner
            for (int i = 0; i < spnCategoria.getCount(); i++) {
                if (spnCategoria.getItemAtPosition(i).toString().equals(livro[3])) {
                    spnCategoria.setSelection(i);
                    break;
                }
            }

            chkLido.setChecked(livro[4].equals("Sim")); // Status de leitura
            btnSalvar.setText("Atualizar");
        });

        // Clique longo para excluir
        listViewLivros.setOnItemLongClickListener((parent, view, position, id) -> {
            int idLivro = listaIds.get(position);
            int deletado = databaseHelper.excluirLivro(idLivro);
            if (deletado > 0) {
                Toast.makeText(this, "Livro excluído!", Toast.LENGTH_SHORT).show();
                carregarLivros();
                if (livroIdSelecionado == idLivro) {
                    limparCampos();
                    livroIdSelecionado = -1;
                    btnSalvar.setText("Salvar");
                }
            }
            return true;
        });
    }

    private void carregarLivros() {
        Cursor cursor = databaseHelper.listarLivro();
        listaLivros = new ArrayList<>();
        listaIds = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String titulo = cursor.getString(1);
                String autor = cursor.getString(2);
                String categoria = cursor.getString(3);
                String lido = cursor.getInt(4) == 1 ? "Sim" : "Não";

                listaLivros.add(id + " - " + titulo + " - " + autor + " - " + categoria + " - " + lido);
                listaIds.add(id);
            } while (cursor.moveToNext());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaLivros);
        listViewLivros.setAdapter(adapter);
    }

    private void limparCampos() {
        edtTitulo.setText("");
        edtAutor.setText("");
        spnCategoria.setSelection(0);
        chkLido.setChecked(false);
    }
}