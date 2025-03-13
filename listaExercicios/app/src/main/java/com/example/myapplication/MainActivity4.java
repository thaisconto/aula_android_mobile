package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity4 extends AppCompatActivity {
    private EditText editNome, editIdade, editUF, editCidade, editTelefone, editEmail;
    private RadioGroup radioGroupTamanho;
    private CheckBox checkAzul, checkVermelho, checkPreto, checkBranco, checkVerde;
    private Button btnCadastrar;
    private TextView textResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main4);

        // Ajuste de padding para barra de status
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Referenciando os componentes
        editNome = findViewById(R.id.editNome);
        editIdade = findViewById(R.id.editIdade);
        editUF = findViewById(R.id.editUF);
        editCidade = findViewById(R.id.editCidade);
        editTelefone = findViewById(R.id.editTelefone);
        editEmail = findViewById(R.id.editEmail);
        radioGroupTamanho = findViewById(R.id.radioGroupTamanho);
        checkAzul = findViewById(R.id.checkAzul);
        checkVermelho = findViewById(R.id.checkVermelho);
        checkPreto = findViewById(R.id.checkPreto);
        checkBranco = findViewById(R.id.checkBranco);
        checkVerde = findViewById(R.id.checkVerde);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        textResultado = findViewById(R.id.textResultado);

        // Adicionando o evento de clique ao botão
        btnCadastrar.setOnClickListener(v -> cadastrarUsuario());
    }

    private void cadastrarUsuario() {
        String nome = editNome.getText().toString();
        String idade = editIdade.getText().toString();
        String uf = editUF.getText().toString();
        String cidade = editCidade.getText().toString();
        String telefone = editTelefone.getText().toString();
        String email = editEmail.getText().toString();

        // Pegando tamanho selecionado
        int selectedId = radioGroupTamanho.getCheckedRadioButtonId();
        RadioButton selectedRadio = findViewById(selectedId);
        String tamanho = (selectedRadio != null) ? selectedRadio.getText().toString() : "Não informado";

        // Pegando cores selecionadas
        StringBuilder cores = new StringBuilder();
        if (checkAzul.isChecked()) cores.append("Azul ");
        if (checkVermelho.isChecked()) cores.append("Vermelho ");
        if (checkPreto.isChecked()) cores.append("Preto ");
        if (checkBranco.isChecked()) cores.append("Branco ");
        if (checkVerde.isChecked()) cores.append("Verde ");

        String resultado = "Nome: " + nome + "\nIdade: " + idade + "\nUF: " + uf + "\nCidade: " + cidade +
                "\nTelefone: " + telefone + "\nEmail: " + email + "\nTamanho: " + tamanho +
                "\nCores: " + (cores.length() > 0 ? cores.toString() : "Nenhuma cor selecionada");

        textResultado.setText(resultado);
    }

    public void carregarMenuInicial(View view) {
        Intent intent = new Intent(MainActivity4.this, MainActivity.class);
        startActivity(intent); // Esse metodo abre uma nova activity
    }
}
