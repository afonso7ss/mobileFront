package com.example.teste03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class CadastroChamadoActivity extends AppCompatActivity {

    private EditText etLocal, etDescricao;
    private Spinner spinnerCategoria;
    private Button btnEnviar, btnVerHistorico, btnSair;
    public static ArrayList<Chamado> chamados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_chamado);

        etLocal = findViewById(R.id.etLocal);
        etDescricao = findViewById(R.id.etDescricao);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        btnEnviar = findViewById(R.id.btnEnviar);
        btnVerHistorico = findViewById(R.id.btnVerHistorico);
        btnSair = findViewById(R.id.btnSair);

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
                    chamados.add(new Chamado(categoria, local, descricao, "pendente"));
                    etLocal.setText("");
                    etDescricao.setText("");
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

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroChamadoActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public static class Chamado {
        String categoria;
        String local;
        String descricao;
        String status;

        Chamado(String categoria, String local, String descricao, String status) {
            this.categoria = categoria;
            this.local = local;
            this.descricao = descricao;
            this.status = status;
        }
    }
}
