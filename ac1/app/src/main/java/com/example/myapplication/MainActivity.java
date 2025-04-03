package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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
    private EditText edtTitulo, edtAutor;
    private Spinner spnCategoria;
    private CheckBox chkLido;
    private Button btnSalvar;
    private ListView listViewLivros;
    private BancoHelper databaseHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> listaLivros;
    private ArrayList<Integer> listaIds;
    private int livroIdSelecionado = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            inicializarComponentes();
            databaseHelper = new BancoHelper(this);
            configurarListeners();
            carregarLivros();
        } catch (Exception e) {
            Toast.makeText(this, "Erro na inicialização: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("MainActivity", "Erro no onCreate", e);
        }
    }

    private void inicializarComponentes() {
        edtTitulo = findViewById(R.id.titleEditText);
        edtAutor = findViewById(R.id.authorEditText);
        spnCategoria = findViewById(R.id.categorySpinner);
        chkLido = findViewById(R.id.readCheckBox);
        btnSalvar = findViewById(R.id.saveButton);
        listViewLivros = findViewById(R.id.booksListView);
    }

    private void configurarListeners() {
        btnSalvar.setOnClickListener(v -> salvarLivro());

        listViewLivros.setOnItemClickListener((parent, view, position, id) -> editarLivro(position));

        listViewLivros.setOnItemLongClickListener((parent, view, position, id) -> {
            excluirLivro(position);
            return true;
        });
    }

    private void salvarLivro() {
        try {
            String titulo = edtTitulo.getText().toString().trim();
            String autor = edtAutor.getText().toString().trim();
            String categoria = spnCategoria.getSelectedItem().toString();
            int lido = chkLido.isChecked() ? 1 : 0;

            if (validarCampos(titulo, autor)) return;

            if (livroIdSelecionado == -1) {
                inserirNovoLivro(titulo, autor, categoria, lido);
            } else {
                atualizarLivroExistente(titulo, autor, categoria, lido);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao salvar: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("MainActivity", "Erro no salvarLivro", e);
        }
    }

    private boolean validarCampos(String titulo, String autor) {
        if (titulo.isEmpty() || autor.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void inserirNovoLivro(String titulo, String autor, String categoria, int lido) {
        long resultado = databaseHelper.inserirLivro(titulo, autor, categoria, lido);
        if (resultado != -1) {
            Toast.makeText(this, "Livro salvo com sucesso!", Toast.LENGTH_SHORT).show();
            limparCampos();
            carregarLivros();
        } else {
            Toast.makeText(this, "Falha ao salvar livro!", Toast.LENGTH_SHORT).show();
        }
    }

    private void atualizarLivroExistente(String titulo, String autor, String categoria, int lido) {
        int resultado = databaseHelper.atualizarLivro(livroIdSelecionado, titulo, autor, categoria, lido);
        if (resultado > 0) {
            Toast.makeText(this, "Livro atualizado com sucesso!", Toast.LENGTH_SHORT).show();
            resetarFormulario();
        } else {
            verificarExistenciaLivro();
        }
    }

    private void resetarFormulario() {
        limparCampos();
        carregarLivros();
        btnSalvar.setText(R.string.botao_salvar);
        livroIdSelecionado = -1;
    }

    private void verificarExistenciaLivro() {
        try (Cursor livro = databaseHelper.getLivroById(livroIdSelecionado)) {
            if (livro.getCount() == 0) {
                Toast.makeText(this, "Livro não encontrado!", Toast.LENGTH_SHORT).show();
                resetarFormulario();
            } else {
                Toast.makeText(this, "Nenhuma alteração foi necessária.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void editarLivro(int position) {
        try {
            livroIdSelecionado = listaIds.get(position);
            String[] livro = listaLivros.get(position).split(" - ");

            edtTitulo.setText(livro[1]);
            edtAutor.setText(livro[2]);

            for (int i = 0; i < spnCategoria.getCount(); i++) {
                if (spnCategoria.getItemAtPosition(i).toString().equals(livro[3])) {
                    spnCategoria.setSelection(i);
                    break;
                }
            }

            chkLido.setChecked(livro[4].equals("Sim"));
            btnSalvar.setText("Atualizar");
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao editar: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("MainActivity", "Erro no editarLivro", e);
        }
    }

    private void excluirLivro(int position) {
        try {
            int idLivro = listaIds.get(position);
            int deletado = databaseHelper.excluirLivro(idLivro);

            if (deletado > 0) {
                Toast.makeText(this, "Livro excluído com sucesso!", Toast.LENGTH_SHORT).show();
                if (livroIdSelecionado == idLivro) {
                    resetarFormulario();
                } else {
                    carregarLivros();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao excluir: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("MainActivity", "Erro no excluirLivro", e);
        }
    }

    private void carregarLivros() {
        try (Cursor cursor = databaseHelper.listarLivros()) {
            listaLivros = new ArrayList<>();
            listaIds = new ArrayList<>();

            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(BancoHelper.COLUMN_ID));
                    String titulo = cursor.getString(cursor.getColumnIndexOrThrow(BancoHelper.COLUMN_TITULO));
                    String autor = cursor.getString(cursor.getColumnIndexOrThrow(BancoHelper.COLUMN_AUTOR));
                    String categoria = cursor.getString(cursor.getColumnIndexOrThrow(BancoHelper.COLUMN_CATEGORIA));
                    int lido = cursor.getInt(cursor.getColumnIndexOrThrow(BancoHelper.COLUMN_LIDO));

                    listaLivros.add(id + " - " + titulo + " - " + autor + " - " + categoria + " - " + (lido == 1 ? "Sim" : "Não"));
                    listaIds.add(id);
                } while (cursor.moveToNext());
            }

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaLivros);
            listViewLivros.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao carregar livros: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("MainActivity", "Erro no carregarLivros", e);
        }
    }

    private void limparCampos() {
        edtTitulo.setText("");
        edtAutor.setText("");
        spnCategoria.setSelection(0);
        chkLido.setChecked(false);
    }

    @Override
    protected void onDestroy() {
        databaseHelper.close();
        super.onDestroy();
    }
}