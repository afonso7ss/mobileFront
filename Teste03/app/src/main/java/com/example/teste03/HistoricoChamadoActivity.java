package com.example.teste03;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoricoChamadoActivity extends AppCompatActivity {

    private LinearLayout linearLayoutHistorico;
    private Button btnVoltar;
    private int userId;

    // Lista que armazena os chamados do usuário obtidos do backend
    private List<CadastroChamadoActivity.Chamado> chamadosUsuario = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_chamado);

        linearLayoutHistorico = findViewById(R.id.linearLayoutHistorico);
        btnVoltar = findViewById(R.id.btnVoltar);

        // Recupera o userId passado pela activity anterior
        userId = getIntent().getIntExtra("userId", -1);

        if (userId == -1) {
            Toast.makeText(this, "Usuário não definido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Carrega os chamados do usuário
        carregarChamadosDoUsuario(userId);

        btnVoltar.setOnClickListener(v -> finish());
    }

    private void carregarChamadosDoUsuario(int userId) {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<CadastroChamadoActivity.Chamado>> call = apiService.getChamadosDoUsuario(userId);
        call.enqueue(new Callback<List<CadastroChamadoActivity.Chamado>>() {
            @Override
            public void onResponse(Call<List<CadastroChamadoActivity.Chamado>> call, Response<List<CadastroChamadoActivity.Chamado>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    chamadosUsuario.clear();
                    chamadosUsuario.addAll(response.body());
                    exibirChamados();
                } else {
                    Toast.makeText(HistoricoChamadoActivity.this, "Erro ao carregar chamados do usuário!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CadastroChamadoActivity.Chamado>> call, Throwable t) {
                Toast.makeText(HistoricoChamadoActivity.this, "Erro de rede: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void exibirChamados() {
        linearLayoutHistorico.removeAllViews();

        for (int i = 0; i < chamadosUsuario.size(); i++) {
            CadastroChamadoActivity.Chamado chamado = chamadosUsuario.get(i);

            LinearLayout layoutChamado = new LinearLayout(this);
            layoutChamado.setOrientation(LinearLayout.HORIZONTAL);
            layoutChamado.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            layoutChamado.setPadding(16, 16, 16, 16);

            View statusView = new View(this);
            LinearLayout.LayoutParams paramsStatus = new LinearLayout.LayoutParams(50, 50);
            paramsStatus.setMargins(0, 0, 16, 0);
            statusView.setLayoutParams(paramsStatus);

            // Se status = false (Em Aberto), pinta de vermelho
            // Se status = true (Resolvido), pinta de verde
            if (chamado.isStatus()) {
                // status = true => vermelho
                statusView.setBackgroundColor(Color.RED);
            } else {
                // status = false => verde
                statusView.setBackgroundColor(Color.GREEN);
            }

            String statusText = chamado.isStatus() ? "Resolvido" : "Em Aberto";


            TextView textView = new TextView(this);
            textView.setText("Categoria: " + chamado.getCategoria() +
                    "\nLocal: " + chamado.getLocal() +
                    "\nDescrição: " + chamado.getDescricao() +
                    "\nStatus: " + statusText);
            textView.setTextColor(getResources().getColor(android.R.color.black));
            textView.setTextSize(16f);

            layoutChamado.addView(statusView);
            layoutChamado.addView(textView);

            // Ao clicar no chamado, passar todos os dados via Intent para DetalhesChamadoActivity
            layoutChamado.setOnClickListener(v -> {
                Intent intent = new Intent(HistoricoChamadoActivity.this, DetalhesChamadoActivity.class);
                intent.putExtra("id", chamado.getId());
                intent.putExtra("categoria", chamado.getCategoria());
                intent.putExtra("local", chamado.getLocal());
                intent.putExtra("descricao", chamado.getDescricao());
                intent.putExtra("status", chamado.isStatus());
                startActivity(intent);
            });

            linearLayoutHistorico.addView(layoutChamado);
        }
    }
}
