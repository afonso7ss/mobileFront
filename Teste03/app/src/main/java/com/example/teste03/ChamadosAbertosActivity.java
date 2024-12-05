package com.example.teste03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChamadosAbertosActivity extends AppCompatActivity {

    private ListView listChamadosAbertos;
    private ArrayAdapter<String> adapter;
    private ArrayList<CadastroChamadoActivity.Chamado> chamadosList = new ArrayList<>();
    private ArrayList<String> chamadosStringList = new ArrayList<>();
    private Button btnAbertos, btnFechados, btnSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chamados_abertos);

        listChamadosAbertos = findViewById(R.id.listChamadosAbertos);
        btnAbertos = findViewById(R.id.ChmdAbertos);
        btnFechados = findViewById(R.id.ChmdFEchado);
        btnSair = findViewById(R.id.btnLogout);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chamadosStringList);
        listChamadosAbertos.setAdapter(adapter);

        carregarChamados();
        btnAbertos.setOnClickListener(v -> filtrarChamados(true));
        btnFechados.setOnClickListener(v -> filtrarChamados(false));
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChamadosAbertosActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void carregarChamados() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<CadastroChamadoActivity.Chamado>> call = apiService.chamados();

        call.enqueue(new Callback<List<CadastroChamadoActivity.Chamado>>() {
            @Override
            public void onResponse(Call<List<CadastroChamadoActivity.Chamado>> call, Response<List<CadastroChamadoActivity.Chamado>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    chamadosList.clear();
                    chamadosList.addAll(response.body());

                    // Exibir apenas chamados com status true inicialmente
                    filtrarChamados(true);
                } else {
                    Toast.makeText(ChamadosAbertosActivity.this, "Erro ao carregar chamados!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CadastroChamadoActivity.Chamado>> call, Throwable t) {
                Toast.makeText(ChamadosAbertosActivity.this, "Falha na conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void filtrarChamados(boolean status) {
        chamadosStringList.clear();

        for (CadastroChamadoActivity.Chamado chamado : chamadosList) {
            boolean chamadoStatus = Boolean.parseBoolean(chamado.status);
            if (chamadoStatus == status) {
                if (chamadoStatus == true) {
                    String chamadoString = "Categoria: " + chamado.categoria + "\nLocal: " + chamado.local + "\nDescrição: " + chamado.descricao + "\nStatus: " + "Aberto";
                    chamadosStringList.add(chamadoString);
                } else if (chamadoStatus == false) {
                    String chamadoString = "Categoria: " + chamado.categoria + "\nLocal: " + chamado.local + "\nDescrição: " + chamado.descricao + "\nStatus: " + "Fechado";
                    chamadosStringList.add(chamadoString);
                }
            }
        }

        adapter.notifyDataSetChanged();

        String mensagem = status ? "Chamados Abertos" : "Chamados Fechados";
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }
}
