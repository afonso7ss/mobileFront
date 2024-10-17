package com.example.teste03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class CadastroChamadoActivity extends AppCompatActivity {

    private EditText etLocal, etDescricao;
    private Spinner spinnerCategoria;
    private Button btnEnviar, btnVerHistorico;
    public static ArrayList<String> chamados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_chamado);

        etLocal = findViewById(R.id.etLocal);
        etDescricao = findViewById(R.id.etDescricao);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        btnEnviar = findViewById(R.id.btnEnviar);
        btnVerHistorico = findViewById(R.id.btnVerHistorico);

        String[] categorias = {"ar-condicionado", "limpeza", "computadores", "estacionamento", "outros"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoria = spinnerCategoria.getSelectedItem().toString();
                String local = etLocal.getText().toString();
                String descricao = etDescricao.getText().toString();

                if (!local.isEmpty() && !descricao.isEmpty()) {
                    String chamado = "Categoria: " + categoria + "\nLocal: " + local + "\nDescrição: " + descricao;
                    chamados.add(chamado);
                    etLocal.setText("");
                    etDescricao.setText("");
                    Toast.makeText(CadastroChamadoActivity.this, "Chamado cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CadastroChamadoActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnVerHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroChamadoActivity.this, HistoricoChamadoActivity.class);
                startActivity(intent);
            }
        });
    }
}