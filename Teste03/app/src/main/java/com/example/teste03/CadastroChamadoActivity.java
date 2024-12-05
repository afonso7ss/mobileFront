package com.example.teste03;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class CadastroChamadoActivity extends AppCompatActivity {

    private EditText etLocal, etDescricao;
    private Spinner spinnerCategoria;
    private Button btnEnviar, btnVerHistorico, btnSair;
    public static ArrayList<Chamado> chamados = new ArrayList<>();

    private EditText edtCategoria, edtLocal, edtDescricao;
    private Switch switchStatus;
    private Button btnCadastrarChamado;

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
                String edtCategoria = spinnerCategoria.getSelectedItem().toString();
                String edtLocal = etLocal.getText().toString();
                String edtDescricao = etDescricao.getText().toString();
                btnCadastrarChamado = findViewById(R.id.btnEnviar);

                if (!edtLocal.isEmpty() && !edtDescricao.isEmpty()) {
                    chamados.add(new Chamado(edtCategoria, edtLocal, edtDescricao, "Aberto"));
                    etLocal.setText("");
                    etDescricao.setText("");
                    btnCadastrarChamado.setOnClickListener(view -> cadastrarChamado());
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

    private void cadastrarChamado() {
        String categoria = edtCategoria.getText().toString().trim();
        String local = edtLocal.getText().toString().trim();
        String descricao = edtDescricao.getText().toString().trim();
        String status = switchStatus.isChecked() ? "true" : "false";

        if (categoria.isEmpty() || local.isEmpty() || descricao.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        Chamado chamado = new Chamado(categoria, local, descricao, status);
        ApiService apiService = RetrofitClient.getApiService();
        retrofit2.Call<Chamado> call = apiService.cadastrarChamado(chamado);
        call.enqueue(new Callback<Chamado>() {
            @Override
            public void onResponse(retrofit2.Call<Chamado> call, Response<Chamado> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CadastroChamadoActivity.this, "Chamado cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    limparCampos();
                } else {
                    Toast.makeText(CadastroChamadoActivity.this, "Erro ao cadastrar chamado!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Chamado> call, Throwable t) {
                Toast.makeText(CadastroChamadoActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void limparCampos() {
        edtCategoria.setText("");
        edtLocal.setText("");
        edtDescricao.setText("");
        switchStatus.setChecked(false);
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
