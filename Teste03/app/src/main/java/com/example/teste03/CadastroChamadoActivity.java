package com.example.teste03;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroChamadoActivity extends AppCompatActivity {

    private EditText etLocal, etDescricao;
    private Spinner spinnerCategoria;
    private Button btnEnviar, btnVerHistorico, btnSair;
    public static ArrayList<Chamado> chamados = new ArrayList<>();

    private int userId; // Para receber do Intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_chamado);

        // Recupera o userId passado pela LoginActivity
        userId = getIntent().getIntExtra("userId", -1);

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

        btnEnviar.setOnClickListener(v -> {
            String categoria = spinnerCategoria.getSelectedItem().toString();
            String local = etLocal.getText().toString().trim();
            String descricao = etDescricao.getText().toString().trim();

            if (!local.isEmpty() && !descricao.isEmpty()) {
                // Cria o chamado com status true (Aberto)
                Chamado chamado = new Chamado(categoria, local, descricao, true);
                cadastrarChamado(userId, chamado);
            } else {
                Toast.makeText(CadastroChamadoActivity.this, "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show();
            }
        });

        btnVerHistorico.setOnClickListener(v -> {
            Intent intent = new Intent(CadastroChamadoActivity.this, HistoricoChamadoActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });

        btnSair.setOnClickListener(v -> {
            Intent intent = new Intent(CadastroChamadoActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void cadastrarChamado(int userId, Chamado chamado) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<Chamado> call = apiService.cadastrarChamado(userId, chamado);
        call.enqueue(new Callback<Chamado>() {
            @Override
            public void onResponse(Call<Chamado> call, Response<Chamado> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(CadastroChamadoActivity.this, "Chamado cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    limparCampos();
                } else {
                    Toast.makeText(CadastroChamadoActivity.this, "Erro ao cadastrar chamado!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Chamado> call, Throwable t) {
                Toast.makeText(CadastroChamadoActivity.this, "Erro de rede: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void limparCampos() {
        etLocal.setText("");
        etDescricao.setText("");
        spinnerCategoria.setSelection(0);
    }

    public static class Chamado {
        int id;
        String categoria;
        String local;
        String descricao;
        boolean status;
        int userId; // Incluindo userId para ficar completo, caso o backend retorne isso

        Chamado(String categoria, String local, String descricao, boolean status) {
            this.categoria = categoria;
            this.local = local;
            this.descricao = descricao;
            this.status = status;
        }

        public Chamado() {
        }

        public int getId() {
            return id;
        }

        public String getCategoria() {
            return categoria;
        }

        public String getLocal() {
            return local;
        }

        public String getDescricao() {
            return descricao;
        }

        public boolean isStatus() {
            return status;
        }

        public int getUserId() {
            return userId;
        }
    }
}
