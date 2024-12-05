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

    // Lista completa dos chamados obtidos do backend
    private ArrayList<CadastroChamadoActivity.Chamado> chamadosList = new ArrayList<>();
    // Lista filtrada de acordo com o filtro selecionado (aberto ou fechado)
    private ArrayList<CadastroChamadoActivity.Chamado> filteredChamados = new ArrayList<>();
    private ArrayList<String> chamadosStringList = new ArrayList<>();

    private Button btnAbertos, btnFechados, btnSair;

    // Mantém o estado do filtro atual: true = exibindo apenas chamados abertos, false = apenas fechados
    private boolean currentFilter = true;

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

        // Carrega todos os chamados do servidor
        carregarChamados();

        // Filtro para chamados abertos
        btnAbertos.setOnClickListener(v -> filtrarChamados(true));

        // Filtro para chamados fechados
        btnFechados.setOnClickListener(v -> filtrarChamados(false));

        // Sair da tela
        btnSair.setOnClickListener(v -> {
            Intent intent = new Intent(ChamadosAbertosActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        // Ao clicar em um chamado da lista, vamos alternar o status
        listChamadosAbertos.setOnItemClickListener((parent, view, position, id) -> {
            // Obtém o chamado correspondente ao item clicado
            CadastroChamadoActivity.Chamado chamadoClicado = filteredChamados.get(position);

            // Alterna o status: se estiver aberto (true), muda para fechado (false). Se estiver fechado, muda para aberto.
            chamadoClicado.status = !chamadoClicado.status;

            // Atualiza a lista de acordo com o filtro atual
            filtrarChamados(currentFilter);

            // Exemplo de notificação ao usuário
            String novoStatus = chamadoClicado.status ? "Aberto" : "Fechado";
            Toast.makeText(ChamadosAbertosActivity.this, "Chamado agora está: " + novoStatus, Toast.LENGTH_SHORT).show();
        });
    }

    private void carregarChamados() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<CadastroChamadoActivity.Chamado>> call = apiService.getChamados();

        call.enqueue(new Callback<List<CadastroChamadoActivity.Chamado>>() {
            @Override
            public void onResponse(Call<List<CadastroChamadoActivity.Chamado>> call, Response<List<CadastroChamadoActivity.Chamado>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    chamadosList.clear();
                    chamadosList.addAll(response.body());

                    // Exibir apenas chamados com status true (aberto) inicialmente
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
        // Atualiza o filtro atual
        currentFilter = status;

        chamadosStringList.clear();
        filteredChamados.clear();

        for (CadastroChamadoActivity.Chamado chamado : chamadosList) {
            if (chamado.status == status) {
                // Monta a string de exibição
                String statusString = chamado.status ? "Aberto" : "Fechado";
                String chamadoString = "Categoria: " + chamado.categoria
                        + "\nLocal: " + chamado.local
                        + "\nDescrição: " + chamado.descricao
                        + "\nStatus: " + statusString;

                chamadosStringList.add(chamadoString);
                filteredChamados.add(chamado);
            }
        }

        adapter.notifyDataSetChanged();

        String mensagem = status ? "Chamados Abertos" : "Chamados Fechados";
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }
}
